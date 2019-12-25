package com.yksys.isystem.service.auth.service.fallback;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.core.hystrix.Fallback;
import com.yksys.isystem.service.auth.service.feign.SystemUserInfoService;
import feign.hystrix.FallbackFactory;

import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 16:46
 **/
public class SystemUserInfoServiceFallback implements FallbackFactory<SystemUserInfoService> {
    @Override
    public SystemUserInfoService create(Throwable throwable) {
        return new SystemUserInfoService() {
            @Override
            public Result getLoginUserInfo(Map<String, Object> map) {
                return Fallback.badGateway();
            }

            @Override
            public Result getCurrentUserMenus() {
                return Fallback.badGateway();
            }
        };
    }
}