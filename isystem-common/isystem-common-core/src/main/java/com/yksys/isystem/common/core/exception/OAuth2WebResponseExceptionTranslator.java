package com.yksys.isystem.common.core.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-16 14:29
 **/
public class OAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        ExceptionResult exceptionResult = globalExceptionHandler.handleOauth2WebResponseException(e);
        return ResponseEntity.status(exceptionResult.getCode()).body(exceptionResult);
    }
}