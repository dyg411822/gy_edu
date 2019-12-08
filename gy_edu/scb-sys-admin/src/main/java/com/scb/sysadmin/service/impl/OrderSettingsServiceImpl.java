package com.scb.sysadmin.service.impl;

import com.scb.common.util.BeanUtils;
import com.scb.common.vo.OrderSettings;
import com.scb.sysadmin.model.po.Settings;
import com.scb.sysadmin.service.OrderSettingsService;
import com.scb.sysadmin.service.SettingsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 订单预设配置服务。
 * @author R)
 */
@Service
public class OrderSettingsServiceImpl implements OrderSettingsService {

    @Resource
    private SettingsService settingsService;

    @Cacheable(
        cacheNames = "SYSTEM::SETTINGS",
        key = "'[" + MODULE_NAME + "]'"
    )
    @Override
    public OrderSettings getOrderSettings() {
        // 获取模块为 MODULE_NAME 的配置列表。
        List<Settings> settingsList = settingsService.listSettingsByModule(MODULE_NAME);

        // 转化为 key - value 的形式，后续操作不需要 MODULE_NAME 了。
        Map<String, Object> settingsMap = settingsList.stream()
            .collect(Collectors.toMap(Settings::getKey, Settings::getValue));

        // Map 转换为 Bean 并返回。
        return BeanUtils.mapToBean(settingsMap, OrderSettings.class);
    }

    @CacheEvict(
        cacheNames = "SYSTEM::SETTINGS",
        key = "'[" + MODULE_NAME + "]'"
    )
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderSettings(OrderSettings orderSettings) {
        settingsService.updateSettings(MODULE_NAME, orderSettings);
    }
}
