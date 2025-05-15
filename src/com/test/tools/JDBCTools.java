package com.test.tools;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class JDBCTools {
    private static DataSource ds;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            Properties pro = new Properties();
            pro.load(JDBCTools.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = ds.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    public static void freeConnection() throws Exception {
        Connection connection = threadLocal.get();
        if (connection != null) {
            threadLocal.remove();
            connection.close();
        }
    }
}
