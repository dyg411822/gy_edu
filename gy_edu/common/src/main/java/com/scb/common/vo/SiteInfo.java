package com.scb.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 站点信息。
 * @author R)
 */
@Data
public class SiteInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 应用名称
     */
    private String name;
    /**
     * 网站标题
     */
    private String title;
    /**
     * 全局关键字
     */
    private String keywords;
    /**
     * 全局站点描述
     */
    private String description;
    /**
     * 网站 logo 地址
     */
    private String logo;
    /**
     * 网站 favicon 地址
     */
    private String favicon;
    /**
     * 网站微信二维码图片地址
     */
    private String wxCode;
    /**
     * 热线电话
     */
    private String hotLine;
    /**
     * 网站备案号码
     */
    private String icpNumber;
    /**
     * 网站版权信息
     */
    private String copyright;
    /**
     * 网站 QQ 客服号码
     */
    private String qqServer;
}
