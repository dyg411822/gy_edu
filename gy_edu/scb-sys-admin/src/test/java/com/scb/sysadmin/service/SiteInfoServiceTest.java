package com.scb.sysadmin.service;

import com.scb.common.vo.SiteInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class SiteInfoServiceTest {

    @Resource
    private SiteInfoService siteInfoService;

    @Test
    public void getSiteInfo() {
        SiteInfo siteInfo = siteInfoService.getSiteInfo();
        assertNotNull(siteInfo);
        log.info(siteInfo.toString());
    }

    @Test
    public void updateSiteInfo() {
        // 测试完整的保存
        SiteInfo siteInfo = siteInfoService.getSiteInfo();
        siteInfo.setName("测试 AA");
        siteInfoService.updateSiteInfo(siteInfo);

        // 测试部分保存
        SiteInfo otherSiteInfo = new SiteInfo();
        otherSiteInfo.setName("测试 AA");
        otherSiteInfo.setCopyright("ABC");
        siteInfoService.updateSiteInfo(otherSiteInfo);
    }
}