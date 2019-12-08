package com.scb.sysadmin.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * <p>
 * 城市
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
@TableName("sys_city")
public class City extends Model<City> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer cityId;
    private String cityName;
    private Integer fatherId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    protected Serializable pkVal() {
        return this.id;
    }

    public String toString() {
        return "City{" +
        ", id=" + id +
        ", cityId=" + cityId +
        ", cityName=" + cityName +
        ", fatherId=" + fatherId +
        "}";
    }
}
