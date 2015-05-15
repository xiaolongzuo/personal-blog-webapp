package com.zuoxiaolong.dao;

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

import com.zuoxiaolong.api.HttpApiHelper;
import com.zuoxiaolong.cache.Caches;
import com.zuoxiaolong.config.Configuration;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 左潇龙
 * @since 5/14/2015 12:03 PM
 */
public abstract class AccessLogDao extends BaseDao {

    private static final long TIME_QUANTUM = Long.valueOf(Configuration.get("time.quantum"));

    private static final long TIMES_PER_SECOND = Long.valueOf(Configuration.get("times.per.second"));

    public static boolean save(final String visitorIp, final String url, final String params) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                try {
                    String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis() - TIME_QUANTUM * 1000));
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select count(id) from access_log where visitor_ip='" + visitorIp + "' and access_date > '" + startTime + "'");
                    int count = -1;
                    if (resultSet.next() && (count = resultSet.getInt(1)) > TIME_QUANTUM * TIMES_PER_SECOND) {
                        warn("the ip[" + visitorIp + "] access too often , please note!");
                        Caches.getConcurrentHashMapCache().set(visitorIp, 1000 * 60 * 60);
                    }
                    info("query count : " + count);
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into access_log (visitor_ip,url,access_date,city,params) values (?,?,?,?,?)");
                    preparedStatement.setString(1, visitorIp);
                    preparedStatement.setString(2, url);
                    preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    preparedStatement.setString(4, HttpApiHelper.getCity(visitorIp));
                    preparedStatement.setString(5, params);
                    int result = preparedStatement.executeUpdate();
                    return result > 0;
                } catch (SQLException e) {
                    error("save remarkVisitorIp failed ...", e);
                }
                return false;
            }
        });
    }

}
