package com.yksys.isystem.service.auth.service.impl;

import com.yksys.isystem.service.auth.service.BaseClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-13 14:48
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    private BaseClientService baseClientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails details = baseClientService.getOauth2ClientInfo(clientId);
        if (details != null && details.getClientId() != null && details.getAdditionalInformation() != null) {
            String status = details.getAdditionalInformation().getOrDefault("status", "0").toString();
            if (!"1".equals(status)) {
                throw new ClientRegistrationException("客户端已被禁用");
            }
        }
        return details;
    }
}