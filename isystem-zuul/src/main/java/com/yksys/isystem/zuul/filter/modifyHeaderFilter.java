package com.yksys.isystem.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.yksys.isystem.common.core.constants.ComConstants;

/**
 * @program: yk-isystem
 * @description: 修改Zuul 请求头
 * @author: YuKai Fan
 * @create: 2019-12-30 21:16
 **/
public class modifyHeaderFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(ComConstants.REQUEST_HEADER_ID, ctx.getRequest().getHeader(ComConstants.REQUEST_HEADER_ID));
        return null;
    }
}