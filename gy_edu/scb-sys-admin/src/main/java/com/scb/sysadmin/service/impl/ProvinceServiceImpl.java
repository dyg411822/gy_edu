package com.scb.sysadmin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scb.sysadmin.dao.ProvinceMapper;
import com.scb.sysadmin.model.po.Province;
import com.scb.sysadmin.service.ProvinceService;

/**
 * <p>
 * 省份 服务实现类
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
@Service
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper, Province> implements ProvinceService {

}
