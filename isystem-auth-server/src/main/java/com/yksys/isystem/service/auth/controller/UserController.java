package com.yksys.isystem.service.auth.controller;

import com.yksys.isystem.common.core.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-10 18:26
 **/
@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private TokenStore tokenStore;

    @GetMapping("/user")
    public Result auth(@RequestHeader("Authorization") String auth) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(auth);
        return new Result(HttpStatus.OK.value(), "获取成功", oAuth2AccessToken);
    }
}