package com.yksys.isystem.service.admin.mapper;

import com.yksys.isystem.common.core.security.UserAuthority;
import com.yksys.isystem.common.model.AuthorityMenu;
import com.yksys.isystem.common.model.AuthorityResource;
import com.yksys.isystem.common.pojo.SystemRole;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 14:52
 **/
public interface SystemUserInfoMapper {

    /**
     * 获取用户信息
     * @param map (phone:手机号, email: 邮箱, account: 账号)
     * @return
     */
    Map<String, Object> getSystemUserInfos(Map<String, Object> map);

    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    List<SystemRole> getUserRoles(String userId);

    /**
     * 获取用户权限集合
     * @return
     */
    List<UserAuthority> getUserAuthorities();

    /**
     * 根据roleId获取权限列表
     * @param roleId
     * @return
     */
    List<UserAuthority> getUserAuthoritiesByRoleId(String roleId);

    /**
     * 获取所有全列菜单列表
     * @return
     */
    List<AuthorityMenu> getAuthorityMenus();

    /**
     * 根据角色id获取菜单列表
     * @param id
     * @return
     */
    List<AuthorityMenu> getUserAuthorityMenusByRoleId(String roleId);

    /**
     * 获取所有访问权限列表
     * @return
     */
    List<AuthorityResource> getAuthorityResources();
}