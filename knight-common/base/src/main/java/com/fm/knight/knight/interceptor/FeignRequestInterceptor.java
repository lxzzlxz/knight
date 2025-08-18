package com.fm.knight.knight.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    private static String serverKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("from-service", serverKey);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes) {
            HttpServletRequest request = attributes.getRequest();
            String sessionId = request.getHeader("SESSIONID");
            if (!StringUtils.isEmpty(sessionId)) {
                requestTemplate.header("SESSIONID", sessionId);
            }
        }
    }

    public static void setServerKey(String key) {
        FeignRequestInterceptor.serverKey = key;
    }
}
