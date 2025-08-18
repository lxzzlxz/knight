package com.fm.knight.knight.helper;



import com.fm.knight.knight.model.R;
import com.fm.knight.knight.model.ResponseCode;

import java.util.HashMap;
import java.util.Map;

public class ResultMapHelper {

    public static Map<String,Object> getSuccessMap(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE,ResponseCode.SUCCESS_CODE);
        map.put(ResponseCode.MSG, ResponseCode.SUCCESS_MSG);
        return map;
    }

    public static Map<String,Object> getSuccessMap(Object obj){
        Map<String,Object> map = new HashMap<>(16);
        map.put(ResponseCode.CODE,ResponseCode.SUCCESS_CODE);
        map.put(ResponseCode.MSG, ResponseCode.SUCCESS_MSG);
        map.put("datas", obj);
        return map;
    }

    public static Map<String,Object> failed(){
    	Map<String,Object> map = new HashMap<String,Object>(16);
    	map.put(ResponseCode.CODE,ResponseCode.FAILED_CODE);
    	map.put(ResponseCode.MSG, ResponseCode.FAILED_MSG);
    	return map;
    }
    public static Map<String,Object> getErrorMap(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE,ResponseCode.ERROR_CODE);
        map.put(ResponseCode.MSG, ResponseCode.ERROR_MSG);
        return map;
    }
    public static Map<String,Object> getErrorMap(Object obj){
    	Map<String,Object> map = new HashMap<String,Object>(16);
    	map.put(ResponseCode.CODE,ResponseCode.ERROR_CODE);
    	map.put(ResponseCode.MSG, ResponseCode.ERROR_MSG);
    	map.put("datas", obj);
    	return map;
    }

    public static Map<String,Object> getNotLoginMap(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE,ResponseCode.NOT_LOGIN_CODE);
        map.put(ResponseCode.MSG, ResponseCode.NOT_LOGIN_MSG);
        return map;
    }

    public static Map<String,Object> getPermissionDenied(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE,ResponseCode.PERMISSION_DENIED_CODE);
        map.put(ResponseCode.MSG, ResponseCode.PERMISSION_DENIED_MSG);
        return map;
    }

    public static Map<String,Object> getServicePermissionDenied(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE,ResponseCode.SERVICE_PERMISSION_DENIED_CODE);
        map.put(ResponseCode.MSG, ResponseCode.SERVICE_PERMISSION_DENIED_MSG);
        return map;
    }

    public static Map<String,Object> getResultMap(String code, String msg, Object data){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE,code);
        map.put(ResponseCode.MSG, msg);
        map.put(ResponseCode.DATA, data);
        return map;
    }
    public static Map<String,Object> getLoginResultMap(String code, String msg, Object data){
    	Map<String,Object> map = new HashMap<String,Object>(16);
    	map.put(ResponseCode.CODE,code);
    	map.put(ResponseCode.MSG, msg);
    	map.put("datas", data);
    	return map;
    }

    public static Map<String,Object> getResultMap(String code, String msg){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE,code);
        map.put(ResponseCode.MSG, msg);
        return map;
    }

    public static Map<String,Object> getParameterErrorMap(){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE, ResponseCode.CUSTOM_CODE);
        map.put(ResponseCode.MSG, ResponseCode.PARAMETER_ERROR);
        return map;
    }

    public static Map<String,Object> getServiceErrorMap(String serviceName){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE, ResponseCode.CUSTOM_CODE);
        map.put(ResponseCode.MSG, serviceName+"服务不通，请检查服务");
        return map;
    }

    public static <T> R<T> getServiceErrorR(String serviceName){
        R<T> r = new R<T>();
        r.setCode(ResponseCode.CUSTOM_CODE);
        r.setMsg(serviceName+"服务不通，请检查服务");
        return r;
    }

    public static Map<String,Object> getCustomMap(String msg){
        Map<String,Object> map = new HashMap<String,Object>(16);
        map.put(ResponseCode.CODE, ResponseCode.CUSTOM_CODE);
        map.put(ResponseCode.MSG, msg);
        return map;
    }

    /**
     * @param count 保存、更新、删除条数
     * @return
     */
    public static Map<String,Object> getResult(int count){
    	 Map<String,Object> map = new HashMap<String,Object>(16);
    	if(count>0) {
    		map.put(ResponseCode.CODE, ResponseCode.SUCCESS_CODE);
    	    map.put(ResponseCode.MSG, ResponseCode.SUCCESS_MSG);
    	    return map;
    	}else {
    		map.put(ResponseCode.CODE, ResponseCode.ERROR_CODE);
    	    map.put(ResponseCode.MSG, ResponseCode.ERROR_MSG);
    	    return map;
    	}
    }

    /**
     * 判断返回map是否成功
     * @param resultMap
     * @return
     */
    public static Boolean isSuccess(Map<String, Object> resultMap){
        if(null != resultMap && "0".equals(resultMap.get("code"))){
            return true;
        }
        return false;
    }
}
