package com.scb.sysadmin.service;

import com.scb.common.vo.OrderSettings;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class OrderSettingsServiceTest {

    @Resource
    private OrderSettingsService orderSettingsService;

    @Test
    public void getOrderSettings() {
        OrderSettings orderSettings = orderSettingsService.getOrderSettings();
        assertNotNull(orderSettings);
        log.info(orderSettings.toString());
    }

    @Test
    public void updateOrderSettings() {
        // 全部内容更新
        OrderSettings orderSettings = orderSettingsService.getOrderSettings();
        orderSettings.setCourseValidity(355);
        orderSettingsService.updateOrderSettings(orderSettings);

        // 部分内容更新
        OrderSettings otherOrderSetting = new OrderSettings();
        otherOrderSetting.setOrderExpire(9);
        orderSettingsService.updateOrderSettings(otherOrderSetting);
    }
}