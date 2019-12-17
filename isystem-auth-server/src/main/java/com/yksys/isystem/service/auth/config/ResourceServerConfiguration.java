package com.yksys.isystem.service.auth.config;

import com.yksys.isystem.common.core.security.YkAccessDeniedHandler;
import com.yksys.isystem.common.core.security.YkAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: YK-iSystem
 * @description: 资源拦截配置
 * @author: YuKai Fan
 * @create: 2019-12-10 17:18
 **/
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    private BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/api/login/**", "/oauth/**").permitAll()
                //监控端点内部放行
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                // /logout退出清楚cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .and()
                //认证鉴权错误处理, 为了统一异常处理。每个资源服务器都应该加上
                .exceptionHandling()
                .accessDeniedHandler(new YkAccessDeniedHandler())
                .authenticationEntryPoint(new YkAuthenticationEntryPoint())
                .and()
                .csrf().disable()
                //禁用httpBasic
                .httpBasic().disable();
    }

    public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
        public LogoutSuccessHandler() {
            //重定向到原地址
            this.setUseReferer(true);
        }

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            //解密请求头
            try {
                authentication = tokenExtractor.extract(request);
                if (authentication != null && authentication.getPrincipal() != null) {
                    String token = authentication.getPrincipal().toString();
                    log.debug("revokeToken tokenValue:{}", token);
                    //移除token
                    tokenStore.removeAccessToken(tokenStore.readAccessToken(token));
                }
            } catch (Exception e) {
                log.error("revokeToken error:{}", e);
            }

            super.onLogoutSuccess(request, response, authentication);
        }
    }
}