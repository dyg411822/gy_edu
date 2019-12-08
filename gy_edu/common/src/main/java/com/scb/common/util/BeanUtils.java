package com.scb.common.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.Map;

/**
 * Bean Object 转换操作。
 * @author R)
 */
public class BeanUtils {

    private BeanUtils() {}

    /**
     * Map 转为为 Bean 对象
     * @param map 字段及内容
     * @param beanClass Bean 对应的类型
     * @return 相应类型的对象
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass) {
        return BeanUtil.mapToBean(map, beanClass, false);
    }

    /**
     * Bean 对象转 Map
     * @param bean 需要转换的对象
     * @param isToUnderlineCase 是否使用下划线加小写字母代替大写字母
     * @param ignoreNullValue 是否忽略为 null 的值
     * @return 类的 map 形式
     */
    public static Map<String, Object> beanToMap(Object bean, boolean isToUnderlineCase, boolean ignoreNullValue) {
        return BeanUtil.beanToMap(bean, isToUnderlineCase, ignoreNullValue);
    }

}
