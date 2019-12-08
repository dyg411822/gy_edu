package com.scb.sysadmin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.scb.common.util.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 检阅信息处理。
 * @author R)
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final String FIELD_CREATED_TIME = "createdTime";
    private static final String FIELD_CREATED_BY = "createdBy";
    private static final String FIELD_UPDATED_TIME = "updatedTime";
    private static final String FIELD_UPDATED_BY = "updatedBy";

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = getUserId();
        Date now = Date.from(ZonedDateTime.now().toInstant());
        log.debug("start insert fill userId:{}", userId);
        this.setFieldValByName(FIELD_CREATED_TIME, now, metaObject);
        this.setFieldValByName(FIELD_CREATED_BY, userId, metaObject);
        this.setFieldValByName(FIELD_UPDATED_TIME, now, metaObject);
        this.setFieldValByName(FIELD_UPDATED_BY, userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = getUserId();
        log.debug("start update fill userId:{}", userId);
        this.setFieldValByName(FIELD_UPDATED_TIME, Date.from(ZonedDateTime.now().toInstant()), metaObject);
        this.setFieldValByName(FIELD_UPDATED_BY, userId, metaObject);
    }

    /**
     * 获取当前操作的用户编号。
     */
    private Long getUserId() {
        return UserContextHolder.getInstance().getUserId();
    }

    /**
     * 获取当前操作的用户名称。
     */
    private String getUsername() {
        return UserContextHolder.getInstance().getUsername();
    }
}