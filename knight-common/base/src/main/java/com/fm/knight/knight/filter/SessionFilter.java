package com.fm.knight.knight.filter;

import com.fm.knight.knight.context.RequestContext;
import com.fm.knight.knight.interceptor.FeignRequestInterceptor;
import com.fm.knight.knight.context.Current;
import com.fm.knight.knight.helper.UUIDHelper;
import com.fm.knight.knight.service.IAuthCheckService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.MessageDigest;

@Component
@ServletComponentScan
@WebFilter(filterName = "customSessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(SessionFilter.class);
    private Boolean isServiceNameInit = false;
    @Autowired
    private IAuthCheckService authCheckService;
    public static final Logger accessLog = LoggerFactory.getLogger("access");
    public static final Logger serviceLog = LoggerFactory.getLogger("service");
    @Value("${spring.application.name}")
    private String serviceName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        //设置服务key
        if(!isServiceNameInit){
            setServiceKey();
            isServiceNameInit = true;
        }

        HttpServletRequest req = null;
        HttpServletResponse res = null;
        if(servletRequest instanceof  HttpServletRequest){
            req = (HttpServletRequest) servletRequest;
            res = (HttpServletResponse) servletResponse;
        }

        assert req != null;
        req.setCharacterEncoding("utf-8");
        res.setCharacterEncoding("utf-8");
        setRequestContext(req, res);

        String queryString = req.getQueryString();
        String uri = req.getRequestURI();
        String remoteIp = getRemoteAddr(req);
        String localIp = req.getLocalAddr();

        //access log
        String pvId = generatePVID(req,remoteIp,localIp);
        StringBuilder accessLogSB = new StringBuilder();
        accessLogSB.append(System.currentTimeMillis()).append("\t").append("ACCESS").append("\t").append(pvId).append("\t")
                .append(localIp).append("\t").append(remoteIp).append("\t").append(uri).append("\t").append(queryString).append("\t");
        RequestContext.getCurrent().setAccessLog(accessLogSB);

        //service log
        StringBuilder serviceLogSB = new StringBuilder();
        RequestContext.getCurrent().setServiceLog(serviceLogSB);

        StringBuilder returnLogSB = new StringBuilder();
        RequestContext.getCurrent().setReturnLog(returnLogSB);

        try{

            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Exception e){
            log.error("", e);
        }finally {
            accessLog();
            serviceLog(res, uri, pvId);
            returnLog(res, pvId);
            RequestContext.remove();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
    private void setRequestContext(HttpServletRequest req, HttpServletResponse res) {
        Current current = RequestContext.initCurrent();
        current.setResponse(res);
        current.setRequest(req);
        String sessionId = req.getHeader("SESSIONID");
        if(null != sessionId) {
            String[] ary = sessionId.split("\\$");
            current.setToken(ary[0]);
            current.setDomain(ary[1]);
        }
    }
    private void setServiceKey() {
        String key = UUIDHelper.getUUID();
        FeignRequestInterceptor.setServerKey(key);
        authCheckService.delServiceKey(serviceName);
        authCheckService.setServiceKey(serviceName, key);
    }
    private String getRemoteAddr(HttpServletRequest req) {
        String remoteIP = req.getRemoteAddr();

        // 1. 优先从 X-Forwarded-For 获取（支持多级代理）
        String xffHeader = req.getHeader("X-Forwarded-For");
        if (xffHeader != null) {
            String[] ips = xffHeader.split("\\s*,\\s*");
            if (ips.length > 0) {
                String candidateIP = ips[0].trim();
                if (!candidateIP.isEmpty()) {
                    remoteIP = candidateIP;
                }
            }
        }

        // 2. 回退到 X-Real-IP（如 Nginx）
        if (remoteIP == null || remoteIP.isEmpty()) {
            remoteIP = req.getHeader("X-Real-IP");
        }

        // 3. 默认返回原始 remoteAddr（兜底）
        return remoteIP != null ? remoteIP : "";
    }
    private String generatePVID(HttpServletRequest req, String remoteIP,String localIp){
        try{
            String now = String.valueOf(System.currentTimeMillis());
            String uri = req.getRequestURI();

            // md5 加密
            byte[] btInput = (remoteIP + now + uri + localIp).getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();

            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(Exception e){
            log.error("",e);
        }
        return UUIDHelper.getUUID();
    }
    private void accessLog() {
        accessLog.info(RequestContext.getCurrent().getAccessLog().toString());
    }
    private void returnLog(HttpServletResponse res, String pvId) {
        accessLog.info(System.currentTimeMillis() + "\t" + "RETURN" + "\t" + pvId + "\t" + res.getStatus() + "\t" + RequestContext.getCurrent().getReturnLog());
    }

    private void serviceLog(HttpServletResponse res,String uri, String pvId) {
        serviceLog.info( System.currentTimeMillis() + "\t"+ pvId + "\t" + uri + "\t" + res.getStatus()+ "\t"+ RequestContext.getCurrent().getServiceLog().toString());
    }
}
