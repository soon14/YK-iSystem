package com.yksys.isystem.common.pojo;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @program: YK-iSystem
 * @description: 系统用户表
 * @author: YuKai Fan
 * @create: 2019-12-03 14:22
 **/
@Data
public class SystemUser {
    //用户标识
    private String id;
    //用户账号
    private String account;
    //用户名称
    private String userName;
    //密码
    private String password;
    //用户昵称
    private String nickName;
    //头像
    private String userIcon;
    //最后登录时间
    private String lastLoginTime;
    //年龄
    private Integer age;
    //性别 1男  0女
    private Integer sex;
    //婚否
    private Integer marryFlag;
    //学历
    private Integer education;
    //手机号
    private String phone;
    //邮箱
    private String email;
    //省级
    private String prov;
    //地市级
    private String city;
    //区县
    private String dist;
    //地址
    private String address;
    //身份证号
    private String idCard;
    //备注
    private String remark;
    //状态:0  已禁用 1 正在使用
    private Integer status;
    //盐
    private String salt;
    //角色
    private List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");
}