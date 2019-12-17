package com.yksys.isystem.service.auth.service.impl;

import com.yksys.isystem.common.core.security.YkClientDetails;
import com.yksys.isystem.service.auth.service.BaseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-17 11:28
 **/
@Service
public class BaseClientServiceImpl implements BaseClientService {
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Override
    public YkClientDetails getOauth2ClientInfo(String clientId) {
        BaseClientDetails baseClientDetails;
        try {
            baseClientDetails = (BaseClientDetails) jdbcClientDetailsService.loadClientByClientId(clientId);
        } catch (InvalidClientException e) {
            return null;
        }
        YkClientDetails ykClientDetails = new YkClientDetails();
        ykClientDetails.convertFor(baseClientDetails);
        return ykClientDetails;
    }
}