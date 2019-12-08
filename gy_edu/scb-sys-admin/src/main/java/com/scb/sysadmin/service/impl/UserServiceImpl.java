package com.scb.sysadmin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scb.sysadmin.dao.UserMapper;
import com.scb.sysadmin.model.po.User;
import com.scb.sysadmin.service.UserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Bean
 * @since 2019-10-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
