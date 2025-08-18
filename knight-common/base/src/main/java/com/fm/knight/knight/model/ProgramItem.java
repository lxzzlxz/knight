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
public class ProgramItem extends BaseLamModel {

    private static final long serialVersionUID = 3863120443520541921L;
    @Schema(description = "数据范围ID 必填")
    private Long programId;

    @Schema(description = "纬度Code 必填")
    private String dimensionCode;

    @Schema(description = "维度值 必填")
    private String dimensionValue;
    private List<String> dimensionValueList;

    @Schema(description = "是否全选 必填")
    private Boolean isAll;

}
