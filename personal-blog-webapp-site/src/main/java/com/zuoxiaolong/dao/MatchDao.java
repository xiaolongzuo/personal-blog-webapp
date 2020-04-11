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

import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 5/19/2015 10:15 AM
 */
public class MatchDao extends BaseDao {

    public int count() {
        return execute(new Operation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select sum(count) from matches");
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    error("query matches failed ...", e);
                }
                return 0;
            }
        });
    }

    public List<Map<String, String>> getAll() {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from matches");
                    while (resultSet.next()) {
                        Map<String, String> match = new HashMap<String, String>();
                        match.put("attack", resultSet.getString("attack"));
                        match.put("defend", resultSet.getString("defend"));
                        match.put("result", String.valueOf(resultSet.getInt("result")));
                        match.put("count", String.valueOf(resultSet.getInt("count")));
                        result.add(match);
                    }
                } catch (SQLException e) {
                    error("query matches failed ...", e);
                }
                return result;
            }
        });
    }

    public List<Map<String, String>> findMatchesResult(String h) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                try {
                    PreparedStatement statement = connection.prepareStatement("select * from matches where attack=? or defend=?");
                    statement.setString(1, h);
                    statement.setString(2, h);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Map<String, String> match = new HashMap<String, String>();
                        match.put("attack", resultSet.getString("attack"));
                        match.put("defend", resultSet.getString("defend"));
                        match.put("result", String.valueOf(resultSet.getInt("result")));
                        match.put("count", String.valueOf(resultSet.getInt("count")));
                        result.add(match);
                    }
                } catch (SQLException e) {
                    error("query matches failed ...", e);
                }
                return result;
            }
        });
    }

    public boolean save(final String a, final String d, final Integer result, final Integer count) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = null;
                    if (count != null && count > 0) {
                        statement = connection.prepareStatement("insert into matches (attack,defend,result,record_date,count) values (?,?,?,?,?)");
                    } else {
                        statement = connection.prepareStatement("insert into matches (attack,defend,result,record_date) values (?,?,?,?)");
                    }
                    statement.setString(1, a);
                    statement.setString(2, d);
                    statement.setInt(3, result);
                    statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    if (count != null && count > 0) {
                        statement.setInt(5, count);
                    }
                    int result = statement.executeUpdate();
                    return result > 0;
                } catch (SQLException e) {
                    error("save matches failed ...", e);
                }
                return false;
            }
        });
    }

    @Override
    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        throw new UnsupportedOperationException();
    }

}
