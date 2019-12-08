package com.scb.sysadmin.service;

import com.scb.common.vo.SiteInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 站点信息服务。
 * @author R);
 */
@RestController
public interface SiteInfoService {

    /**
     * 模块名称。
     */
    String MODULE_NAME = "siteInfo";

    /**
     * 获取站点信息。
     * @return 站点信息
     */
    @GetMapping("/xx")
    SiteInfo getSiteInfo();

    /**
     * 更新站点信息。
     * 只有提供的部分被更新。不需要更新的部分可设置为 null
     * @param siteInfo 站点信息
     */
    void updateSiteInfo(SiteInfo siteInfo);

}
