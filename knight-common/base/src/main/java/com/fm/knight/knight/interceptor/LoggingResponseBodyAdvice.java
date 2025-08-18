package com.fm.knight.knight.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.knight.knight.context.RequestContext;
import com.fm.knight.knight.context.Current;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 该方法主要用于记录日志
 */
@ControllerAdvice
public class LoggingResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    // 使用单例ObjectMapper，避免重复创建
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 可配置的日志记录开关
    private static final boolean loggingEnabled = true;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 可以通过配置决定是否启用，或者添加特定注解检查
        return loggingEnabled && RequestContext.getCurrent() != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (!loggingEnabled) {
            return body;
        }

        Current context = RequestContext.getCurrent();
        if (context == null) {
            return body;
        }

        try {
            // 使用StringBuilder提高字符串拼接效率
            StringBuilder returnLog = context.getReturnLog();
            if (returnLog != null) {
                // 添加分隔符，便于区分多次记录
                if (returnLog.length() > 0) {
                    returnLog.append("\n");
                }
                returnLog.append(objectMapper.writeValueAsString(body));
            }
        } catch (JsonProcessingException e) {
            // 使用日志框架记录错误
            e.printStackTrace(); // 实际项目中替换为日志记录
        }

        return body;
    }

}
