package com.zuoxiaolong.dao;/*
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

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 5/19/2015 10:15 AM
 */
public abstract class MatchDao extends BaseDao {

    public static int count() {
        return execute(new Operation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select count(id) from matches");
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    error("query matches failed ..." , e);
                }
                return 0;
            }
        });
    }

    public static List<Map<String,String>> findMatchesResult(String h) {
        return execute(new Operation<List<Map<String,String>>>() {
            @Override
            public List<Map<String,String>> doInConnection(Connection connection) {
                List<Map<String,String>> result = new ArrayList<Map<String, String>>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from matches where attack ='" + h + "' or defend = '" +h + "'");
                    while (resultSet.next()) {
                        Map<String,String> match = new HashMap<String, String>();
                        match.put("attack", resultSet.getString("attack"));
                        match.put("defend", resultSet.getString("defend"));
                        match.put("result", String.valueOf(resultSet.getInt("result")));
                        result.add(match);
                    }
                } catch (SQLException e) {
                    error("query matches failed ..." , e);
                }
                return result;
            }
        });
    }

    public static boolean save(final String a, final String d , final Integer result) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = connection.prepareStatement("insert into matches (attack,defend,result) values (?,?,?)");
                    statement.setString(1, a);
                    statement.setString(2, d);
                    statement.setInt(3, result);
                    int result = statement.executeUpdate();
                    return result > 0;
                } catch (SQLException e) {
                    error("save matches failed ..." , e);
                }
                return false;
            }
        });
    }

}
