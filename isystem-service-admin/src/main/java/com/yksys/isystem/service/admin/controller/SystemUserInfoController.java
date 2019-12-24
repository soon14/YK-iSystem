package com.yksys.isystem.service.admin.controller;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.model.SystemUserInfo;
import com.yksys.isystem.service.admin.service.SystemUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取登录人用户信息(角色集合, 权限集合)
     * @param map
     * @return
     */
    @GetMapping("/getLoginUserInfo")
    public Result getLoginUserInfo(@RequestParam Map<String, Object> map) {
        SystemUserInfo loginUserInfo = systemUserInfoService.getLoginUserInfo(map);
        return new Result(HttpStatus.OK.value(), "获取成功", loginUserInfo);
    }
}