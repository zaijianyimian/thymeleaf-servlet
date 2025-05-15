package com.test.dao;

import com.test.tools.JDBCTools;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

public class BaseDao {
    private QueryRunner queryRunner = new QueryRunner();
    protected int update(String sql, Object... args) {
        try {
            Connection conn = JDBCTools.getConnection();
            return queryRunner.update(conn, sql, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected <T> T getBean(Class<T> clazz, String sql, Object... args){
        try {
            Connection conn = JDBCTools.getConnection();
            return queryRunner.query(conn, sql, new BeanHandler<T>(clazz), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> List<T> getList(Class<T> clazz, String sql, Object... args){
        try {
            Connection conn = JDBCTools.getConnection();
            return queryRunner.query(conn, sql, new BeanListHandler<T>(clazz), args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
