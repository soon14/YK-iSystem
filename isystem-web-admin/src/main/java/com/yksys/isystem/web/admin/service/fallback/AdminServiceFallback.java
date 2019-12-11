package com.yksys.isystem.web.admin.service.fallback;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.core.hystrix.Fallback;
import com.yksys.isystem.common.vo.SystemUserVo;
import com.yksys.isystem.web.admin.service.AdminService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-05 09:43
 **/
@Component
public class AdminServiceFallback implements FallbackFactory<AdminService> {
    @Override
    public AdminService create(Throwable throwable) {
        return new AdminService() {
            @Override
            public Result addSystemUser(SystemUserVo systemUserVo) {
                return Fallback.badGateway();
            }

            @Override
            public Result getSystemUser(Map<String, Object> map) {
                return Fallback.badGateway();
            }
        };
    }
}