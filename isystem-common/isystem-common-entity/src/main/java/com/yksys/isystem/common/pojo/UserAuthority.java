package com.yksys.isystem.common.pojo;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-18 09:29
 **/
@Data
public class UserAuthority extends SystemUser implements Serializable {
    private static final long serialVersionUID = 5450606777116188809L;

    /**
     * 角色集合
     */
    private List<Map<String, Object>> roles = Lists.newArrayList();


}