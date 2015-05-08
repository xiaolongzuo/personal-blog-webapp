package com.zuoxiaolong.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public static boolean updateCount(final int id, final String column) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("update articles set " + column + " = " + column + " + 1 where id = ?");
                    preparedStatement.setInt(1, id);
                    int number = preparedStatement.executeUpdate();
                    return number > 0;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
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

            article.put("good_times", resultSet.getString("good_times"));
            info("transfer good_times success ...");
            article.put("touch_times", resultSet.getString("touch_times"));
            info("transfer touch_times success ...");
            article.put("funny_times", resultSet.getString("funny_times"));
            info("transfer funny_times success ...");
            article.put("happy_times", resultSet.getString("happy_times"));
            info("transfer happy_times success ...");
            article.put("anger_times", resultSet.getString("anger_times"));
            info("transfer anger_times success ...");
            article.put("bored_times", resultSet.getString("bored_times"));
            info("transfer bored_times success ...");
            article.put("water_times", resultSet.getString("water_times"));
            info("transfer water_times success ...");
            article.put("surprise_times", resultSet.getString("surprise_times"));
            info("transfer surprise_times success ...");
            String content = resultSet.getString("content");
            article.put("content", content);
            article.put("summary", content.substring(0, content.length() < 100 ? content.length() : 100));
            info("transfer summary success ...");
            putAllTimesHeight(article);
            info("transfer putAllTimesHeight success ...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return article;
    }

    private static void putAllTimesHeight(Map<String, String> article) {
        int max = 10;
        for (String key : article.keySet()) {
            if (key.endsWith("_times") && !key.startsWith("access") && !key.startsWith("comment")) {
                Integer times = Integer.valueOf(article.get(key));
                if (times > max) {
                    max = times;
                }
            }
        }
        Map<String, String> tempArticle = new HashMap<String, String>(article);
        for (String key : tempArticle.keySet()) {
            if (key.endsWith("_times") && !key.startsWith("access") && !key.startsWith("comment")) {
                article.put(key + "_height", String.valueOf(Integer.valueOf(article.get(key)) * 50 / max));
            }
        }
    }

}
