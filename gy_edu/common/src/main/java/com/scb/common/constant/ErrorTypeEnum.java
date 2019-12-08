package com.scb.common.constant;

import com.scb.common.exception.ErrorType;
import lombok.Getter;

/**
 * 失败消息枚举。
 * @author R)
 */
@Getter
public enum ErrorTypeEnum implements ErrorType {

    // 错误值及消息
    SYSTEM_BUSY(10020, "系统繁忙，请稍后再试"),
    ARGUMENT_NOT_VALID(10400, "请求参数校验失败"),
    LOGIN_STATUE_EXPIRES(10410, "您的登录信息已过期"),
    ACCOUNT_NO_AUTHENTICATION(10430, "请先进行登录"),
    ACCOUNT_NO_AUTHORIZATION(10450, "您缺少该功能的权限"),
    ACCOUNT_NEED_ACTIVE(10460, "您的账号需要进行激活"),
    UPLOAD_FILE_SIZE_LIMIT(10044, "上传文件大小超过限制"),
    SYSTEM_ERROR(10500, "系统异常"),
    DUPLICATED(10501, "已存在相同的内容"),
    SQL_EXECUTE_FAIL(10800, "发生未知错误")
    ;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误提示消息
     */
    private String message;

    ErrorTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
