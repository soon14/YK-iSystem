package com.yksys.isystem.common.pojo;

import lombok.Data;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 11:29
 **/
@Data
public class SystemAuthority {
    //权限id
    private String id;
    //权限编码
    private String authority;
    //菜单id
    private String menuId;
    //备注
    private String remark;
    //状态
    private int status;
}