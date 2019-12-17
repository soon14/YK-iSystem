package com.yksys.isystem.service.auth.service;

import com.yksys.isystem.common.core.security.YkClientDetails;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-17 11:27
 **/
public interface BaseClientService {
    /**
     * 获取客户端信息
     * @param clientId
     * @return
     */
    YkClientDetails getOauth2ClientInfo(String clientId);
}