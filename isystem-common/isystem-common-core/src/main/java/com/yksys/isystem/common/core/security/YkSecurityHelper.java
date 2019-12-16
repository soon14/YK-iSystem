package com.yksys.isystem.common.core.security;

import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

/**
 * @program: YK-iSystem
 * @description: 认证信息帮助类
 * @author: YuKai Fan
 * @create: 2019-12-16 14:15
 **/
public class YkSecurityHelper {

    /**
     * 构建token转换器
     * @return
     */
    public static DefaultAccessTokenConverter buildAccessTokenConverter() {
        YkUserConverter ykUserConverter = new YkUserConverter();
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        accessTokenConverter.setUserTokenConverter(ykUserConverter);
        return accessTokenConverter;
    }
}