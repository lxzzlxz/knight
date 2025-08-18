package com.fm.knight.knight.service;

import java.util.Map;

public interface IAuthCheckService {
    /**
     * 获取缓存数据
     * @return String
     */
    String getData(String domain, String sessionId);
    /**
     * 删除服务令牌
     * @return map
     */
    Map<String,Object> delServiceKey(String serviceName);
    /**
     * 服务认证，设置服务令牌
     */
    Map<String,Object> setServiceKey(String serviceName, String key);
}
