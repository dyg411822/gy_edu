package com.scb.common.util;

/**
 * @author R)
 */
public class Assert {

    /**
     * 禁止泛化
     */
    private Assert() {
    }

    /**
     * 当对象为空时抛出错误。
     *
     * @param obj     目标对象
     * @param message 提示消息
     */
    public static void isNotNull(Object obj, String message) {
        if (null == obj) {
            throw new IllegalArgumentException(message);
        }
    }

}
