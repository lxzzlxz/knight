package com.fm.knight.service.impl;

import com.fm.knight.service.IAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService implements IAuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private static final String SESSION_PREFIX = "legao_session_";

    private static final String USER_ID_PREFIX = "legao_userid_";

    private static final String SESSION_DATA_PREFIX = "legao_session_data_";

    private static final String SESSION_EXPIRE_PREFIX = "legao_expire_";

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;


    @Override
    public Mono<Map<String, Object>> check(String domain, String sessionId) {
        // 定义Key
        final String key = SESSION_PREFIX + domain + "_" + sessionId;
        final String dataKey = SESSION_DATA_PREFIX + domain + "_" + sessionId;
        final String expireKey = SESSION_EXPIRE_PREFIX + domain + "_" + sessionId;
        final String userKey = USER_ID_PREFIX + domain + "_" + sessionId;

        // 初始化返回结果（默认失败）
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("code", "1");
        resultMap.put("msg", "fail");

        // 获取 ReactiveValueOperations
        ReactiveValueOperations<String, String> valueOps = reactiveRedisTemplate.opsForValue();

        return valueOps.get(key).flatMap(userId -> {
            if (userId == null || userId.isEmpty()) {
                return Mono.just(resultMap);
            }

            return valueOps.get(expireKey).flatMap(expireStr -> {
                try {
                    int expire = Integer.parseInt(expireStr);
                    if (expire <= 0) {
                        return Mono.just(resultMap);
                    }

                    // 设置成功响应
                    resultMap.put("code", "0");
                    resultMap.put("msg", "ok");

                    // 更新所有Key的过期时间
                    Duration duration = Duration.ofSeconds(expire);
                    return Mono.zip(reactiveRedisTemplate.expire(key, duration), reactiveRedisTemplate.expire(userKey, duration), reactiveRedisTemplate.expire(dataKey, duration), reactiveRedisTemplate.expire(expireKey, duration)).thenReturn(resultMap);
                } catch (NumberFormatException e) {
                    return Mono.just(resultMap);
                }
            });
        }).onErrorResume(e -> {
            Map<String, Object> errorResult = new HashMap<>(2);
            errorResult.put("code", "1");
            errorResult.put("msg", "fail");
            return Mono.just(errorResult);
        }).defaultIfEmpty(resultMap);
    }
}
