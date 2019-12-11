package com.yksys.isystem.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-06 10:11
 **/
@Data
public class RedisVo implements Serializable {
    private static final long serialVersionUID = -4846499850058410453L;
    //键
    private String key;
    //值
    private Object value;
    //过期时间
    private long time;
}