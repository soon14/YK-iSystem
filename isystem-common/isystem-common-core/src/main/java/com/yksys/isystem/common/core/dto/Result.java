package com.yksys.isystem.common.core.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-04 09:09
 **/
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -3752082498241325137L;
    //返回结果信息
    private String message;
    //返回结果码
    private int code;
    //返回数据
    private T data;

    //在使用Feign, 返回实体不知道为什么需要一个空构造, 否则会一直调用fallback方法
    public Result() {
    }

    public Result(int code, String message, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public Result(int code, String message) {
        this.message = message;
        this.code = code;
    }
}