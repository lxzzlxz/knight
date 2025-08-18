package com.fm.knight.knight.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleProgram extends BaseLamModel {
    private static final long serialVersionUID = -7691157659279568839L;
    @Schema(description = "用户ID 必填")
    private Long userId;
    private List<Long> userIds;
    @Schema(description = "角色ID 必填")
    private Long roleId;
    @Schema(description = "角色名称")
    private String roleName;
    @Schema(description = "数据范围ID 必填")
    private Long programId;
    @Schema(description = "状态，1：启用，2：锁定 必填")
    private Integer status;
    @Schema(description = "维度值")
    private String dimensionValue;
}

