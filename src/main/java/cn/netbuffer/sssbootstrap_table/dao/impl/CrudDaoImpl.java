package cn.netbuffer.sssbootstrap_table.dao.impl;

import cn.netbuffer.sssbootstrap_table.dao.CrudDaoCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CrudDaoImpl implements CrudDaoCustom{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long getTotal(){
        return jdbcTemplate.queryForObject("select count(*) from t_crud_demo",Long.class);
    }

}
