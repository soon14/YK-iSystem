package com.yksys.isystem.service.admin.service;

import com.yksys.isystem.common.core.security.UserAuthority;
import com.yksys.isystem.common.model.AuthorityMenu;
import com.yksys.isystem.common.model.AuthorityResource;
import com.yksys.isystem.common.model.SystemUserInfo;
import com.yksys.isystem.common.pojo.SystemRole;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 14:55
 **/
public interface SystemUserInfoService {

    /**
     * 获取登录用户信息
     * @param map
     * @return
     */
    SystemUserInfo getLoginUserInfo(Map<String, Object> map);

    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    List<SystemRole> getUserRoles(String userId);

    /**
     * 获取用户权限集合
     * @param userId
     * @param roleCode
     * @return
     */
    List<UserAuthority> getUserAuthorities(String userId, String roleCode);

    /**
     * 获取用户已授权的权限详情
     * @param userId
     * @param roleCode
     * @return
     */
    List<AuthorityMenu> getAuthorityMenuByUserId(String userId, String roleCode);

    /**
     * 获取所有访问权限列表
     * @return
     */
    List<AuthorityResource> getAuthorityResources();

}