package com.fm.knight.interceptor;

import com.fm.knight.filter.PreFilter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactiveFeignInterceptor implements RequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(ReactiveFeignInterceptor.class);

    @Override
    public void apply(RequestTemplate template) {
        // 从 Reactor 上下文中获取数据（需确保上游已放入 Context）
        String serviceKey = Mono.deferContextual(ctx -> Mono.justOrEmpty(ctx.<String>getOrEmpty("serviceKey"))).block(); // 注意：Feign 拦截器是同步的，此处 block() 是安全的（但需确保上下文已存在）

        // fallback：如果上下文中没有，则从静态方法或默认值获取
        if (serviceKey == null) {
            serviceKey = PreFilter.getServiceKey(); // 假设 PreFilter 是同步的
        }

        template.header("from-service", serviceKey);
        log.debug("Added header: from-service={}", serviceKey);
    }
}
