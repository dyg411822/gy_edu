package com.scb.sysadmin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scb.sysadmin.dao.RoleMapper;
import com.scb.sysadmin.model.po.Role;
import com.scb.sysadmin.service.RoleService;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
