package com.yksys.isystem.common.core.hystrix;

import com.yksys.isystem.common.core.dto.Result;
import org.springframework.http.HttpStatus;

/**
 * @program: YK-iSystem
 * @description: 通用的熔断方法
 * @author: YuKai Fan
 * @create: 2019-12-05 09:46
 **/
public class Fallback {

    public static Result badGateway() {
        return new Result(HttpStatus.BAD_GATEWAY.value(), HttpStatus.BAD_GATEWAY.getReasonPhrase());
    }
}