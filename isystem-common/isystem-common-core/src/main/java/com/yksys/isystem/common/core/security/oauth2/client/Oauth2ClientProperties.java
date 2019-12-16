package com.yksys.isystem.common.core.security.oauth2.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-13 14:17
 **/
@Data
@Component
@ConfigurationProperties(prefix = "isystem.client")
public class Oauth2ClientProperties {
    private Map<String, AuthorizationParam> oauth2;
}