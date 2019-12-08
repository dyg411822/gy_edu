package com.scb.sysadmin.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scb.sysadmin.model.po.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class RoleMapperTest {

    @Resource
    private RoleMapper roleMapper;

    @Test
    public void testAddRole() {
        Role role = new Role();
        role.setName("测试1");
        role.setRemark("");
        role.setEnabled(true);

        int count = roleMapper.insert(role);
        assertEquals(1, count);
    }

    /**
     * 测试分页功能。
     */
    @Test
    public void testListPage() {
        IPage<Role> page = new Page<>(1, 10);
        IPage<Role> result = roleMapper.selectPage(page, null);
        List<Role> roleList = result.getRecords();
        log.info(roleList.toString());
        Assert.assertTrue(roleList.size() > 0);
    }
}