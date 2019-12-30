package com.yksys.isystem.common.core.filter;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yksys.isystem.common.core.utils.StringUtil;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * @program: yk-isystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-30 20:08
 **/
public class XServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    private final byte[] body;
    private Map<String, String>customHeaders;

    public XServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.request = request;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(request.getInputStream(), byteArrayOutputStream);
        this.body = byteArrayOutputStream.toByteArray();
        this.customHeaders = Maps.newHashMap();
    }

    public void putHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> set = Sets.newHashSet(customHeaders.keySet());
        Enumeration<String> enumeration = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            set.add(name);
        }

        return Collections.enumeration(set);
    }

    @Override
    public String getHeader(String name) {
        String value = this.customHeaders.get(name);
        if (StringUtil.isNotEmpty(value)) {
            return value;
        }
        return ((HttpServletRequest)getRequest()).getHeader(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        for (int i = 0; i < parameterValues.length; i++) {
            String value = parameterValues[i];
            parameterValues[i] = StringUtil.trim(value).trim();
        }
        return parameterValues;
    }

    @Override
    public String getParameter(String name) {
        String value = request.getParameter(name);
        if (StringUtil.isNotEmpty(value)) {
            value = StringUtil.trim(value).trim();
        }
        return value;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }


}