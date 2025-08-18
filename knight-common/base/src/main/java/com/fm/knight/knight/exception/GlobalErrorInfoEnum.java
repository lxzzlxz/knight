package com.fm.knight.knight.exception;


/**
 * 系统及业务级别的通用错误码
 *
 */
public enum GlobalErrorInfoEnum implements ErrorInfoInterface {
    SUCCESS("0", "success"),
    FAILED("-1", "system exception"),
    AUTH_USER_UNAUTH("-2", "unAuthorized"),
    UTILES_MD5_NOSUCH("-3", "no such algorith"),
    NOT_FOUND("-4","service not found"),
    RESOURCE_NOT_EXIST("-5", "resource file not found"),
    ENCYPTY_CYPTY_ERROR("-6","encypty cypty error"),
    CFG_NOT_FUND_SERVICE("-7","not found service"),
    CFG_NOT_FUND_METHOD("-8","not found method"),
    CFG_SECURITY_CHECK_METHOD("-9","no such method or security check is not pass."),
    CFG_INSTANCE_OBJECT( "-10","instantiation object error."),
    CFG_EXECUTE_METHOD("-11","method execute error."),
    CFG_NOT_FUND_CLASS("-12","class not found error."),
    CFG_NOT_SUPPORT_ARRAY("-13","not support array."),
    XML_PARSE_EXCEPTION("-14","xml parse excption."),
    AES_UTILS_EXCEPTION("-15","AES utils excption."),
    DRUID_LOAD_FILTER("-16","druid load filter error"),
    HYSTRIX_EXCEPTION("-17","hystrix exception"),
	AUTH_NOT_FUND_PARAM("-18","not found auth param"),
	AUTH_NOT_FUND_SERVICE("-19","not found auth service"),
	HEADER_NO_AUTH("-20","no authorization header field"),
	SIGN_NO_PARAM("-21","not param"),
	SIGN_NO_AUTH("-22","not auth param"),
	SIGN_NO_MATCH("-23","sign not match"),
	NO_SUCH_ALGORITHM("-24","no such Algorithm"),
	SOCKET_TIMEOUT_EXCEPTION("-25","Read Timeout"),
    ASYNC_INVOKE_ERROR("-26","Async invoke error");

    private String code;

    private String message;

    GlobalErrorInfoEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
