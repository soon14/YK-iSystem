package com.yksys.isystem.zuul.service;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.zuul.service.fallback.AuthorityServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-27 10:08
 **/
@FeignClient(value = "isystem-service-admin", fallbackFactory = AuthorityServiceFallback.class)
public interface AuthorityService {

    /**
     * 获取所有访问权限列表
     * @return
     */
    @GetMapping("/api/systemUserInfo/getAuthorityResources")
    Result getAuthorityResources();
}