package com.fm.knight.knight.model;

public class ResponseCode {
    public static final String CODE = "code";

    public static final String MSG = "msg";

    public static final String DATA = "data";

    public static final String MAP_DATAS = "datas";

    /**
     * 成功
     */
    public static final String SUCCESS_CODE = "0";

    public static final String SUCCESS_MSG = "ok";
    /**
     * 失败
     */
    public static final String FAILED_CODE = "-1";

    public static final String FAILED_MSG = "failed";

    /**
     * session过期
     */
    public static final String NOT_LOGIN_CODE = "1";

    public static final String NOT_LOGIN_MSG = "请登录";

    /**
     * error
     */
    public static final String ERROR_CODE = "500";

    public static final String ERROR_MSG = "ERROR";

    /**
     * 用户自定义返回信息
     */
    public static final String CUSTOM_CODE = "2";

    public static final String PARAMETER_ERROR = "参数错误";

    /**
     * 没有权限
     */
    public static final String PERMISSION_DENIED_CODE = "3";

    public static final String PERMISSION_DENIED_MSG = "没有权限，请联系管理员申请权限";

    /**
     * 服務沒有權限
     */
    public static final String SERVICE_PERMISSION_DENIED_CODE = "4";

    public static final String SERVICE_PERMISSION_DENIED_MSG = "没有服务权限，请联系管理员";

    public static final String SERVICE_OLDPASSWORD_CODE = "5";

    public static final String SERVICE_OLDPASSWORD_MSG = "原密码输入错误，请重新输入";
}
