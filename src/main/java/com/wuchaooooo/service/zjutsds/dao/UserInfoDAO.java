package com.wuchaooooo.service.zjutsds.dao;

import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
import com.wuchaooooo.service.zjutsds.pojo.po.PUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Repository
public class UserInfoDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoDAO.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public int saveUserInfo(PUserInfo pUserInfo) {
        String sql = "insert into " + getTable() + " (`nceeId`, `name`, `gender`, `idCard`, `nativePlace`, `highSchool`, `nceeScore`, `mobile`, `email`, `gmtCreateInfo`) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{pUserInfo.getNceeId(), pUserInfo.getName(), pUserInfo.getGender(), pUserInfo.getIdCard(), pUserInfo.getNativePlace(), pUserInfo.getHighSchool(), pUserInfo.getNceeScore(), pUserInfo.getMobile(), pUserInfo.getEmail(), pUserInfo.getGmtCreateTopic()});
    }

    public PUserInfo getUserInfo(String userName) {
        String sql = "select * from " + getTable() + " where `idCard` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new BeanPropertyRowMapper<>(PUserInfo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int saveResult(PUserInfo pUserInfo) {
        String sql = "UPDATE " + getTable() + " SET `r` = ?, `c` = ?, `e` = ?, `s` = ?, `a` = ?, `i` = ?, `sdsName` = ?, `gmtCreateTopic` = ? WHERE `idCard` = ?";
        return jdbcTemplate.update(sql, new Object[]{pUserInfo.getR(), pUserInfo.getC(), pUserInfo.getE(), pUserInfo.getS(), pUserInfo.getA(), pUserInfo.getI(), pUserInfo.getSdsName(), pUserInfo.getGmtCreateTopic(), pUserInfo.getIdCard()});
    }

    private String getTable() {
        return "user_info";
    }

}
