package com.yksys.isystem.service.auth.controller;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.core.security.oauth2.client.AuthorizationParam;
import com.yksys.isystem.common.core.security.oauth2.client.Oauth2ClientProperties;
import com.yksys.isystem.common.core.utils.WebUtil;
import com.yksys.isystem.common.vo.SystemUserVo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-10 18:26
 **/
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Oauth2ClientProperties oauth2ClientProperties;

    @PostMapping("/login/token")
    public Result getLoginToken(@RequestBody SystemUserVo systemUserVo, @RequestHeader HttpHeaders headers) {
        Map result = getToken(systemUserVo.getAccount(), systemUserVo.getPassword(), null, headers);
        if (!result.containsKey("access_token")) {
            return new Result(HttpStatus.UNAUTHORIZED.value(), "登陆失败", result);
        }
        return new Result(HttpStatus.OK.value(), "获取成功", result);
    }

    private JSONObject getToken(String userName, String password, String type, HttpHeaders headers) {
        AuthorizationParam auth = oauth2ClientProperties.getOauth2().get("auth");
        String url = WebUtil.getServerUrl(WebUtil.getHttpServletRequest()) + "/oauth/token";
        // 使用oauth2密码模式登录
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", userName);
        postParameters.add("password", password);
        postParameters.add("client_id", auth.getClientId());
        postParameters.add("client_secret", auth.getSecret());
        postParameters.add("grant_type", auth.getAuthorizedGrantTypes());
        //添加参数区分第三方登录
        postParameters.add("login_type", type);
        //使用客户端的请求头, 发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头, 防止token失效
//        headers.remove(HttpHeaders.AUTHORIZATION);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(postParameters, headers);
        JSONObject result = restTemplate.postForObject(url, request, JSONObject.class);
        return result;
    }
}