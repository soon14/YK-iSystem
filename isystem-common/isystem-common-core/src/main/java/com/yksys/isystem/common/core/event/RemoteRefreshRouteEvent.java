package com.yksys.isystem.common.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @program: yk-isystem
 * @description: 自定义网关刷新远程事件
 * @author: YuKai Fan
 * @create: 2019-12-26 22:19
 **/
public class RemoteRefreshRouteEvent extends ApplicationEvent {
    public RemoteRefreshRouteEvent(Object source) {
        super(source);
    }
}