package com.yksys.isystem.common.core.utils;

import com.yksys.isystem.common.core.exception.ParameterException;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-25 09:41
 **/
public class AssertUtil {

    /**
     * 判断对象不为空
     * @param fieldName
     * @param msg
     */
    public static void assertNotNull(Object fieldName, String msg) {
        if (null == fieldName) {
            throw new ParameterException(msg);
        }
    }

    /**
     * 判断字符串不为空
     * @param fieldName
     * @param msg
     */
    public static void assertNotBlank(String fieldName, String msg) {
        if (StringUtil.isBlank(fieldName)) {
            throw new ParameterException(msg);
        }
    }
}