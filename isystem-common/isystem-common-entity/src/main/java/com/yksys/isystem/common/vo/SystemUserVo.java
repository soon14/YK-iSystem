package com.yksys.isystem.common.vo;

import com.google.common.base.Converter;
import com.yksys.isystem.common.pojo.SystemUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @program: YK-iSystem
 * @description:
 * @author: YuKai Fan
 * @create: 2019-12-04 09:33
 **/
@Data
public class SystemUserVo implements Serializable {
    private static final long serialVersionUID = -8816454437885030486L;

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
    //盐
    private String salt;

    public SystemUser convert() {
        SystemUserVoConvert systemUserVoConvert = new SystemUserVoConvert();
        SystemUser systemUser = systemUserVoConvert.convert(this);
        return systemUser;
    }

    private static class SystemUserVoConvert extends Converter<SystemUserVo, SystemUser> {

        @Override
        protected SystemUser doForward(SystemUserVo systemUserVo) {
            SystemUser systemUser = new SystemUser();
            BeanUtils.copyProperties(systemUserVo, systemUser);
            return systemUser;
        }

        @Override
        protected SystemUserVo doBackward(SystemUser systemUser) {
            return null;
        }
    }
}