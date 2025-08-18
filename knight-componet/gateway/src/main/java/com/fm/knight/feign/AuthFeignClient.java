package com.fm.knight.feign;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@ReactiveFeignClient(value = "auth", fallback = AuthFallBack.class)
public interface AuthFeignClient {
    @GetMapping("/auth/v1/check")
    Mono<Map<String, Object>> check(@RequestParam("domain") String domain, @RequestParam("sessionId") String sessionId);

    @GetMapping("/auth/v1/setServiceKey")
    Mono<Map<String, Object>> setServiceKey(@RequestParam("serviceName") String sessionId, @RequestParam("sessionId") String serviceName);

    @GetMapping("/auth/v1/delServiceKey")
    Mono<Map<String, Object>> delServiceKey(@RequestParam("serviceName") String serviceName);
}

@Component
class AuthFallBack implements AuthFeignClient {
    private Map<String, Object> errorResponse() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", "1");
        map.put("msg", "auth service error!");
        return map;
    }

    @Override
    public Mono<Map<String, Object>> check(String domain, String sessionId) {
        return Mono.just(errorResponse());
    }

    @Override
    public Mono<Map<String, Object>> setServiceKey(String sessionId, String serviceName) {
        return Mono.just(errorResponse());
    }

    @Override
    public Mono<Map<String, Object>> delServiceKey(String serviceName) {
        return Mono.just(errorResponse());
    }
}
