package com.yksys.isystem.service.auth.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yksys.isystem.common.core.utils.MapUtil;
import com.yksys.isystem.common.mapper.SystemUserMapper;
import com.yksys.isystem.common.pojo.SystemUser;
import com.yksys.isystem.service.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-10 17:26
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("account", account);
        List<Map<String, Object>> systemUsers = systemUserMapper.getSystemUsers(map);
        if (CollectionUtils.isEmpty(systemUsers) || systemUsers.size() != 1) {
            throw new UsernameNotFoundException("用户不存在");
        }
        SystemUser systemUser = MapUtil.mapToObject(SystemUser.class, systemUsers.get(0), false);

        List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
        List<String> roles = systemUser.getRoles();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return new User(systemUser.getAccount(), systemUser.getPassword(), authorities);
    }

//    private static class UserDetailConverter {
//        static AuthUserDetail con
//    }
}