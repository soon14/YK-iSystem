package com.yksys.isystem.common.core.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-18 09:29
 **/
@Data
public class UserAuthority implements GrantedAuthority {
    private static final long serialVersionUID = -7632055205068274041L;

    /**
     * 权限id
     */
    private String authorityId;

    /**
     * 权限标识
     */
    private String authority;
}