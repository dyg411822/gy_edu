package com.scb.sysadmin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scb.sysadmin.model.po.Settings;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 系统配置信息表 Mapper 接口
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
public interface SettingsMapper extends BaseMapper<Settings> {

    /**
     * 更新单个配置内容。
     * @param settings 配置节
     * @return 执行成功记录数
     */
    @Update("UPDATE sys_settings SET `value` = #{value} WHERE module = #{module} AND `key` = #{key}")
    int updateByModuleAndKey(Settings settings);

}
