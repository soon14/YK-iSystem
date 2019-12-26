package com.yksys.isystem.zuul.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-26 16:51
 **/
@Data
@ConfigurationProperties(prefix = "api-gateway")
public class ApiGatewayProperties {
    /**
     * 是否开启签名验证
     */
    private Boolean checkSign = true;
    /**
     * 是否开启动态访问控制
     */
    private Boolean accessControl = true;
    /**
     * 是否开启接口调试
     */
    private Boolean apiDebug = false;
    /**
     * 始终放行
     */
    private Set<String> permitAll;
    /**
     * 无需鉴权的请求
     */
    private Set<String> authorityIgnores;
    /**
     * 忽略签名
     */
    private Set<String> signIgnores;
}