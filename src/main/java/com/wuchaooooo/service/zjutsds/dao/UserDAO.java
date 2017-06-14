package com.wuchaooooo.service.zjutsds.dao;

import com.wuchaooooo.service.zjutsds.pojo.po.PMajor;
import com.wuchaooooo.service.zjutsds.pojo.po.PUser;
import com.wuchaooooo.service.zjutsds.pojo.po.PUserInfo;
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
        String sql = "insert into " + getTable() + " (`userName`, `password`, `role`, `gmtCreate`) values (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, new Object[]{pUser.getUserName(), pUser.getPassword(), pUser.getRole(), pUser.getGmtCreate()});
    }

    public int removeUser(int id) {
        String sql = "delete from " + getTable() + " where `id` = ?";
        return jdbcTemplate.update(sql, new Object[] {id});
    }


    private String getTable() {
        return "user";
    }

}
