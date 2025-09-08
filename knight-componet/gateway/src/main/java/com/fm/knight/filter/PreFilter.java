package com.fm.knight.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.knight.feign.AuthFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class PreFilter implements GatewayFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(PreFilter.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<String> ALLOWED_URIS = Arrays.asList("/auth/login", "/public/api");
    private static final List<String> IGNORED_URIS = Arrays.asList("/swagger", "/v3/api-docs", "/webjars/");
    /**
     * service 标识
     */
    private static String serviceKey;
    private final AtomicBoolean isServiceNameInit = new AtomicBoolean(false);
    private final AuthFeignClient authFeignClient;
    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${session.domain}")
    private String authDomain;

    public PreFilter(AuthFeignClient authFeignClient) {
        this.authFeignClient = authFeignClient;
    }

    public static String getServiceKey() {
        return serviceKey;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果服务信息没有初始化，初始化服务信息，用于服务之间权限控制
        if (!isServiceNameInit.getAndSet(true)) {
            setServiceKey();
        }

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        setupCorsHeaders(response, request.getHeaders().getFirst(HttpHeaders.ORIGIN));
        initServiceKey();

        try {
            String uri = request.getPath().toString();
            String remoteIp = getRemoteIp(request);
            String localIp = getLocalIp(exchange);
            String pvId = generatePVID(request, remoteIp, localIp);

            logAccess(pvId, localIp, remoteIp, uri);
            String sessionId = getSessionId(request);

            if (isUriAllowed(uri)) {
                return handleAllowedRequest(exchange, pvId);
            }

            if (isUriIgnored(uri)) {
                return handleIgnoredRequest(exchange, sessionId, pvId, chain);
            }

            return checkAuthentication(exchange, sessionId, pvId, chain);

        } catch (NoSuchAlgorithmException e) {
            log.error("PVID生成失败", e);
            return handleErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonMap("msg", "系统错误，请稍后重试"));
        }
    }

    private void initServiceKey() {
        if (isServiceNameInit.compareAndSet(false, true)) {
            try {
                String newKey = generateServiceKey();
                Mono.fromCallable(() -> authFeignClient.delServiceKey(serviceName)).then(Mono.fromCallable(() -> authFeignClient.setServiceKey(serviceName, newKey))).subscribe(result -> log.info("服务密钥初始化成功: {}", result), ex -> {
                    log.error("服务密钥初始化失败", ex);
                    isServiceNameInit.set(false);
                });
            } catch (Exception e) {
                log.error("服务密钥初始化异常", e);
                isServiceNameInit.set(false);
            }
        }
    }

    private String generateServiceKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private void setupCorsHeaders(ServerHttpResponse response, String origin) {
        if (origin != null) {
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        }
    }

    private String getSessionId(ServerHttpRequest request) {
        Optional<String> sessionOpt = Optional.ofNullable(request.getCookies().getFirst("JSESSIONID")).map(HttpCookie::getValue);
        return sessionOpt.orElseGet(this::generateSessionId);
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    private Mono<Void> handleAllowedRequest(ServerWebExchange exchange, String pvId) {
        exchange.getAttributes().put("pvId", pvId);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> handleIgnoredRequest(ServerWebExchange exchange, String sessionId, String pvId, GatewayFilterChain chain) {
        setRequestAttributes(exchange, sessionId);
        exchange.getAttributes().put("pvId", pvId);
        return chain.filter(exchange);
    }

    private Mono<Void> checkAuthentication(ServerWebExchange exchange, String sessionId, String pvId, GatewayFilterChain chain) {
        return authFeignClient.check(authDomain, sessionId).flatMap(map -> {
            if (isValidAuthResult(map)) {
                setRequestAttributes(exchange, sessionId);
                exchange.getAttributes().put("pvId", pvId);
                return chain.filter(exchange);
            } else {
                return handleUnauthorized(exchange);
            }
        }).onErrorResume(e -> handleUnauthorized(exchange));
    }

    private boolean isValidAuthResult(Map<String, Object> authResult) {
        return authResult != null && "0".equals(authResult.get("code"));
    }

    private Mono<Void> handleUnauthorized(ServerWebExchange exchange) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", "1");
        result.put("msg", "请登录");
        return handleErrorResponse(exchange.getResponse(), HttpStatus.OK, result);
    }

    private Mono<Void> handleErrorResponse(ServerHttpResponse response, HttpStatus status, Map<String, Object> errorResponse) {
        return handleErrorResponse(response, status, toJsonString(errorResponse));
    }

    private Mono<Void> handleErrorResponse(ServerHttpResponse response, HttpStatus status, String errorMessage) {
        response.setStatusCode(status);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");

        byte[] bytes = errorMessage.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    private String getRemoteIp(ServerHttpRequest request) {
        String xffHeader = request.getHeaders().getFirst("X-Forwarded-For");
        if (xffHeader != null) {
            return xffHeader.split(",")[0].trim();
        }

        InetSocketAddress remoteAddress = request.getRemoteAddress();
        return remoteAddress != null ? remoteAddress.getAddress().getHostAddress() : "unknown";
    }

    private String getLocalIp(ServerWebExchange exchange) {
        InetSocketAddress localAddress = exchange.getRequest().getLocalAddress();
        return localAddress != null ? localAddress.getAddress().getHostAddress() : "unknown";
    }

    private String generatePVID(ServerHttpRequest request, String remoteIp, String localIp) throws NoSuchAlgorithmException {
        String raw = String.join("", remoteIp, String.valueOf(System.currentTimeMillis()), request.getPath().toString(), localIp);

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private boolean isUriAllowed(String uri) {
        for (String allowedUri : ALLOWED_URIS) {
            if (uri.startsWith(allowedUri)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUriIgnored(String uri) {
        for (String ignoredUri : IGNORED_URIS) {
            if (uri.contains(ignoredUri)) {
                return true;
            }
        }
        return false;
    }

    private void setRequestAttributes(ServerWebExchange exchange, String sessionId) {
        exchange.getAttributes().put("sessionId", sessionId);
    }

    private String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("JSON序列化失败", e);
            return "{\"code\":\"500\",\"msg\":\"系统错误\"}";
        }
    }

    private void logAccess(String pvId, String localIp, String remoteIp, String uri) {
        log.info("{} ACCESS {} {} {} {}", System.currentTimeMillis(), pvId, localIp, remoteIp, uri);
    }

    @Override
    public int getOrder() {
        return -100;
    }

    private void setServiceKey() {
        // 如果已初始化，直接返回
        if (isServiceNameInit.get()) {
            return;
        }
        Mono.defer(() -> {
            // 双重检查锁定
            if (isServiceNameInit.compareAndSet(false, true)) {
                String key = UUID.randomUUID().toString().replaceAll("-", "");
                return authFeignClient.delServiceKey(serviceName).then(authFeignClient.setServiceKey(serviceName, key)).doOnNext(result -> {
                    serviceKey = key;
                    log.info("Service key initialized: {}", result);
                }).onErrorResume(e -> {
                    isServiceNameInit.set(false); // 回滚状态
                    log.error("Failed to initialize service key", e);
                    return Mono.error(e);
                });
            }
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic()).subscribe();
    }
}
