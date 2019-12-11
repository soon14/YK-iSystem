package com.yksys.isystem.common.core.utils;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-04 09:04
 **/
public class AppUtil {

    /**
     * 获取随机全局唯一id
     * @return
     */
    public static String randomId() {
        IdWorker idWorker = new IdWorker(1, 1);
        return String.valueOf(idWorker.nextId());
    }
}