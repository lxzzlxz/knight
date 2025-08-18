package com.fm.knight.knight.client;

import com.fm.knight.knight.helper.ResultMapHelper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "auth", fallback = AuthFallBack.class)
public interface AuthClient {

    @RequestMapping(value = "/auth/v1/check", method = RequestMethod.GET)
    Map<String, Object> check(@RequestParam("domain") String domain, @RequestParam("sessionId") String sessionId);

    @RequestMapping(value = "/auth/v1/login", method = RequestMethod.POST)
    Map<String, Object> login(@RequestParam("domain") String domain, @RequestParam("sessionId") String sessionId, @RequestParam("userId") String userId, @RequestBody String body, @RequestParam("second") int second);

    @RequestMapping(value = "/auth/v1/appLogin", method = RequestMethod.POST)
    Map<String, Object> appLogin(@RequestParam("domain") String domain, @RequestParam("sessionId") String sessionId, @RequestParam("userId") String userId, @RequestBody String body, @RequestParam("second") int second);

    @RequestMapping(value = "/auth/v1/logout", method = RequestMethod.GET)
    Map<String, Object> loginOut(@RequestParam("domain") String domain, @RequestParam("sessionId") String sessionId);

    @RequestMapping(value = "/auth/v1/setdata", method = RequestMethod.POST)
    Map<String, Object> setData(@RequestBody String data, @RequestParam("domain") String domain, @RequestParam
            ("sessionId") String sessionId);

    @RequestMapping(value = "/auth/v1/getdata", method = RequestMethod.GET)
    String getData(@RequestParam("domain") String domain, @RequestParam("sessionId") String sessionId);

    @RequestMapping(value = "/auth/v1/setServiceKey", method = RequestMethod.GET)
    Map<String, Object> setServiceKey(@RequestParam("serviceName") String sessionId, @RequestParam("sessionId") String serviceName);

    @RequestMapping(value = "/auth/v1/delServiceKey", method = RequestMethod.GET)
    Map<String, Object> delServiceKey(@RequestParam("serviceName") String serviceName);

    @RequestMapping(value = "/auth/v1/getService", method = RequestMethod.GET)
    String getService(@RequestParam("sessionId") String sessionId);
}

@Component
class AuthFallBack implements AuthClient {

    @Override
    public Map<String, Object> check(String domain, String sessionId) {
        return ResultMapHelper.getServiceErrorMap("auth");
    }

    @Override
    public Map<String, Object> login(String domain, String sessionId, String userId, String body, int second) {
        return ResultMapHelper.getServiceErrorMap("auth");
    }

    @Override
    public Map<String, Object> appLogin(String domain, String sessionId, String userId, String body, int second) {
        return ResultMapHelper.getServiceErrorMap("auth");
    }

    @Override
    public Map<String, Object> loginOut(String domain, String sessionId) {
        return ResultMapHelper.getServiceErrorMap("auth");
    }

    @Override
    public Map<String, Object> setData(String data, String domain, String sessionId) {
        return ResultMapHelper.getServiceErrorMap("auth");
    }

    @Override
    public String getData(String domain, String sessionId) {

        return null;
    }

    @Override
    public Map<String, Object> setServiceKey(String sessionId, String serviceName) {

        return ResultMapHelper.getServiceErrorMap("auth");
    }

    @Override
    public Map<String, Object> delServiceKey(String serviceName) {

        return ResultMapHelper.getServiceErrorMap("auth");
    }

    @Override
    public String getService(String sessionId) {

        return null;
    }

}
