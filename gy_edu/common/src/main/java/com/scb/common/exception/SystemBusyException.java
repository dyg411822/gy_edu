package com.scb.common.exception;

import com.scb.common.constant.ErrorTypeEnum;

/**
 * 系统繁忙，可用于熔断。
 * @author R)
 */
public class SystemBusyException extends BaseException {

    public SystemBusyException() {
        super(ErrorTypeEnum.SYSTEM_BUSY);
    }

    public SystemBusyException(String message) {
        super(ErrorTypeEnum.SYSTEM_BUSY, message);
    }

    public SystemBusyException(String message, Throwable cause) {
        super(ErrorTypeEnum.SYSTEM_BUSY, message, cause);
    }
}
