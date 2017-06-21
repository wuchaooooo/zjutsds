package com.wuchaooooo.service.zjutsds.dao;

import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wuchaooooo on 08/06/2017.
 */
@Repository
public class UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public PUser getUser(int id) {
        String sql = "select * from " + getTable() + " where `id` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(PUser.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public PUser getUser(String userName) {
        String sql = "select * from " + getTable() + " where `userName` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new BeanPropertyRowMapper<>(PUser.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public PUser getUser(String userName, String password) {
        String sql = "select * from " + getTable() + " where `userName` = ? and `password` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userName, password}, new BeanPropertyRowMapper<>(PUser.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<PUser> listUser() {
        String sql = "select * from " + getTable();
        RowMapper<PUser> rowMapper = new BeanPropertyRowMapper<>(PUser.class);
        try {
            return jdbcTemplate.query(sql, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int saveUser(PUser pUser) {
        String sql = "insert into " + getTable() + " (`userName`, `password`, `role`, `name`) values (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{pUser.getUserName(), pUser.getPassword(), pUser.getRole(), pUser.getName()});
    }

    public int removeUser(int id) {
        String sql = "delete from " + getTable() + " where `id` = ?";
        return jdbcTemplate.update(sql, new Object[] {id});
    }

    public int saveUserInfo(PUser pUser) {
        String sql = "UPDATE " + getTable() + " SET `nceeId` = ?, `gender` = ?, `nativePlace` = ?, `highSchool` = ?, `nceeScore` = ?, `mobile` = ?, `email` = ?, `gmtCreateInfo` = ? WHERE `userName` = ?";
        return jdbcTemplate.update(sql, new Object[]{pUser.getNceeId(), pUser.getGender(), pUser.getNativePlace(), pUser.getHighSchool(), pUser.getNceeScore(), pUser.getMobile(), pUser.getEmail(), pUser.getGmtCreateInfo(), pUser.getUserName()});
    }

    public int saveResult(PUser pUser) {
        String sql = "UPDATE " + getTable() + " SET `r` = ?, `c` = ?, `e` = ?, `s` = ?, `a` = ?, `i` = ?, `sdsName` = ?, `gmtCreateTopic` = ? WHERE `userName` = ?";
        return jdbcTemplate.update(sql, new Object[]{pUser.getR(), pUser.getC(), pUser.getE(), pUser.getS(), pUser.getA(), pUser.getI(), pUser.getSdsName(), pUser.getGmtCreateTopic(), pUser.getUserName()});
    }

    public int activateUser(int flag, int id) {
        String sql = "UPDATE " + getTable() + " SET `isActivated` = ? WHERE `id` = ?";
        return jdbcTemplate.update(sql, new Object[]{flag, id});
    }

    private String getTable() {
        return "user";
    }

}
