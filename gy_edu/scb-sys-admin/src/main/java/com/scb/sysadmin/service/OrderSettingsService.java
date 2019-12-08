package com.scb.sysadmin.service;

import com.scb.common.vo.OrderSettings;

/**
 * 订单设置预设服务。
 * @author R);
 */
public interface OrderSettingsService {
    /**
     * 模块名称。
     */
    String MODULE_NAME = "order";

    /**
     * 获取订单预设配置信息。
     * @return 订单预设配置
     */
    OrderSettings getOrderSettings();

    /**
     * 更新订单预设配置信息。
     * @param orderSettings 订单预设配置
     */
    void updateOrderSettings(OrderSettings orderSettings);

}
