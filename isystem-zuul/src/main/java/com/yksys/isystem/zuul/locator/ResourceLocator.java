package com.yksys.isystem.zuul.locator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.core.event.RemoteRefreshRouteEvent;
import com.yksys.isystem.common.core.utils.StringUtil;
import com.yksys.isystem.common.model.AuthorityResource;
import com.yksys.isystem.zuul.service.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @program: yk-isystem
 * @description: 资源加载器
 * @author: YuKai Fan
 * @create: 2019-12-26 22:16
 **/
@Slf4j
public class ResourceLocator implements ApplicationListener<RemoteRefreshRouteEvent> {
    @Override
    public void onApplicationEvent(RemoteRefreshRouteEvent remoteRefreshRouteEvent) {
        refresh();
    }

    private AuthorityService authorityService;
    private JdbcRouteLocator jdbcRouteLocator;

    private Map<String, List<ConfigAttribute>> configAttributes = Maps.newConcurrentMap();

    /**
     * 权限列表
     */
    private List<AuthorityResource> authorityResources;

    public ResourceLocator(AuthorityService authorityService, JdbcRouteLocator jdbcRouteLocator) {
        this.authorityService = authorityService;
        this.jdbcRouteLocator = jdbcRouteLocator;
        this.authorityResources = Lists.newCopyOnWriteArrayList();
    }

    /**
     * 刷新配置
     */
    public void refresh() {
        loadAuthority();
    }

    public void loadAuthority() {
        List<ConfigAttribute> list;
        ConfigAttribute cfg;
        Map<String, List<ConfigAttribute>> configAttributes = Maps.newHashMap();
        try {
            Result<List<AuthorityResource>> result = authorityService.getAuthorityResources();
            List<AuthorityResource> authorityResources = result.getData();
            if (!CollectionUtils.isEmpty(authorityResources)) {
                for (AuthorityResource authorityResource : authorityResources) {
                    String path = authorityResource.getPath();
                    if (StringUtil.isBlank(path)) {
                        continue;
                    }
                    String fullPath = getFullPath(authorityResource.getServiceId(), path);
                    authorityResource.setPath(fullPath);
                    list = configAttributes.get(fullPath);
                    if (CollectionUtils.isEmpty(list)) {
                        list = Lists.newArrayList();
                    }

                    if (!list.contains(authorityResource.getAuthority())) {
                        cfg = new SecurityConfig(authorityResource.getAuthority());
                        list.add(cfg);
                    }

                    configAttributes.put(fullPath, list);
                }
                this.configAttributes.clear();
                this.authorityResources.clear();
                this.configAttributes = configAttributes;
                this.authorityResources = authorityResources;
            }
            log.info("加载动态权限:{}", this.authorityResources.size());
        } catch (Exception e) {
            log.error("加载动态权限错误", e);
        }
    }

    private String getFullPath(String serviceId, String path) {
        List<Route> routes = jdbcRouteLocator.getRoutes();
        if (!CollectionUtils.isEmpty(routes)) {
             return routes.stream()
                     .filter(route -> route.getId().equals(serviceId))
                     .findAny()
                     .get().getPrefix().concat(path.startsWith("/") ? path : "/" + path);
        }
        return path;
    }

    public List<AuthorityResource> getAuthorityResources() {
        return authorityResources;
    }

    public void setAuthorityResources(List<AuthorityResource> authorityResources) {
        this.authorityResources = authorityResources;
    }

    public Map<String, List<ConfigAttribute>> getConfigAttributes() {
        return configAttributes;
    }

    public void setConfigAttributes(Map<String, List<ConfigAttribute>> configAttributes) {
        this.configAttributes = configAttributes;
    }
}