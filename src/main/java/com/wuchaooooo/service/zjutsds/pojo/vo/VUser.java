package com.wuchaooooo.service.zjutsds.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Data
public class VUser {
    private int id;
    //登录账号（身份证号）
    private String userName;
    //登录密码
    private String password;
    //真实姓名
    private String name;
    private String role;
    private Date gmtCreate;
    private Date gmtModify;
}
