package com.yksys.isystem.common.core.constants;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 15:25
 **/
public class ComConstants {
    // 默认超级管理员账号
    public final static String ROOT_ADMIN = "admin";

    /**
     * 账号状态
     * 0: 禁用, 1: 正常, 2: 锁定
     */
    public final static Integer ACCOUNT_STATUS_DISABLE = 0;
    public final static Integer ACCOUNT_STATUS_NORMAL = 1;
    public final static Integer ACCOUNT_STATUS_LOCKED = 2;

    /**
     * 微服务之间传递的唯一标识
     */
    public final static String REQUEST_HEADER_ID = "Request-Header-Id";
}