package com.wuchaooooo.service.zjutsds.pojo.po;

import lombok.Data;

import java.util.Date;

/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Data
public class PUserInfo {
    //高考号
    private String nceeId;
    //姓名
    private String name;
    //性别 1男 0女
    private Integer gender;
    //身份证号
    private String idCard;
    //籍贯
    private String nativePlace;
    //毕业高中
    private String highSchool;
    //高考分数
    private Integer nceeScore;
    //手机号码
    private String mobile;
    //邮箱地址
    private String email;
    private Integer r;
    private Integer c;
    private Integer e;
    private Integer s;
    private Integer a;
    private Integer i;
    private String sdsName;
    private Date gmtCreateInfo;
    private Date gmtCreateTopic;
}
