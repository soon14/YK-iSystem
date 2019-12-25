package com.yksys.isystem.common.core.security;

import com.yksys.isystem.common.core.utils.AssertUtil;
import com.yksys.isystem.common.core.utils.FormUtil;
import com.yksys.isystem.common.core.utils.MapUtil;
import com.yksys.isystem.common.core.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-25 09:16
 **/
@Slf4j
public class AppSession {

    /**
     * 获取当前登录认证用户信息
     * @return
     */
    public static YkUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
            if (!oAuth2Authentication.isClientOnly()) {
                if (authentication.getPrincipal() instanceof YkUserDetails) {
                    return (YkUserDetails) authentication.getPrincipal();
                }

                if (authentication.getPrincipal() instanceof Map) {
                    return MapUtil.mapToObject(YkUserDetails.class, (Map)authentication.getPrincipal(), false);
                }
            } else {
                YkUserDetails userDetails = new YkUserDetails();
                userDetails.setClientId(oAuth2Request.getClientId());
                userDetails.setAuthorities(oAuth2Request.getAuthorities());
                return userDetails;
            }
        }
        return null;
    }

    /**
     * 获取当前登录认证用户id
     * @return
     */
    public static String getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    /**
     * 更新当前登录用户信息
     *
     * @param tokenStore
     * @param userDetails
     */
    public static void updateCurrentUser(TokenStore tokenStore, YkUserDetails userDetails) {
        if (userDetails == null) {
            return;
        }
        AssertUtil.assertNotBlank(userDetails.getClientId(), "客户端id不能为空");
        AssertUtil.assertNotBlank(userDetails.getUsername(), "用户名不能为空");
        //动态更新客户端生成的token
        Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientIdAndUserName(userDetails.getClientId(), userDetails.getUsername());
        if (!CollectionUtils.isEmpty(accessTokens)) {
            for (OAuth2AccessToken accessToken : accessTokens) {
                //没有set方法，利用反射机制强制赋值
                OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
                if (oAuth2Authentication != null) {
                    Authentication authentication = oAuth2Authentication.getUserAuthentication();
                    ReflectionUtil.setFieldValue(authentication, "principal", userDetails);
                    //重新保存
                    tokenStore.storeAccessToken(accessToken, oAuth2Authentication);
                }
            }
        }
    }

    /**
     * 构建资源服务器RedisToken服务
     * @param redisConnectionFactory
     * @return
     */
    public static ResourceServerTokenServices buildRedisTokenServices(RedisConnectionFactory redisConnectionFactory) {
        YkRedisTokenService tokenService = new YkRedisTokenService();
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenService.setTokenStore(redisTokenStore);
        log.info("buildRedisTokenServices[{}]", tokenService);
        return tokenService;
    }
}