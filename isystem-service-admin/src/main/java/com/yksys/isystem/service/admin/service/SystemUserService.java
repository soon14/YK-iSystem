package com.yksys.isystem.service.admin.service;

import com.yksys.isystem.common.pojo.SystemUser;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description: 系统用户service
 * @author: YuKai Fan
 * @create: 2019-12-03 20:04
 **/
public interface SystemUserService {
    /**
     * 新增用户
     * @param systemUser
     * @return
     */
    SystemUser addSystemUser(SystemUser systemUser);

    /**
     * 获取所有用户(分页)
     * @param start 开始记录
     * @param pageSize 分页大小
     * @param map 参数
     * @return
     */
    List<Map<String, Object>> getSystemUsers(int start, int pageSize, Map<String, Object> map);

    /**
     * 获取所有用户
     * @param map 参数
     * @return
     */
    List<Map<String, Object>> getSystemUsers(Map<String, Object> map);
}