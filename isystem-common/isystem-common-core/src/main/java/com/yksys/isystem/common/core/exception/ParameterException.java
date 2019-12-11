package com.yksys.isystem.common.core.exception;

/**
 * @program: YK-iSystem
 * @description: 参数异常
 * @author: YuKai Fan
 * @create: 2019-12-04 10:16
 **/
public class ParameterException extends RuntimeException {
    //返回码
    private int code;
    //返回数据
    private Object data;

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException() {

    }
}