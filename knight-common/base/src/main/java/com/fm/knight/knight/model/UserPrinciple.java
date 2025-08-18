package com.fm.knight.knight.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class UserPrinciple extends BaseLamModel {

    private static final long serialVersionUID = -8330298842079308477L;

    @Schema(description = "用户ID 必填")
    private String userId;
    @Schema(description = "用户名称 登录必填，新增必填")
    private String username;

    @Schema(description = "用户密码，登录必填md5加密，新增必填")
    private String password;

    @Schema(description = "用户电话")
    private String phone;

    @Schema(description = "用户邮箱")
    private String mail;

    @Schema(description = "身份证号")
    private String idCardNO;

    @Schema(description = "是否修改密码，1：是，2：否")
    private Integer passwordStatus;

    @Schema(description = "用户类型")
    private Integer userType;

}
