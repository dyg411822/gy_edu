package com.scb.sysadmin.dao;

import com.scb.sysadmin.model.po.Settings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SettingsMapperTest {

    @Resource
    private SettingsMapper settingsMapper;

    @Test
    public void testAddSettings() {
        Settings settings = new Settings();
        settings.setModule("测试12");
        settings.setKey("KKK");
        settings.setValue("BBBBBB");

        int count = settingsMapper.insert(settings);
        assertEquals(1, count);

        // 保证模块 + 键唯一
        count = 0;
        try {
            count = settingsMapper.insert(settings);
        } catch (Exception e) {
            // Nothing view happened!
        }
        assertEquals(0, count);
    }

}