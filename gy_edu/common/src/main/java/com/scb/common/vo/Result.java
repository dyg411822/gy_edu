package com.scb.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.scb.common.exception.BaseException;
import com.scb.common.exception.ErrorType;
import lombok.Data;

/**
 * 统一返回结果集。
 * @author R)
 */
@Data
public class Result {

    public static final int SUCCESS_CODE = 10000;
    public static final int FAILURE_CODE = 10001;

    static final String DEFAULT_SUCCESS_MESSAGE = "处理成功";
    static final String DEFAULT_FAILURE_MESSAGE = "处理失败";

    /**
     * 处理码
     */
    private int code;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 返回的数据主体。
     * 当值为 null 时不生成在 Json 中。
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    private static Result getInstance(int code, String message, Object data) {
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    /**
     * 处理成功返回值。
     *
     * @param message 提示消息
     * @param data    返回的数据
     */
    public static Result succeed(String message, Object data) {
        return getInstance(SUCCESS_CODE, message, data);
    }

    /**
     * 处理成功返回值。
     *
     * @param data 返回的数据
     */
    public static Result succeed(Object data) {
        return succeed(DEFAULT_SUCCESS_MESSAGE, data);
    }

    /**
     * 处理成功返回值。
     * 不带 data
     */
    public static Result succeed() {
        return succeed(null);
    }

    /**
     * 处理失败返回值。
     *
     * @param message 提示消息
     * @param data    返回数据
     */
    public static Result fail(String message, Object data) {
        return getInstance(FAILURE_CODE, message, data);
    }

    /**
     * 处理失败返回值。
     *
     * @param message 提示消息
     */
    public static Result fail(String message) {
        return fail(message, null);
    }

    /**
     * 处理失败返回值。
     */
    public static Result fail() {
        return fail(DEFAULT_FAILURE_MESSAGE);
    }

    /**
     * 特定的失败返回值。
     * @param failureType 失败类型。
     */
    public static Result fail(ErrorType failureType) {
        return getInstance(failureType.getCode(), failureType.getMessage(), null);
    }

    /**
     * 特定的失败返回值。
     * @param failureType 失败类型。
     * @param message 替换的提示消息。
     */
    public static Result fail(ErrorType failureType, String message) {
        return getInstance(failureType.getCode(), message, null);
    }

    public static Result fail(BaseException exception) {
        return fail(exception.getErrorType());
    }

    @JsonIgnore
    public boolean isSucceed() {
        return code == SUCCESS_CODE;
    }

    @JsonIgnore
    public boolean isFail() {
        return code != SUCCESS_CODE;
    }
}
