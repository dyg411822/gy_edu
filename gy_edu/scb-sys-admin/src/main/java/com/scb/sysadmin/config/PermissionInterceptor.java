package com.scb.sysadmin.config;

import com.scb.common.constant.Constant;
import com.scb.common.constant.ErrorTypeEnum;
import com.scb.common.util.UserContextHolder;
import com.scb.common.vo.Result;
import com.scb.sysadmin.annotation.PermissionSign;
import com.scb.sysadmin.constant.PermissionLogicEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 鉴权。
 * 判断请求用户的角色是否拥有功能相应的权限。
 * @author R)
 */
@Aspect
@Component
@Order(11000)
public class PermissionInterceptor {

    /**
     * 切点，拦截控制器
     */
    @Pointcut("execution(com.scb.common.vo.Result com.scb.sysadmin.web.*.*(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public Object checkPermission(ProceedingJoinPoint pjp) throws Throwable {
        // 获取请求方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
 
        // 获取权限注解
        PermissionSign permissionSign = method.getAnnotation(PermissionSign.class);
        // 无注解则不需要进行权限校验
        if (permissionSign == null) {
            return pjp.proceed();
        }

        @SuppressWarnings("unchecked")
        List<String> roleList = (List<String>) UserContextHolder.getInstance().getContext().get(Constant.USER_ROLE);

        String[] permissions = permissionSign.value();
        PermissionLogicEnum logic = permissionSign.logic();
        List<String> permissionList = Arrays.asList(permissions);

        // 是否允许放行。
        boolean permit = false;

        if (logic == PermissionLogicEnum.AND) {
            // 必须符合所有权限
            permit = roleList.containsAll(permissionList);
        } else {
            // 只要符合其中一个权限
            for (String p : permissionList) {
                if (roleList.contains(p)) {
                    permit = true;
                    break;
                }
            }
        }

        if (permit) {
            return pjp.proceed();
        }

        return Result.fail(ErrorTypeEnum.ACCOUNT_NO_AUTHORIZATION);
    }

}
