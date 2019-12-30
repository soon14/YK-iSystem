package com.yksys.isystem.zuul.filter;

import com.yksys.isystem.common.core.constants.ComConstants;
import com.yksys.isystem.common.core.filter.XServletRequestWrapper;
import com.yksys.isystem.common.core.interceptor.FeignRequestInterceptor;
import com.yksys.isystem.common.core.utils.AppUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @program: YK-iSystem
 * @description: 请求前缀过滤器, 增加请求时间
 * @author: YuKai Fan
 * @create: 2019-12-30 09:10
 **/
public class PreRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute("requestTime", new Date());
        // 防止流读取一次丢失问题
        XServletRequestWrapper requestWrapper = new XServletRequestWrapper(request);
        String xid = AppUtil.randomId();
        //添加自定义请求头
        requestWrapper.putHeader(ComConstants.REQUEST_HEADER_ID, xid);
        response.setHeader(ComConstants.REQUEST_HEADER_ID, xid);
        filterChain.doFilter(requestWrapper, response);
    }
}