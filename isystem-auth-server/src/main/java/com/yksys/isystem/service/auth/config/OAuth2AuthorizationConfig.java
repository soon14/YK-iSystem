package com.yksys.isystem.service.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @program: YK-iSystem
 * @description: OAuth配置类
 * @author: YuKai Fan
 * @create: 2019-12-10 17:08
 **/
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    //spring security5 之后需要
    private static final String SECRET_PREFIX = "{noop}";

    @Autowired
    private AuthenticationManager authenticationManager;//认证管理
    @Autowired
    private UserDetailsService userDetailsService;// 用户信息服务
    @Autowired
    private TokenStore tokenStore;// 保存令牌数据栈
    @Autowired
    private AuthorizationParam authorizationParam;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(authorizationParam.getClientId())
                .authorizedGrantTypes(authorizationParam.getAuthorizedGrantTypes())
                .secret(passwordEncoder().encode(authorizationParam.getSecret()))
                .accessTokenValiditySeconds(authorizationParam.getTokenExpire())
                .refreshTokenValiditySeconds(authorizationParam.getTokenRefresh())
                .scopes("read", "write");

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public TokenStore tokenStore(RedisConnectionFactory redisConnectionFactory) {
        //redis存储令牌
        return new RedisTokenStore(redisConnectionFactory);
    }

}