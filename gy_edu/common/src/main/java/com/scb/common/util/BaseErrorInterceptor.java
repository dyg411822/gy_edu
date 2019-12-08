package com.scb.common.util;

import com.scb.common.exception.BaseException;
import com.scb.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 统一错误拦截器。
 * 在其他项目中使用可以直接继承此类，并添加相应的拦截逻辑，示例：
 * @<code>
 * @Aspect
 * @Component
 * public class RestApiErrorInterceptor extends BaseErrorInterceptor {
 *
 *     // 切点，拦截返回值为 Result 的控制器方法。
 *     @Pointcut("execution(com.scb.common.vo.Result com.scb.member.web.*.*(..))")
 *     public void pointcut() {}
 *
 *     @Around("pointcut()")
 *     @Override
 *     public Object around(ProceedingJoinPoint pjp) {
 *         return super.around(pjp);
 *     }
 * }
 * </code>
 * @author R)
 */
@Slf4j
public abstract class BaseErrorInterceptor {

    /**
     * 统一错误拦截处理（包围）
     */
    protected Object around(ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (BaseException exception) {
            // 处理自定义错误部分内容
            return Result.fail(exception.getErrorType());
        } catch (Throwable throwable) {
            log.error("服务处理失败：", throwable);
        }
        // 其他情况返回统一失败提示
        return Result.fail();
    }

}
