package com.yksys.isystem.common.model;

import com.yksys.isystem.common.pojo.SystemMenu;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-24 16:13
 **/
@Data
public class AuthorityMenu extends SystemMenu implements Serializable {
    private static final long serialVersionUID = 1141444819706835737L;
    /**
     * 权限ID
     */
    private Long authorityId;

    /**
     * 权限标识
     */
    private String authority;
}