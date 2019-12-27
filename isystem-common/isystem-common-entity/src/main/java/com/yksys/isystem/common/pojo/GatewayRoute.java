package com.yksys.isystem.common.pojo;

import lombok.Data;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-27 10:27
 **/
@Data
public class GatewayRoute {
    //路由标识
    private String id;
    //路由名称
    private String routeName;
    //路径
    private String path;
    //服务id
    private String serviceId;
    //完整地址
    private String url;
    //是否忽略前缀 0:否, 1:是
    private int stripPrefix;
    //是否重试 0：不重试, 1: 重试
    private int retryable;
    //是否保留数据 0: 否, 1: 是
    private int persist;
    //状态
    private int status;
    //路由描述
    private String routeDesc;
}