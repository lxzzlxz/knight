package com.fm.knight.service;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface IAuthService {

    /**
     * 单向认证
     *
     * @param domain
     * @param sessionId
     * @return
     */
    Mono<Map<String, Object>> check(String domain, String sessionId);

    /*  *//**
     * 登录
     * @param domain
     * @param sessionId
     * @param obj
     * @param second
     * @return
     *//*
    Map<String,Object> login(String domain, String sessionId, String userId, String obj, int second);

    *//**
     * 登录
     * @param domain
     * @param sessionId
     * @param obj
     * @param second
     * @return
     *//*
    Map<String,Object> appLogin(String domain, String sessionId, String userId, String obj, int second);

    *//**
     * 登出
     * @param domain
     * @param sessionId
     * @return
     *//*
    Map<String,Object> logOut(String domain, String sessionId);

    *//**
     * 缓存数据
     * @param domain
     * @param sessionId
     * @param obj
     * @return
     *//*
    Map<String,Object> setData(String domain, String sessionId, String obj);

    *//**
     * 获取数据
     * @param domain
     * @param sessionId
     * @return
     *//*
    String getData(String domain, String sessionId);

    *//**
     * 设置服务信息
     * @param serviceName
     * @param key
     * @return
     *//*
    Map<String,Object> setServiceKey(String serviceName, String key);

    *//**
     * 删除服务信息
     * @param serviceName
     * @return
     *//*
    Map<String,Object> delServiceKey(String serviceName);

    *//**
     * 服务认证，根据key获取服务名称
     * @param key
     * @return
     *//*
    String getService(String key);*/
}
