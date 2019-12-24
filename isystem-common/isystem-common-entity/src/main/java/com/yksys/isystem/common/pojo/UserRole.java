package com.yksys.isystem.common.pojo;

import lombok.Data;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 15:12
 **/
@Data
public class UserRole {
    //角色id
    private String roleId;
    //用户id
    private String userId;
    //角色编码
    private String roleCode;
    //角色名称
    private String roleName;
}