package com.fm.knight.knight.interceptor;

import com.fm.knight.knight.annotation.RedisTemplateProvider;
import com.fm.knight.knight.annotation.RepeatSubmit;
import com.fm.knight.knight.context.Current;
import com.fm.knight.knight.context.RequestContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/*

@Component
public class SameUrlDataInterceptor extends RepeatSubmitInterceptor {

    private static final String SESSION_REPEAT_KEY = "repeatData";

    private final RedisTemplate<String, Long> redisTemplate;

    // 通过依赖提供者注入
    public SameUrlDataInterceptor(RedisTemplateProvider<String, Long> provider) {
        this.redisTemplate = provider.getRedisTemplate();
    }

    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
        Long currentTime = System.currentTimeMillis();
        String url = request.getRequestURI();
        Current current = RequestContext.getCurrent();
        String key = SESSION_REPEAT_KEY + ":" + current.getUser().getId() + ":" + url;

        Long preTime = redisTemplate.opsForValue().get(key);
        if (preTime != null && compareTime(currentTime, preTime, annotation.interval())) {
            redisTemplate.opsForValue().set(key, currentTime, 1, TimeUnit.MINUTES);
            return true;
        }
        redisTemplate.opsForValue().set(key, currentTime, 1, TimeUnit.MINUTES);
        return false;
    }

    private boolean compareTime(Long currentTime, Long preTime, int interval) {
        return (currentTime - preTime) < interval;
    }
}
*/
