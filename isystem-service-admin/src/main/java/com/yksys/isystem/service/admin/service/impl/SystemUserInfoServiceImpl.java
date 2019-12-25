package com.yksys.isystem.service.admin.service.impl;

import com.google.common.collect.Lists;
import com.yksys.isystem.common.core.constants.ComConstants;
import com.yksys.isystem.common.core.exception.ParameterException;
import com.yksys.isystem.common.core.security.UserAuthority;
import com.yksys.isystem.common.core.utils.MapUtil;
import com.yksys.isystem.common.model.AuthorityMenu;
import com.yksys.isystem.common.model.SystemUserInfo;
import com.yksys.isystem.common.pojo.SystemRole;
import com.yksys.isystem.common.pojo.UserRole;
import com.yksys.isystem.service.admin.mapper.SystemUserInfoMapper;
import com.yksys.isystem.service.admin.service.SystemUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 14:56
 **/
@Service
public class SystemUserInfoServiceImpl implements SystemUserInfoService {
    @Autowired
    private SystemUserInfoMapper systemUserInfoMapper;

    @Override
    public SystemUserInfo getLoginUserInfo(Map<String, Object> map) {
        Map<String, Object> systemUserInfoMap = systemUserInfoMapper.getSystemUserInfos(map);
        if (CollectionUtils.isEmpty(systemUserInfoMap)) {
            throw new ParameterException("该用户信息错误, 请联系管理员");
        }
        SystemUserInfo systemUserInfo = MapUtil.mapToObject(SystemUserInfo.class, systemUserInfoMap, false);
        //获取用户角色集合
        List<SystemRole> userRoles = getUserRoles(systemUserInfo.getId());
        //用户权限列表
        List<UserAuthority> userAuthorities = getUserAuthorities(systemUserInfo.getId(), systemUserInfo.getUserName());
        systemUserInfo.setRoles(userRoles);
        systemUserInfo.setAuthorities(userAuthorities);
        return systemUserInfo;
    }

    @Override
    public List<SystemRole> getUserRoles(String userId) {
        return systemUserInfoMapper.getUserRoles(userId);
    }

    @Override
    public List<UserAuthority> getUserAuthorities(String userId, String roleCode) {
        if (ComConstants.ROOT_ADMIN.equals(roleCode)) {
            //返回所有权限
            return systemUserInfoMapper.getUserAuthorities();
        }
        List<UserAuthority> list = Lists.newArrayList();
        List<SystemRole> userRoles = getUserRoles(userId);
        if (!CollectionUtils.isEmpty(userRoles)) {
            userRoles.forEach(userRole -> {
                List<UserAuthority> authoritiesByRoleId = systemUserInfoMapper.getUserAuthoritiesByRoleId(userRole.getId());
                if (!CollectionUtils.isEmpty(authoritiesByRoleId)) {
                    list.addAll(authoritiesByRoleId);
                }
            });
        }

        if (!CollectionUtils.isEmpty(list)) {
            //集合去重
            list.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(
                            () -> new TreeSet<>(Comparator.comparing(UserAuthority::getAuthorityId))),
                    ArrayList::new));
        }
        return list;
    }

    @Override
    public List<AuthorityMenu> getAuthorityMenuByUserId(String userId, String roleCode) {
        if (ComConstants.ROOT_ADMIN.equals(roleCode)) {
            //返回所有权限
            return systemUserInfoMapper.getAuthorityMenus();
        }
        List<AuthorityMenu> list = Lists.newArrayList();
        List<SystemRole> userRoles = getUserRoles(userId);
        if (!CollectionUtils.isEmpty(userRoles)) {
            userRoles.forEach(userRole -> {
                List<AuthorityMenu> authorityMenuList = systemUserInfoMapper.getUserAuthorityMenusByRoleId(userRole.getId());
                if (!CollectionUtils.isEmpty(authorityMenuList)) {
                    list.addAll(authorityMenuList);
                }
            });
        }
        if (!CollectionUtils.isEmpty(list)) {
            //集合去重
            list.stream().collect(Collectors.collectingAndThen(
                    Collectors.toCollection(
                            () -> new TreeSet<>(Comparator.comparing(AuthorityMenu::getAuthorityId))),
                    ArrayList::new));
        }
        return list;
    }


}