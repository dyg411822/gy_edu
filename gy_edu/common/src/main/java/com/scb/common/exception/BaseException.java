package com.scb.common.exception;

import com.scb.common.constant.ErrorTypeEnum;
import lombok.Getter;

/**
 * 基础异常信息。（默认系统错误）
 * @author R)
 */
@Getter
public class BaseException extends RuntimeException {

    private final ErrorType errorType;

    public BaseException() {
        this.errorType = ErrorTypeEnum.SYSTEM_ERROR;
    }

    public BaseException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public BaseException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

}
