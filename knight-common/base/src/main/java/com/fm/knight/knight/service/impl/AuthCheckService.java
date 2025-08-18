package com.fm.knight.knight.service.impl;

import com.fm.knight.knight.client.AuthClient;
import com.fm.knight.knight.service.IAuthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthCheckService implements IAuthCheckService {

    @Autowired
    private AuthClient authClient;

    /*@Override
    public Boolean check(String authDomain, String sessionId) {
        Map<String,Object> map = authClient.check(authDomain, sessionId);
        if(null != map && "0".equals(map.get("code"))){
            return true;
        }
        return false;
    }

    @Override
    public Boolean login(String authDomain, String sessionId, String userId, String obj, int seconds) {
        Map<String,Object> map = authClient.login(authDomain, sessionId, userId, obj, seconds);
        if(null != map && "0".equals(map.get("code"))){
            return true;
        }
        return false;
    }

    @Override
    public Boolean appLogin(String authDomain, String sessionId, String userId, String obj, int seconds) {
        Map<String,Object> map = authClient.appLogin(authDomain, sessionId, userId, obj, seconds);
        if(null != map && "0".equals(map.get("code"))){
            return true;
        }
        return false;
    }

    @Override
    public Boolean logout(String authDomain, String sessionId) {
        Map<String,Object> map = authClient.loginOut(authDomain, sessionId);
        if(null != map && "0".equals(map.get("code"))){
            return true;
        }
        return false;
    }

    @Override
    public Boolean setData(String authDomain, String sessionId, String obj) {
        Map<String,Object> map = authClient.setData(obj,authDomain, sessionId);
        if(null != map && "0".equals(map.get("code"))){
            return true;
        }
        return false;
    }*/

    @Override
    public String getData(String authDomain, String sessionId) {

        return authClient.getData(authDomain, sessionId);
    }

    @Override
    public Map<String,Object> setServiceKey(String serviceName, String key) {

        return authClient.setServiceKey(serviceName, key);
    }

    @Override
    public Map<String,Object> delServiceKey(String serviceName) {

        return authClient.delServiceKey(serviceName);
    }
/*
    @Override
    public String getService(String key) {

       return authClient.getService(key);
    }*/
}
