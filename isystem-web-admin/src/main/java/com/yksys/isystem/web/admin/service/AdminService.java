package com.yksys.isystem.web.admin.service;

import com.yksys.isystem.common.core.dto.Result;
import com.yksys.isystem.common.vo.SystemUserVo;
import com.yksys.isystem.web.admin.service.fallback.AdminServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-05 09:37
 **/
@FeignClient(value = "isystem-service-admin", fallbackFactory = AdminServiceFallback.class)
public interface AdminService {

    @PostMapping("/api/systemUser/addSystemUser")
    Result addSystemUser(@RequestBody SystemUserVo systemUserVo);

    @GetMapping("/api/systemUser/getSystemUsers/noPage")
    Result getSystemUser(@RequestParam Map<String, Object> map);

}