package com.wuchaooooo.service.zjutsds.dao;

import com.wuchaooooo.service.zjutsds.pojo.po.PCareer;
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
public class CareerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(CareerDAO.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public PCareer getCareer(String sdsName) {
        String sql = "select * from " + getTable() + " where `sdsName` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{sdsName}, new BeanPropertyRowMapper<>(PCareer.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private String getTable() {
        return "career";
    }

}
