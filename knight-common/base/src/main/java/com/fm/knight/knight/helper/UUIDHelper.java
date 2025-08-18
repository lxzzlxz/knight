package com.fm.knight.knight.helper;

import java.util.UUID;

public class UUIDHelper {

    public static String getUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
