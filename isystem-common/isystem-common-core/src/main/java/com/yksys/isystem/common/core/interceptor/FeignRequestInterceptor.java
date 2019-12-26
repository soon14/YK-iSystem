package com.yksys.isystem.common.core.interceptor;

import com.google.common.collect.Maps;
import com.yksys.isystem.common.core.constants.ComConstants;
import com.yksys.isystem.common.core.utils.AppUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-26 11:55
 **/
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if (request != null) {
            Map<String, String> headers = getHeaders(request);
            headers.forEach((key, value) -> requestTemplate.header(key, value));
        }

        if (request.getHeader(ComConstants.REQUEST_HEADER_ID) == null) {
            requestTemplate.header(ComConstants.REQUEST_HEADER_ID, AppUtil.randomId());
        }

        log.debug("FeignRequestInterceptor: {}", request.toString());

    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = Maps.newLinkedHashMap();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }
}