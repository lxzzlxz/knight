package com.fm.knight.knight.context;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fm.knight.knight.model.UserPrinciple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class Current implements Serializable {
    @JSONField(serialize = false)
    @JsonIgnore
    private String token;

    @JSONField(serialize = false)
    @JsonIgnore
    private Boolean getData = false;

    @JSONField(serialize = false)
    @JsonIgnore
    private String domain;

    @JSONField(serialize = false)
    @JsonIgnore
    private HttpServletRequest request;

    @JSONField(serialize = false)
    @JsonIgnore
    private HttpServletResponse response;

    private UserPrinciple user;
    private Set<String> permissions;

    @JSONField(serialize = false)
    @JsonIgnore
    private Boolean permissionChecked = false;

    @JSONField(serialize = false)
    @JsonIgnore
    private StringBuilder serviceLog = new StringBuilder();

    @JSONField(serialize = false)
    @JsonIgnore
    private StringBuilder accessLog = new StringBuilder();

    @JSONField(serialize = false)
    @JsonIgnore
    private StringBuilder returnLog = new StringBuilder();

}
