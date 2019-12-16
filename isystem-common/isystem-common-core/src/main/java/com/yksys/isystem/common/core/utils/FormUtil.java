package com.yksys.isystem.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormUtil {
    public static final char UNDERLINE = '_';

    private static final Logger logger = LoggerFactory.getLogger(FormUtil.class);

    /**
     * 数组去重复
     *
     * @param
     * @return
     * @description
     * @date 2018年3月21日
     */
    public static String[] arrayUnique(String[] a) {
        // array_unique
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < a.length; i++) {
            if (!list.contains(a[i])) {
                list.add(a[i]);
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * 驼峰转下划线
     *
     * @param
     * @return
     * @description
     * @date 2018年3月21日
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param
     * @return
     * @description
     * @date 2018年3月21日
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param
     * @return
     * @description
     * @date 2018年3月21日
     */
    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param);
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 根据key获取map中对应的值
     *
     * @param
     * @return
     * @description
     * @date 2018年1月31日
     */
    public static String getMapValue(Map<String, Object> map, String key) {
        if (CollectionUtils.isEmpty(map)) {
            return null;
        }
        if (null == map.get(key) || "".equals(map.get(key).toString().trim())) {
            return null;
        }
        return map.get(key).toString();
    }

    /**
     * 求和：list中map的key对应的value数字之和
     *
     * @param
     * @return
     * @description
     * @date 2018年1月31日
     */
    public static BigDecimal sumListMapValue(List<Map<String, Object>> list, String key) {
        BigDecimal sum = new BigDecimal(0);
        if (CollectionUtils.isEmpty(list)) {
            return sum;
        }
        for (Map<String, Object> map : list) {
            if (null == map.get(key) || "".equals(map.get(key).toString().trim())) {
                continue;
            }
            String value = map.get(key).toString();
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    /**
     * 根据key获取list的map中对应的值
     *
     * @param
     * @return
     * @description 唯一性判断用，只有一条记录
     * @date 2018年1月31日
     */
    public static Object getListMapUniqueValue(List<Map<String, Object>> list, String key) {
        Object o = null;
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (Map<String, Object> map : list) {
            if (!CollectionUtils.isEmpty(map) && null != map.get(key)) {
                o = map.get(key);
                break;
            }
        }
        return o;
    }

    /**
     * 验证key是否在List的map中存在
     *
     * @param
     * @return
     * @description 唯一性判断用，只有一条记录
     * @date 2018年1月31日
     */
    public static void uniqueValue(List<Map<String, Object>> list, String key, String tipName, String tipMsg) {
        if (null != getListMapUniqueValue(list, key)) {
            //throw new UniqueException(String.format("%s%s", tipName,tipMsg));
        }
    }
}
