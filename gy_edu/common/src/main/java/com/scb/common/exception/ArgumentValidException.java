package com.scb.common.exception;

import com.scb.common.constant.ErrorTypeEnum;

/**
 * 参数校验错误返回值。
 * @author R)
 */
public class ArgumentValidException extends BaseException {

    public ArgumentValidException() {
        super(ErrorTypeEnum.ARGUMENT_NOT_VALID);
    }

    public ArgumentValidException(String message) {
        super(ErrorTypeEnum.ARGUMENT_NOT_VALID, message);
    }

    public ArgumentValidException(String message, Throwable cause) {
        super(ErrorTypeEnum.ARGUMENT_NOT_VALID, message, cause);
    }

}
