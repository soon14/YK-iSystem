package com.yksys.isystem.zuul.configuration;

import com.google.common.collect.Lists;
import com.netflix.zuul.ZuulFilter;
import com.yksys.isystem.zuul.filter.modifyHeaderFilter;
import com.yksys.isystem.zuul.locator.JdbcRouteLocator;
import com.yksys.isystem.zuul.locator.ResourceLocator;
import com.yksys.isystem.zuul.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @program: yk-isystem
 * @description: 网关配置
 * @author: YuKai Fan
 * @create: 2019-12-30 20:55
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({ApiGatewayProperties.class})
public class GatewayConfiguration {
    private static final String ALLOW_HEADERS = "*";
    private static final String ALLOW_METHODS = "*";
    private static final String ALLOW_ORIGIN = "*";
    private static final String ALLOW_EXPOSE = "Authorization";
    private static final Long MAX_AGE = 18000L;

    @Autowired
    private JdbcRouteLocator jdbcRouteLocator;

    /**
     * 修改请求头
     * @return
     */
    @Bean
    public ZuulFilter modifyHeaderFilter() {
        ZuulFilter zuulFilter = new modifyHeaderFilter();
        log.info("ModifyHeaderFilter [{}]", zuulFilter);
        return zuulFilter;
    }

    /**
     * 资源加载器
     * @param authorityService
     * @return
     */
    @Bean
    public ResourceLocator resourceLocator(AuthorityService authorityService) {
        ResourceLocator resourceLocator = new ResourceLocator(authorityService, jdbcRouteLocator);
        log.info("CorsFilter [{}]", resourceLocator);
        return resourceLocator;
    }

    /**
     * 路由加载器
     * @param zuulProperties
     * @param serverProperties
     * @param jdbcTemplate
     * @param publisher
     * @return
     */
    @Bean
    public JdbcRouteLocator jdbcRouteLocator(ZuulProperties zuulProperties, ServerProperties serverProperties, JdbcTemplate jdbcTemplate, ApplicationEventPublisher publisher) {
        JdbcRouteLocator jdbcRouteLocator = new JdbcRouteLocator(serverProperties.getServlet().getContextPath(), zuulProperties, jdbcTemplate, publisher);
        log.info("JdbcRouteLocator: {}", jdbcRouteLocator);
        return jdbcRouteLocator;
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Lists.newArrayList(ALLOW_HEADERS.split(",")));
        config.setAllowedMethods(Lists.newArrayList(ALLOW_METHODS.split(",")));
        config.setAllowedOrigins(Lists.newArrayList(ALLOW_ORIGIN.split(",")));
        config.setMaxAge(MAX_AGE);
        config.addExposedHeader(ALLOW_EXPOSE);

        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        log.info("CorsFilter [{}]", bean);
        return bean;
    }


}