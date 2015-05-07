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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 5/7/2015 3:40 PM
 */
public abstract class ArticleDao extends BaseDao {

    public static List<Map<String, String>> getArticles(final String order) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                String sql = "select * from articles order by " + order + " desc";
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    info("getArticles's resultSet : " + resultSet);
                    while (resultSet.next()) {
                        result.add(transfer(resultSet));
                    }
                    info("transfer success ...");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return result;
            }
        });
    }

    public static Map<String, String> getArticle(final int id) {
        return execute(new Operation<Map<String, String>>() {
            @Override
            public Map<String, String> doInConnection(Connection connection) {
                String sql = "select * from articles where id = " + id;
                Map<String, String> result = new HashMap<String, String>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        result = transfer(resultSet);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return result;
            }
        });
    }

    private static Map<String, String> transfer(ResultSet resultSet) {
        Map<String, String> article = new HashMap<String, String>();
        info("start transfer ...");
        try {
            article.put("id", resultSet.getString("id"));
            info("transfer id success ...");
            article.put("icon", resultSet.getString("icon"));
            info("transfer icon success ...");
            article.put("subject", resultSet.getString("subject"));
            info("transfer subject success ...");
            article.put("username", resultSet.getString("username"));
            info("transfer username success ...");
            article.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(resultSet.getDate("create_date")));
            info("transfer create_date success ...");
            article.put("access_times", resultSet.getString("access_times"));
            info("transfer access_times success ...");
            article.put("comment_times", resultSet.getString("comment_times"));
            info("transfer comment_times success ...");
            String content = resultSet.getString("content");
            article.put("content", content);
            article.put("summary", content.substring(0, content.length() < 50 ? content.length() : 50));
            info("transfer summary success ...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return article;
    }

}
