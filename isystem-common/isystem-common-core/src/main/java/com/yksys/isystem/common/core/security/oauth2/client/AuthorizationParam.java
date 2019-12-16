package com.yksys.isystem.common.core.security.oauth2.client;

import lombok.Data;

/**
 * @program: YK-iSystem
 * @description: 认证配置类
 * @author: YuKai Fan
 * @create: 2019-12-10 16:52
 **/
@Data
public class AuthorizationParam {
    //客户端id
    private String clientId;
    //客户端秘钥
    private String secret;
    //授权类型
    private String authorizedGrantTypes;
    //token过期时间
    private int tokenExpire;
    //token刷新时间
    private int tokenRefresh;
}