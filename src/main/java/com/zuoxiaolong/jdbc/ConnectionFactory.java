package com.zuoxiaolong.jdbc;

/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zuoxiaolong.config.Configuration;

/**
 * @author 左潇龙
 * @since 5/7/2015 10:23 AM
 */
public abstract class ConnectionFactory {

    private static final String url;

    private static final String username;

    private static final String password;

    private static final String driver;
    
    static {
        try {
            url = Configuration.get("url");
            driver = Configuration.get("driver");
            if (Configuration.isProductEnv()) {
                username = Configuration.get("username.product");
                password = Configuration.get("password.product");
            } else {
                username = Configuration.get("username");
                password = Configuration.get("password");
            }
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
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
