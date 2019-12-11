package com.yksys.isystem.service.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-10 17:20
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //对任何请求做拦截, 如果是完整认证的话, 就允许访问
        http.authorizeRequests().anyRequest().fullyAuthenticated();
        //配置登录连接, 允许访问 --认证接口调用/oauth/token
        http.formLogin().loginPage("/login").failureUrl("/login?code=").permitAll();
        //配置登出连接, 允许访问
        http.logout().logoutUrl("/logout").permitAll();
        http.authorizeRequests().antMatchers("/oauth/authorize").permitAll();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}