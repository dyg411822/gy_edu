package com.scb.sysadmin.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scb.sysadmin.model.po.Settings;

/**
 * <p>
 * 系统配置信息表 服务类
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
public interface SettingsService extends IService<Settings> {

    /**
     * 系统配置信息管理服务。
     * @param module 模块名称
     * @return 配置列表
     */
    List<Settings> listSettingsByModule(String module);

    /**
     * 更新配置信息。
     * @param module 模块名称
     * @param obj 配置对象
     */
    void updateSettings(String module, Object obj);

}
