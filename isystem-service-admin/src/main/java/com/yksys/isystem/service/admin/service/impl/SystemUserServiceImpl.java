package com.yksys.isystem.service.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.yksys.isystem.common.core.utils.AppUtil;
import com.yksys.isystem.common.mapper.SystemUserMapper;
import com.yksys.isystem.common.pojo.SystemUser;
import com.yksys.isystem.service.admin.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-03 20:05
 **/
@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public SystemUser addSystemUser(SystemUser systemUser) {
        systemUser.setId(AppUtil.randomId());
        systemUser.setStatus(1);
        systemUserMapper.addSystemUser(systemUser);
        return systemUser;
    }

    @Override
    public List<Map<String, Object>> getSystemUsers(int start, int pageSize, Map<String, Object> map) {
        PageHelper.offsetPage(start, pageSize);
        return this.getSystemUsers(map);
    }

    @Override
    public List<Map<String, Object>> getSystemUsers(Map<String, Object> map) {
        return systemUserMapper.getSystemUsers(map);
    }
}