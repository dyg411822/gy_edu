package com.scb.sysadmin.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 * 系统配置信息表
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
@TableName("sys_settings")
public class Settings extends Model<Settings> {

    private static final long serialVersionUID = 1L;

    /**
     * 模块
     */
    private String module;
    /**
     * 配置的键
     */
    @TableField("`key`")
    private String key;
    /**
     * 配置的值
     */
    @TableField("`value`")
    private String value;


    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Settings{" +
        ", module=" + module +
        ", key=" + key +
        ", value=" + value +
        "}";
    }
}
