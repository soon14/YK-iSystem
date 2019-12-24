package com.yksys.isystem.common.pojo;

import lombok.Data;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 11:27
 **/
@Data
public class SystemRole {
    //角色标识
    private String id;
    //角色名称
    private String roleName;
    //备注
    private String remark;
    //状态
    private int status;
}