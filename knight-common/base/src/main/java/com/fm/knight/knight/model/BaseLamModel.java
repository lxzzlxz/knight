package com.fm.knight.knight.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fm.knight.knight.context.RequestContext;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BaseLamModel {
    @Schema(description = "回收商结算单确认表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @Schema(description = "创建时间")
    @TableField(value = "creation_date")
    private Date creationDate;
    @Schema(description = "创建人")
    @TableField(value = "created_by")
    private Long createdBy;
    @Schema(description = "修改时间")
    @TableField(value = "last_update_date")
    private Date lastUpdateDate;
    @Schema(description = "修改人")
    @TableField(value = "last_updated_by")
    private Long lastUpdatedBy;
    @TableField(exist = false)
    private String lastUpdateUser;
    @TableField(exist = false)
    private String createUser;

    public void setCreateInfo() {
        UserPrinciple user = RequestContext.getCurrent().getUser();
        if (null != user) {
            this.createdBy = user.getId();
        }
        this.creationDate = new Date();
    }

    public void setUpdateInfo() {
        UserPrinciple user = RequestContext.getCurrent().getUser();
        if (null != user) {
            this.lastUpdatedBy = user.getId();
        }
        this.lastUpdateDate = new Date();
    }
}
