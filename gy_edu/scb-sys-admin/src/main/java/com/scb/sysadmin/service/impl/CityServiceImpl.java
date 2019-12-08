package com.scb.sysadmin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scb.sysadmin.dao.CityMapper;
import com.scb.sysadmin.model.po.City;
import com.scb.sysadmin.service.CityService;

/**
 * <p>
 * 城市 服务实现类
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {

}
