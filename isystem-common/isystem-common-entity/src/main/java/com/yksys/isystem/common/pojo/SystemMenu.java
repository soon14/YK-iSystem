package com.yksys.isystem.common.pojo;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description: 用户权限
 * @author: YuKai Fan
 * @create: 2019-12-18 09:14
 **/
@Data
public class SystemMenu{
    //权限标识
    private String id;
    //权限名称
    private String menuName;
    //上级权限id
    private String pid;
    //排名
    private Integer sort;
    //等级
    private Integer level;
    //备注
    private String remark;
    //状态:0  已禁用 1 正在使用
    private Integer status;
    //链接
    private String url;
    //权限标识
    private String perm;
    //图标
    private String icon;
}