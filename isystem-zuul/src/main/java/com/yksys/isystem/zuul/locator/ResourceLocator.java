package com.yksys.isystem.zuul.locator;

import com.google.common.collect.Maps;
import com.yksys.isystem.common.core.event.RemoteRefreshRouteEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.ConfigAttribute;

import java.util.List;
import java.util.Map;

/**
 * @program: yk-isystem
 * @description: 资源加载器
 * @author: YuKai Fan
 * @create: 2019-12-26 22:16
 **/
public class ResourceLocator implements ApplicationListener<RemoteRefreshRouteEvent> {
    @Override
    public void onApplicationEvent(RemoteRefreshRouteEvent remoteRefreshRouteEvent) {
        refresh();
    }

    /**
     * 刷新配置
     */
    public void refresh() {

    }

    public void loadAuthority() {
        List<ConfigAttribute> list;
        ConfigAttribute cfg;
        Map<String, List<ConfigAttribute>> configAttributes = Maps.newHashMap();

    }
}