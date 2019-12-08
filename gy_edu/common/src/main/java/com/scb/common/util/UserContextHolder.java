package com.scb.common.util;

import com.scb.common.constant.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 用户信息上下文。方便方法之间传输非业务值。
 * @author R)
 */
public class UserContextHolder implements AutoCloseable {

    private static final Map<String, Object> DEFAULT_CONTENT = new HashMap<>(1);

    static {
        DEFAULT_CONTENT.put("userId", Constant.DEFAULT_USER_ID);
    }

    private static final UserContextHolder INSTANCE = new UserContextHolder();
    private ThreadLocal<Map<String, Object>> threadLocal;

    private UserContextHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    public static UserContextHolder getInstance() {
        return INSTANCE;
    }

    /**
     * 配置自定义上下文。
     * @param map 键值对
     */
    public void setContext(Map<String, Object> map) {
        threadLocal.set(map);
    }

    /**
     * 获取配置上下文。
     * @return 键值对
     */
    public Map<String, Object> getContext() {
        return threadLocal.get();
    }

    /**
     * 使用完成之后需要进行删除。
     */
    public void clear() {
        threadLocal.remove();
    }

    /**
     * 获取上下文中的用户名。
     * @return 用户名
     */
    public String getUsername() {
        return Optional.ofNullable(threadLocal.get()).orElse(new HashMap<>(0)).get(Constant.USERNAME) + "";
    }

    /**
     * 获取上下文中的用户编号。
     * 如果 id 不存在，则返回默认的 id。
     * @return 用户编号
     */
    public Long getUserId() {
        Object userId = Optional.ofNullable(threadLocal.get()).orElse(DEFAULT_CONTENT).get(Constant.USER_ID);
        if (userId != null) {
            return Long.valueOf(userId + "");
        }
        return Constant.DEFAULT_USER_ID;
    }

    /**
     * 使用自动关闭的功能，清理资源。
     */
    @Override
    public void close() {
        clear();
    }
}
