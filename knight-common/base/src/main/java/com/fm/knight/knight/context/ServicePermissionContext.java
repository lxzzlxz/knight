package com.fm.knight.knight.context;

import java.util.Set;

public class ServicePermissionContext {

    private static Set<String> serviceAuthorized;

    public static void setServiceAuthorized(Set<String> serviceAuthorized) {
        ServicePermissionContext.serviceAuthorized = serviceAuthorized;
    }

    public static Set<String> getServiceAuthorized(){
        return ServicePermissionContext.serviceAuthorized;
    }
}
