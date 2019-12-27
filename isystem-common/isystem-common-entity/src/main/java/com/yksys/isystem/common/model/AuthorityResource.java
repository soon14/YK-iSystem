package com.yksys.isystem.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YK-iSystem
 * @description: 权限资源
 * @author: YuKai Fan
 * @create: 2019-12-27 09:23
 **/
@Data
public class AuthorityResource implements Serializable {
    private static final long serialVersionUID = -8727758006766239574L;
    //访问路径
    private String path;
    //权限标识
    private String authority;
    //权限id
    private String authorityId;
    //是否身份认证
    private int auth = 1;
    //是否公开访问
    private int open = 1;
    //服务名称
    private String serviceId;
    //前缀
    private String prefix;
    //状态
    private int status;
}