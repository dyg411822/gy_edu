package com.scb.common.exception;

import com.scb.common.constant.ErrorTypeEnum;

/**
 * SQL 执行错误
 * @author R)
 */
public class SqlExecuteException extends BaseException {

    public SqlExecuteException() {
        super(ErrorTypeEnum.SQL_EXECUTE_FAIL);
    }

    public SqlExecuteException(String message) {
        super(ErrorTypeEnum.SYSTEM_BUSY, message);
    }

    public SqlExecuteException(String message, Throwable cause) {
        super(ErrorTypeEnum.SQL_EXECUTE_FAIL, message, cause);
    }

}
