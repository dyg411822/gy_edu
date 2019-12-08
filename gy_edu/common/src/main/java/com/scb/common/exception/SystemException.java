package com.scb.common.exception;

import com.scb.common.constant.ErrorTypeEnum;

/**
 * 系统异常。
 * @author R)
 */
public class SystemException extends BaseException {

    public SystemException() {
        super(ErrorTypeEnum.SYSTEM_ERROR);
    }

    public SystemException(String message) {
        super(ErrorTypeEnum.SYSTEM_ERROR, message);
    }

    public SystemException(String message, Throwable cause) {
        super(ErrorTypeEnum.SYSTEM_ERROR, message, cause);
    }

}
