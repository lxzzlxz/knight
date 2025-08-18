package com.fm.knight.knight.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServicePermission extends BaseLamModel {

    private static final long serialVersionUID = 5292289745947751530L;

    private String serviceName;

    private String authorized;

}
