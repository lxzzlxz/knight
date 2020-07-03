package com.fm.demo.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Reflict {
    public void test() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        this.reflect(5);
    }
    // o: RoomController$CreateRoomRequestBody@232 "RoomController.CreateRoomRequestBody(id=3, name=3, type=0, comments=null)
    // 通过反射，将结果以map形式返回
    private Map<String, Object> reflect(Object o)
            throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        String className = o.getClass().getName();
        Class clazz = Class.forName(className);
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取对象属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);// 私有属性设置访问权限
            String name = field.getName();
            Object resultValue = field.get(o);
            result.put(name, resultValue);
        }
        return result;
    }
}