package com.fm.knight.knight.context;

import com.alibaba.fastjson2.JSON;
import com.fm.knight.knight.service.IAuthCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class RequestContext {

    public static final Logger log = LoggerFactory.getLogger(RequestContext.class);

    private static final ThreadLocal<Current> current = new ThreadLocal<>();

    private static IAuthCheckService authCheckService;


    public static Current getCurrent() {
        Current cur = current.get();
        log.info("current:" + (null != cur));
        if (null != cur && !cur.getGetData() && null != cur.getToken()) {
            if (null == authCheckService) {
                authCheckService = (IAuthCheckService) AppContext.getContext().getBean("authCheckService");
            }
            String json = authCheckService.getData(cur.getDomain(), cur.getToken());
            if (!StringUtils.isEmpty(json)) {
                Object parse = JSON.parse(json);
                Current cache = JSON.parseObject(parse.toString(), Current.class);
                cur.setUser(cache.getUser());
                cur.setPermissions(cache.getPermissions());
                cur.setGetData(true);
            }
        }
        return cur;
    }

    public static Current initCurrent() {
        current.remove();
        current.set(new Current());

        return current.get();
    }

    public static void remove() {
        current.remove();
    }

    public static ApplicationContext getApplicationContext() {

        return AppContext.getContext();
    }


    public static WebApplicationContext getWebApplicationContext() {

        return ContextLoader.getCurrentWebApplicationContext();
    }
}
