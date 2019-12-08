package com.scb.sysadmin.config;

import com.scb.common.constant.Constant;
import com.scb.common.constant.ErrorTypeEnum;
import com.scb.common.util.Assert;
import com.scb.common.util.UserContextHolder;
import com.scb.common.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 认证。
 * 通过网关之后，应该能从请求头中获取到用户的编号，名称和角色信息。
 * 附带这些信息则认为是有效请求，否则将认为是无效请求。
 * @author R)
 */
@Aspect
@Component
@Order(10500)
public class AuthenticationInterceptor {

    /**
     * 切点，拦截控制器
     */
    @Pointcut("execution(com.scb.common.vo.Result com.scb.sysadmin.web.*.*(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public Object checkAuthentication(ProceedingJoinPoint pjp) throws Throwable {

        // 获取请求信息
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        Assert.isNotNull(sra, "服务异常");
        HttpServletRequest request = sra.getRequest();

        Integer userId = request.getIntHeader(Constant.USER_ID);
        String username = request.getHeader(Constant.USERNAME);
        Enumeration<String> roles = request.getHeaders(Constant.USER_ROLE);

        List<String> roleList = new ArrayList<>();
        while (roles.hasMoreElements()) {
            roleList.add(roles.nextElement());
        }

        // 如果信息不齐全则表示认证失效。
        if (StringUtils.isEmpty(username) || roleList.size() == 0) {
            return Result.fail(ErrorTypeEnum.ACCOUNT_NO_AUTHENTICATION);
        }

        Map<String, Object> userInfo = new HashMap<>(3);
        userInfo.put(Constant.USER_ID, userId);
        userInfo.put(Constant.USERNAME, username);
        userInfo.put(Constant.USER_ROLE, roleList);

        try (UserContextHolder contextHolder = UserContextHolder.getInstance()) {
            contextHolder.setContext(userInfo);
            return pjp.proceed();
        }
    }

}
