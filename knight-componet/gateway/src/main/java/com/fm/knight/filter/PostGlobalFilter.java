package com.fm.knight.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class PostGlobalFilter implements GlobalFilter, Ordered {
    public static final Logger accessLog = LoggerFactory.getLogger("access");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 先执行后续过滤器
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 后置处理逻辑
            ServerHttpResponse response = exchange.getResponse();
            // 从请求属性中获取pvId（假设在之前的过滤器中已设置）
            String pvId = exchange.getAttribute("pvId");
            // 构建日志字符串
            String sb = Instant.now().toEpochMilli() + "\t" + "RETURN" + "\t" + pvId + "\t" + (response.getStatusCode() != null ? response.getStatusCode().value() : "N/A");
            accessLog.info(sb);
            // 在Gateway中，响应状态码通常由后端服务设置，这里不需要显式设置
            // 如果需要修改响应，可以通过exchange.getResponse()进行操作
        }));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
