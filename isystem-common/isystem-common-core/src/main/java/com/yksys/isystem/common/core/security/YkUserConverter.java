package com.yksys.isystem.common.core.security;

import com.google.common.collect.Maps;
import com.yksys.isystem.common.core.utils.MapUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-16 14:16
 **/
public class YkUserConverter extends DefaultUserAuthenticationConverter {

    public YkUserConverter() {
    }

    private Object converter(Map<String, ?> map) {
        Map<String, Object> params = Maps.newHashMap();
        for (String key : map.keySet()) {
            if (USERNAME.equals(key)) {
                if (map.get(key) instanceof Map) {
                    params.putAll((Map) map.get(key));
                }
                else  if (map.get(key) instanceof YkUserDetails) {
                    return map.get(key);
                }else {
                    params.put(key, map.get(key));
                }
            } else {
                params.put(key, map.get(key));
            }
        }
        YkUserDetails auth = MapUtil.mapToObject(YkUserDetails.class, params, false);
        if (params.get(USERNAME) != null) {
            auth.setUsername(params.get(USERNAME).toString());
        }
        if (params.get(YkSecurityConstants.OPEN_ID) != null) {
            auth.setUserId(params.get(YkSecurityConstants.OPEN_ID).toString());
        }
        auth.setClientId(params.get(AccessTokenConverter.CLIENT_ID).toString());
        auth.setAuthorities(getAuthorities(map));
        return auth;
    }

    /**
     * 获取权限
     * @param map
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return AuthorityUtils.NO_AUTHORITIES;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(
                    StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        }

        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}