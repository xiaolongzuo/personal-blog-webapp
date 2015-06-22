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

import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import com.zuoxiaolong.util.StringUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 15/6/21 00:25
 */
public class QuestionDao extends BaseDao {

    public boolean updateCount(Integer id) {
        return updateCount(id, "questions", "access_times");
    }

    public Map<String, String> getQuestion(final Integer id) {
        return getById("questions", id);
    }

    public Integer save(String username, String title, String description, String content) {
        return execute(new TransactionalOperation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                String sql = "insert into questions (username,title,description,content,create_date) values (?,?,?,?,?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, username);
                    statement.setString(2, title);
                    statement.setString(3, description);
                    statement.setString(4, content);
                    statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    int result = statement.executeUpdate();
                    if (result > 0) {
                        ResultSet keyResultSet = statement.getGeneratedKeys();
                        if (keyResultSet.next()) {
                            return keyResultSet.getInt(1);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    public Integer getTotal() {
        return execute(new Operation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                List<Map<String, String>> questions = new ArrayList<Map<String,String>>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select count(*) from questions");
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    error("get questions failed ..." , e);
                }
                return 0;
            }
        });
    }

    public List<Map<String, String>> getQuestions(Map<String, Integer> pager) {
        return getPager(pager, "questions");
    }

    public Map<String, String> transfer(ResultSet resultSet){
        Map<String, String> question = new HashMap<String, String>();
        try {
            question.put("id", resultSet.getString("id"));
            question.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("create_date")));
            question.put("title", resultSet.getString("title"));
            question.put("description", resultSet.getString("description"));
            String content = resultSet.getString("content");
            question.put("summary", StringUtil.substring(content, 100));
            question.put("username", resultSet.getString("username"));
            question.put("access_times", resultSet.getString("access_times"));
            question.put("answer_number", resultSet.getString("answer_number"));
            question.put("is_resolved", resultSet.getString("is_resolved"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return question;
    }

}
