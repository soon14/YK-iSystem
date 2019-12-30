package com.yksys.isystem.zuul.filter;

import com.google.common.collect.Sets;
import com.yksys.isystem.common.core.constants.ComConstants;
import com.yksys.isystem.common.core.utils.StringUtil;
import com.yksys.isystem.zuul.configuration.ApiGatewayProperties;
import com.yksys.isystem.zuul.locator.ResourceLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-27 14:58
 **/
@Slf4j
@Component
public class AccessManager {
    private ResourceLocator resourceLocator;
    private ApiGatewayProperties apiGatewayProperties;
    private Set<String> permitAll = Sets.newHashSet();
    private Set<String> authorityIgnores = Sets.newHashSet();
    private static final AntPathMatcher pathMatch = new AntPathMatcher();

    public boolean check(HttpServletRequest request, Authentication authentication) {
        if (!apiGatewayProperties.getAccessControl()) {
            return true;
        }
        String requestPath = getRequestPath(request);
        if (permitAll(requestPath)) {
            return true;
        }
        return checkAuthorities(request, authentication, requestPath);
    }

    private boolean checkAuthorities(HttpServletRequest request, Authentication authentication, String requestPath) {
        Object principal = authentication.getPrincipal();
        //已认证的身份
        if (principal != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                log.info("认证错误, {}", authentication);
            }

            if (authorityIgnores(requestPath)) {
                //忽略需要认证的权限
                return true;
            }

            return mathAuthorities(request, authentication, requestPath);
        }

        return false;
    }

    private boolean mathAuthorities(HttpServletRequest request, Authentication authentication, String requestPath) {
        List<ConfigAttribute> attributes = getAttributes(requestPath);
        int result = 0;
        if (authentication == null) {
            return false;
        }
        if (ComConstants.ROOT_ADMIN.equals(authentication.getName())) {
            //超级管理员账号, 直接放行
            return true;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<ConfigAttribute> attributeIter = attributes.iterator();
        while (attributeIter.hasNext()) {
            ConfigAttribute attribute = attributeIter.next();
            Iterator<? extends GrantedAuthority> authorityIter = authorities.iterator();
            while (authorityIter.hasNext()) {
                GrantedAuthority authority = authorityIter.next();
                if (attribute.getAttribute().equals(authority.getAuthority())) {
                    result++;
                }
            }
        }
        log.debug("mathAuthorities result[{}]", result);
        return result > 0;
    }

    private List<ConfigAttribute> getAttributes(String requestPath) {
        //匹配动态权限
        AtomicReference<List<ConfigAttribute>> attributes = new AtomicReference<>();
        resourceLocator.getConfigAttributes().keySet().stream()
                .filter(item -> !"/**".equals(item))
                .filter(item -> pathMatch.match(item, requestPath))
                .findFirst().ifPresent(item -> attributes.set(resourceLocator.getConfigAttributes().get(item)));

        if (!CollectionUtils.isEmpty(attributes.get())) {
            return attributes.get();
        }
        return SecurityConfig.createList("AUTHORITIES_REQUIRED");
    }

    private boolean authorityIgnores(String requestPath) {
        return authorityIgnores.stream()
                .filter(item -> pathMatch.match(item, requestPath))
                .findFirst().isPresent();
    }

    private boolean permitAll(String requestPath) {
        boolean permit = permitAll.stream()
                .filter(item -> pathMatch.match(item, requestPath)).findFirst().isPresent();
        if (permit) {
            return true;
        }

        return resourceLocator.getAuthorityResources().stream()
                .filter(item -> StringUtil.isNotBlank(item.getPath()))
                .filter(item -> {
                    boolean isAuth = item.getAuth() == 1 ? true : false;
                    //无需认证, 返回true
                    return pathMatch.match(item.getPath(), requestPath) && !isAuth;
                }).findFirst().isPresent();
    }

    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (StringUtil.isNotEmpty(pathInfo)) {
            url = StringUtil.isNotBlank(url) ? url + pathInfo : pathInfo;
        }
        return url;
    }
}