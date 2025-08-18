package com.fm.knight.knight.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Program extends BaseLamModel {

    private static final long serialVersionUID = -2444799895665986800L;
    @Schema(description = "数据范围名称 必填")
    private String programName;

    private String scope;

    @Schema(description = "状态，1：启用，2：锁定 必填")
    private Integer status;

    private List<Long> programIds;

    @Schema(description = "数据范围详情 必填 可以为空数组")
    private List<ProgramItem> programItems;

}
