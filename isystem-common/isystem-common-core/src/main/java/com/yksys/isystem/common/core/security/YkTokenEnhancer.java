package com.yksys.isystem.common.core.security;

import com.google.common.collect.Maps;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-16 09:14
 **/
public class YkTokenEnhancer extends TokenEnhancerChain {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        final Map<String, Object> additionalInfo = Maps.newHashMap();
        if (!authentication.isClientOnly()) {
            if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof YkTokenEnhancer) {
                //设置用户额外信息
                YkUserDetails baseUser = ((YkUserDetails) authentication.getPrincipal());
                additionalInfo.put(YkSecurityConstants.OPEN_ID, baseUser.getUserId());
            }
        }

        oAuth2AccessToken.setAdditionalInformation(additionalInfo);
        return super.enhance(oAuth2AccessToken, authentication);
    }
}