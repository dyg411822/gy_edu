package com.scb.sysadmin.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 * PO 公共字段
 * @author R)
 */
@Data
abstract class BasePO {

    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;
    @TableField(fill = FieldFill.INSERT)
    private Integer createdBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updatedBy;

    @TableLogic
    private Boolean deleted;

}
