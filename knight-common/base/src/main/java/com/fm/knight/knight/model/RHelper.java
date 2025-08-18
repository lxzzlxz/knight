package com.fm.knight.knight.model;

import org.springframework.util.StringUtils;

public class RHelper {
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
}
