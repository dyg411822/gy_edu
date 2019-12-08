package com.scb.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单相关配置
 * @author R)
 */
@Data
public class OrderSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 课程订单为付款取消间隔。
     */
    private Integer orderExpire;
    /**
     * 积分订单自动收货确认间隔。
     */
    private Integer orderReceivingExpire;
    /**
     * 购买课程的有效期。
     */
    private Integer courseValidity;
}
