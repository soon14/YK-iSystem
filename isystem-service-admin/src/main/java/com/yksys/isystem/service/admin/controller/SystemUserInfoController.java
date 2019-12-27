package com.yksys.isystem.service.admin.controller;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.core.security.AppSession;
import com.yksys.isystem.common.model.AuthorityMenu;
import com.yksys.isystem.common.model.AuthorityResource;
import com.yksys.isystem.common.model.SystemUserInfo;
import com.yksys.isystem.service.admin.service.SystemUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 16:40
 **/
@RestController
@RequestMapping("/api/systemUserInfo")
public class SystemUserInfoController {
    @Autowired
    private SystemUserInfoService systemUserInfoService;

    /**
     * 获取用户基础信息
     *
     * @return
     */
    @GetMapping("/getUserProfile")
    public Result getUserProfile() {
        return new Result(HttpStatus.OK.value(), "获取成功", AppSession.getCurrentUser());
    }

    /**
     * 获取登录人用户信息(角色集合, 权限集合)
     * @param map
     * @return
     */
    @GetMapping("/getLoginUserInfo")
    public Result getLoginUserInfo(@RequestParam Map<String, Object> map) {
        SystemUserInfo loginUserInfo = systemUserInfoService.getLoginUserInfo(map);
        return new Result(HttpStatus.OK.value(), "获取成功", loginUserInfo);
    }

    /**
     * 获取当前登录用户的操作菜单列表
     *
     * @return
     */
    @GetMapping("/getCurrentUserMenus")
    public Result getCurrentUserMenus(@RequestParam String userId, @RequestParam String userName) {
        List<AuthorityMenu> list = systemUserInfoService.getAuthorityMenuByUserId(userId, userName);
        return new Result(HttpStatus.OK.value(), "获取成功", list);
    }

    /**
     * 获取所有访问权限列表
     * @return
     */
    @GetMapping("/getAuthorityResources")
    public Result getAuthorityResources() {
        List<AuthorityResource> list = systemUserInfoService.getAuthorityResources();
        return new Result(HttpStatus.OK.value(), "获取成功", list);
    }
}