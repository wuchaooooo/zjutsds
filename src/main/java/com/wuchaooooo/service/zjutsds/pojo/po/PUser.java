package com.wuchaooooo.service.zjutsds.pojo.po;

import lombok.Data;

import java.util.Date;

/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Data
public class PUser {
    private int id;
    private String userName;
    private String password;
    private String role;
    private Date gmtCreate;
    private Date gmtModify;
}
