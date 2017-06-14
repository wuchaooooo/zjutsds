package com.wuchaooooo.service.zjutsds.dao;

import com.wuchaooooo.service.zjutsds.pojo.po.PMajor;
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
 * Created by wuchaooooo on 12/06/2017.
 */
@Repository
public class MajorDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(MajorDAO.class);

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public List<PMajor> listMajor(String sdsName) {
        String sql = "select * from " + getTable() + " where `sds1` = ?";
        RowMapper<PMajor> rowMapper = new BeanPropertyRowMapper<>(PMajor.class);
        try {
            return jdbcTemplate.query(sql, rowMapper, sdsName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private String getTable() {
        return "major";
    }
}
