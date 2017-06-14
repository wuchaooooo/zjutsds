package com.wuchaooooo.service.zjutsds.pojo.vo;

import lombok.Data;

/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Data
public class VUserInfo {
    //高考号
    private String nceeId;
    //姓名
    private String name;
    //性别 1男 0女
    private int gender;
    //身份证号
    private String idCard;
    //籍贯
    private String nativePlace;
    //毕业高中
    private String highSchool;
    //高考分数
    private int nceeScore;
    //手机号码
    private String mobile;
    //邮箱地址
    private String email;
}
