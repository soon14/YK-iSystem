package com.yksys.isystem.service.auth.service.feign;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.model.AuthorityMenu;
import com.yksys.isystem.common.model.SystemUserInfo;
import com.yksys.isystem.service.auth.service.fallback.SystemUserInfoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 16:44
 **/
@FeignClient(value = "isystem-service-admin", fallbackFactory = SystemUserInfoServiceFallback.class)
public interface SystemUserInfoService {

    /**
     * 获取登录人用户信息(角色集合, 权限集合)
     * @param map
     * @return
     */
    @GetMapping("/api/systemUserInfo/getLoginUserInfo")
    Result<SystemUserInfo> getLoginUserInfo(@RequestParam Map<String, Object> map);

    /**
     * 获取当前登录用户的操作菜单列表
     * @return
     */
    @GetMapping("/api/systemUserInfo/getCurrentUserMenus")
    Result<AuthorityMenu> getCurrentUserMenus();
}