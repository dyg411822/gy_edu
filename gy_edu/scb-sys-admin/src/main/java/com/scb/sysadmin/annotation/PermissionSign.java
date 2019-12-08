package com.scb.sysadmin.annotation;

import com.scb.sysadmin.constant.PermissionLogicEnum;

import java.lang.annotation.*;

/**
 * 路径权限注解。
 * @author R)
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionSign {
    String[] value();
    PermissionLogicEnum logic() default PermissionLogicEnum.AND;
}
