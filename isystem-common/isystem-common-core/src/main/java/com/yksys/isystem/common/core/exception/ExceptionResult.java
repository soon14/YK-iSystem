package com.yksys.isystem.common.core.exception;

import lombok.Data;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-04 11:33
 **/
@Data
public class ExceptionResult {
    private int code;
    private String message;

    public ExceptionResult() {
    }

    public static ExceptionResult create(int code, String message) {
        return new ExceptionResult(code, message);
    }

    public ExceptionResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}