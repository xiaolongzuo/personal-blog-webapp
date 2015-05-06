package com.zuoxiaolong.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * <p>
 * 连接工厂
 * </p>
 *
 * @author 左潇龙
 * @since 5/6/2015 6:10 PM
 */
public abstract class ConnectionFactory {

    private static final String url;

    private static final String username;

    private static final String password;

    static {
        try {
            Properties properties = new Properties();
            properties.load(ClassLoader.getSystemResourceAsStream("jdbc.properties"));
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
