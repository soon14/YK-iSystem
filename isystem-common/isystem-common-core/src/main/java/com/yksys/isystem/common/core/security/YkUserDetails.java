package com.yksys.isystem.common.core.security;

import com.google.common.base.Converter;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.Collection;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-16 09:22
 **/
@Data
public class YkUserDetails implements UserDetails {
    private static final long serialVersionUID = 8420766434004461217L;

    //用户id
    private String userId;
    //用户名
    private String username;
    //账号
    private String account;
    //密码
    private String password;
    //用户权限
    private Collection<? extends GrantedAuthority> authorities;
    //是否已锁定
    private boolean accountNonLocked;
    //是否已过期
    private boolean accountNonExpired;
    //是否启用
    private boolean enabled;
    //密码是否已过期
    private boolean credentialsNonExpired;
    //认证客户端id
    private String clientId;
    //用户昵称
    private String nickName;
    //头像
    private String userIcon;
}