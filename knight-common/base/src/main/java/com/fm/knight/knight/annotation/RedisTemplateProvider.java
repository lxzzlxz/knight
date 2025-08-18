package com.fm.knight.knight.annotation;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisTemplateProvider<K, V> {
    RedisTemplate<K, V> getRedisTemplate();
}
