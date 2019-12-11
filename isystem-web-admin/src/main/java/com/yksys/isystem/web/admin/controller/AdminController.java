package com.yksys.isystem.web.admin.controller;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.core.utils.MD5Util;
import com.yksys.isystem.common.vo.SystemUserVo;
import com.yksys.isystem.web.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-05 09:27
 **/
@RestController
@RequestMapping("/api/sys")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 用户注册
     * @param systemUserVo
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody SystemUserVo systemUserVo) {
        //判断手机号, 用户名是否已存在 todo

        //生成随机盐
        String salt = MD5Util.createSalt();
        String pwd = systemUserVo.getPassword();
        //如果账号为空,则设置默认账号为用户名拼音 todo
        //密码为空表示管理员创建用户,默认密码为111111 todo
        systemUserVo.setPassword(MD5Util.generate(pwd, systemUserVo.getAccount() + salt));
        systemUserVo.setSalt(salt);
        return adminService.addSystemUser(systemUserVo);
    }

    @PostMapping("/login")
    public Result login(@RequestBody SystemUserVo systemUserVo) {
        return null;
    }
}