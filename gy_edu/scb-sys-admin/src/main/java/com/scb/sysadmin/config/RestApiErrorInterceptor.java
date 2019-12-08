package com.scb.sysadmin.config;

import com.scb.common.util.BaseErrorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 统一 Rest API 错误返回值
 * @author R)
 */
@Aspect
@Component
@Order(10000)
public class RestApiErrorInterceptor extends BaseErrorInterceptor {

    /**
     * 切点，拦截返回值为 Result 的控制器方法。
     */
    @Pointcut("execution(com.scb.common.vo.Result com.scb.sysadmin.web.*.*(..))")
    public void pointcut() {}

    @Around("pointcut()")
    @Override
    public Object around(ProceedingJoinPoint pjp) {
        return super.around(pjp);
    }
}
