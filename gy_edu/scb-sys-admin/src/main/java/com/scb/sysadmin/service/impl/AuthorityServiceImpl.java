package com.scb.sysadmin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scb.sysadmin.dao.AuthorityMapper;
import com.scb.sysadmin.model.po.Authority;
import com.scb.sysadmin.service.AuthorityService;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

}
