package com.yksys.isystem.common.model;

import com.yksys.isystem.common.core.security.UserAuthority;
import com.yksys.isystem.common.pojo.SystemRole;
import com.yksys.isystem.common.pojo.SystemUser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 11:21
 **/
@Data
public class SystemUserInfo extends SystemUser implements Serializable {
    private static final long serialVersionUID = -1666628898966720470L;

    //角色集合
    private List<SystemRole> roles;

    //用户权限
    private List<UserAuthority> authorities;
}