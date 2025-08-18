package com.fm.knight.knight.interceptor;

import com.fm.knight.knight.annotation.Operation;
import com.fm.knight.knight.annotation.Resource;
import com.fm.knight.knight.context.Current;
import com.fm.knight.knight.context.RequestContext;
import com.fm.knight.knight.helper.RHelper;
import com.fm.knight.knight.helper.ResultMapHelper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;

@Component
public class SecurityInterceptor implements MethodInterceptor {

    @Value("${spring.application.name}")
    private String scope;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> c = invocation.getMethod().getDeclaringClass();
        Resource resource = c.getAnnotation(Resource.class);
        Operation operation = invocation.getMethod().getAnnotation(Operation.class);
        Current current = RequestContext.getCurrent();
        if (null != resource && null != operation && null != current && !current.getPermissionChecked()) {
            String permission = scope + "$" + resource.value() + "$" + operation.value();
            if (null == current.getPermissions() || !current.getPermissions().contains(permission)) {
                Class<?> returnType = invocation.getMethod().getReturnType();
                switch (returnType.getName()) {
                    case "java.util.Map":
                        return ResultMapHelper.getPermissionDenied();
                    case "com.fm.knight.model.R":
                        return RHelper.getPermissionDenied();
                    case "java.util.List":
                        return Collections.EMPTY_LIST;
                    case "void":
                        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        assert servletRequestAttributes != null;
                        HttpServletResponse response = servletRequestAttributes.getResponse();
                        assert response != null;
                        ServletOutputStream outputStream = response.getOutputStream();
                        String res = "No Permission !";
                        byte[] b = res.getBytes();
                        outputStream.write(b);
                        outputStream.flush();
                        outputStream.close();
                        break;
                }
                return null;
            }
            current.setPermissionChecked(true);
        }
        return invocation.proceed();
    }
}
