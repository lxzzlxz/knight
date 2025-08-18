package com.fm.knight.knight.helper;

import com.fm.knight.knight.model.R;
import com.fm.knight.knight.model.ResponseCode;
import org.springframework.util.StringUtils;

public class RHelper {
    private RHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> R<T> getServiceError(String serviceName) {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.CUSTOM_CODE);
        r.setMsg(serviceName + "服务不通");
        return r;
    }

    public static <T> R<T> getPermissionDenied() {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.PERMISSION_DENIED_CODE);
        r.setMsg(ResponseCode.PERMISSION_DENIED_MSG);
        return r;
    }

    public static <T> R<T> ok() {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.SUCCESS_CODE);
        r.setMsg(ResponseCode.SUCCESS_MSG);
        return r;
    }

    public static <T> R<T> getSuccessR(T t) {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.SUCCESS_CODE);
        r.setMsg(ResponseCode.SUCCESS_MSG);
        r.setDatas(t);
        return r;
    }

    public static <T> R<T> failed() {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.FAILED_CODE);
        r.setMsg(ResponseCode.FAILED_MSG);
        return r;
    }

    public static <T> R<T> getErrorR(String msg) {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.ERROR_CODE);
        if (!StringUtils.isEmpty(msg)) {
            r.setMsg(msg);
        } else {

            r.setMsg(ResponseCode.ERROR_MSG);
        }

        return r;
    }

    public static <T> R<T> getErrorObjectR(T t, String msg) {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.ERROR_CODE);
        if (!StringUtils.isEmpty(msg)) {
            r.setMsg(msg);
        } else {
            r.setMsg(ResponseCode.ERROR_MSG);
        }
        r.setDatas(t);
        return r;
    }

    public static <T> R<T> getResultR(String code, String msg) {
        R<T> r = new R<T>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> getResultR(String code, String msg, T t) {
        R<T> r = new R<T>();
        r.setCode(code);
        r.setMsg(msg);
        r.setDatas(t);
        return r;
    }

    public static <T> Boolean isSuccessR(R<T> r) {
        if (null == r) {
            return false;
        }
        return "0".equals(r.getCode());
    }

    public static <T> R<T> getServiceErrorR(String serviceName) {
        R<T> r = new R<T>();
        r.setCode(ResponseCode.CUSTOM_CODE);
        r.setMsg(serviceName + "服务不通，请检查服务");
        return r;
    }

    /**
     * @param count 保存、更新、删除条数
     * @return
     */
    public static <T> R<T> getResult(int count) {
        R<T> r = new R<T>();
        if (count > 0) {
            r.setCode(ResponseCode.SUCCESS_CODE);
            r.setMsg(ResponseCode.SUCCESS_MSG);
            return r;
        } else {
            r.setCode(ResponseCode.ERROR_CODE);
            r.setMsg(ResponseCode.ERROR_MSG);
            return r;
        }
    }


}
