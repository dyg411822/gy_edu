package com.scb.common.exception;

/**
 * 错误类型
 * @author R)
 */
public interface ErrorType {

    /**
     * 错误码。
     */
    int getCode();

    /**
     * 错误消息
     */
    String getMessage();

}
