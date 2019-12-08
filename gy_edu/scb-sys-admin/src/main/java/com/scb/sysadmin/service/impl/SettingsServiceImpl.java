package com.scb.sysadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scb.common.exception.SqlExecuteException;
import com.scb.common.util.BeanUtils;
import com.scb.sysadmin.dao.SettingsMapper;
import com.scb.sysadmin.model.po.Settings;
import com.scb.sysadmin.service.SettingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 配置信息服务。
 * @author R)
 */
@Service
public class SettingsServiceImpl extends ServiceImpl<SettingsMapper, Settings> implements SettingsService {

    @Resource
    private SettingsMapper settingsMapper;

    @Override
    public List<Settings> listSettingsByModule(String module) {
        Settings settings = new Settings();
        settings.setModule(module);
        Wrapper<Settings> param = new QueryWrapper<>(settings);
        List<Settings> settingsList = settingsMapper.selectList(param);
        return settingsList;
    }

    @Override
    public void updateSettings(String module, Object orderSettings) {
        // 把对象转换成 map
        Map<String, Object> map = BeanUtils.beanToMap(orderSettings, false, true);
        int resCount = map.size();
        int destCount = 0;

        // 遍历更新
        Settings settings;
        for (Map.Entry<String, Object> entries : map.entrySet()) {
            settings = new Settings();
            settings.setModule(module);
            settings.setKey(entries.getKey());
            // 此处对象转 map 时已经筛选掉了为 null 的部分，此处不需要验证。
            settings.setValue(entries.getValue().toString());
            int count = settingsMapper.updateByModuleAndKey(settings);
            destCount += count;
        }
        if (resCount != destCount) {
            throw new SqlExecuteException("更新失败");
        }
    }
}
