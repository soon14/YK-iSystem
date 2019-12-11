package com.yksys.isystem.service.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: YK-iSystem
 * @description: 认证配置类
 * @author: YuKai Fan
 * @create: 2019-12-10 16:52
 **/
@Data
@Component
@ConfigurationProperties(prefix = "authorization-param")
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