package com.yksys.isystem.common.core.security;

import com.yksys.isystem.common.core.exception.ExceptionResult;
import com.yksys.isystem.common.core.exception.GlobalExceptionHandler;
import com.yksys.isystem.common.core.utils.WebUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-17 09:16
 **/
public class YkAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ExceptionResult exceptionResult = globalExceptionHandler.handleOauth2WebResponseException(e);
        WebUtil.writeJson(httpServletResponse, exceptionResult);
    }
}