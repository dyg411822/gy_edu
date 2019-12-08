package com.scb.common.vo;

import com.scb.common.constant.ErrorTypeEnum;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ResultTest {

    private static final String SUCCESS_MESSAGE = "成功";
    private static final String FAIL_MESSAGE = "处理失败";

    @Test
    public void testSucceed() {
        Object obj = new HashMap<>();
        Result result = Result.succeed();
        assertEquals(Result.SUCCESS_CODE, result.getCode());
        assertNull(result.getData());

        // 只提供返回数据
        result = Result.succeed(obj);
        assertEquals(Result.SUCCESS_CODE, result.getCode());
        assertSame(obj, result.getData());

        // 同时提供返回数据和提示消息
        result = Result.succeed(SUCCESS_MESSAGE, obj);
        assertEquals(Result.SUCCESS_CODE, result.getCode());
        assertEquals(SUCCESS_MESSAGE, result.getMessage());
        assertSame(obj, result.getData());
    }

    @Test
    public void testFail() {
        Object obj = new HashMap<>();
        Result result = Result.fail();
        assertEquals(Result.FAILURE_CODE, result.getCode());
        assertNull(result.getData());

        // 只提供提示消息，data 为空
        result = Result.fail(FAIL_MESSAGE);
        assertEquals(Result.FAILURE_CODE, result.getCode());
        assertEquals(FAIL_MESSAGE, result.getMessage());
        assertNull(result.getData());

        // 同时提供消息和返回数据
        result = Result.fail(FAIL_MESSAGE, obj);
        assertEquals(Result.FAILURE_CODE, result.getCode());
        assertEquals(FAIL_MESSAGE, result.getMessage());
        assertSame(obj, result.getData());

        // 测试特定错误方式
        ErrorTypeEnum systemErr = ErrorTypeEnum.SYSTEM_ERROR;
        result = Result.fail(systemErr);
        assertEquals(systemErr.getCode(), result.getCode());
        assertEquals(systemErr.getMessage(), result.getMessage());
    }
}
