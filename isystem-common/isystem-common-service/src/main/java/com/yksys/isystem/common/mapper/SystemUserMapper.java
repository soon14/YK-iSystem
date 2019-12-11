package com.yksys.isystem.common.mapper;

import com.yksys.isystem.common.pojo.SystemUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description: 系统用户mapper
 * @author: YuKai Fan
 * @create: 2019-12-03 14:56
 **/
public interface SystemUserMapper {
    /**
     * 新增用户
     * @param systemUser 用户实体
     * @return
     */
    int addSystemUser(SystemUser systemUser);

    /**
     * 批量新增用户
     * @param list 用户集合
     */
    void addSystemUsers(@Param(value = "list") List<SystemUser> list);

    /**
     * 根据id查询指定用户
     * @param id  主键
     * @return
     */
    Map<String, Object> getSystemUserById(String id);

    /**
     * 根据id修改用户
     * @param user 用户实体
     * @return
     */
    int editSystemUserById(SystemUser user);

    /**
     * 批量修改用户
     *
     * @param user 用户实体
     * @param ids 主键集合
     */
    void editSystemUserByIds(@Param("map") SystemUser user, @Param("list") List<String> ids);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int delSystemUserById(String id);

    /**
     * 批量删除用户
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delSystemUserByIds(List<String> ids);

    /**
     * 真删除用户
     *
     * @param id 主键
     * @return dao成功失败标志
     */
    int delSystemUserRealById(String id);

    /**
     * 真批量删除用户
     *
     * @param ids 主键集合
     * @return dao成功失败标志
     */
    int delSystemUserRealByIds(List<String> ids);

    /**
     * 全部真删除
     * @return
     */
    int delAllSystemUserReal();

    /**
     * 获取所有用户.
     * @param map 页面表单
     * @return 结果集合
     */
    List<Map<String, Object>> getSystemUsers(Map<String, Object> map);
}