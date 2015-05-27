package com.zuoxiaolong.dao;

import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.util.ImageUtil;

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
	
    private static final int SUMMARY_LENGTH = 100;
    
    private static final int SHORT_SUBJECT_LENGTH = 10;
    
    public static boolean saveOrUpdate(String resourceId, String subject, String createDate, Integer status,String username, Integer accessTimes, Integer goodTimes, String html, String content) {
    	return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				String selectSql = "select id,status from articles where resource_id=?";
				String insertSql = "insert into articles (resource_id,username,icon,create_date," +
                "access_times,good_times,subject,html,content,status) values (?,?,?,?,?,?,?,?,?,?)";
				String updateSql = "update articles set subject=?,html=?,content=?,icon=?,status=? where resource_id=?";
				try {
					PreparedStatement statement = connection.prepareStatement(selectSql);
					statement.setString(1, resourceId);
					ResultSet resultSet = statement.executeQuery();
					boolean exsits = false;
					int currentStatus = 0;
					if (resultSet.next()) {
						exsits = true;
						currentStatus = resultSet.getInt("status");
					}
					PreparedStatement saveOrUpdate = null;
					if (!exsits) {
						saveOrUpdate = connection.prepareStatement(insertSql);
						saveOrUpdate.setString(1, resourceId);
						saveOrUpdate.setString(2, username);
						saveOrUpdate.setString(3, ImageUtil.randomArticleImage());
						saveOrUpdate.setString(4, createDate);
						saveOrUpdate.setInt(5, accessTimes);
						saveOrUpdate.setInt(6, goodTimes);
						saveOrUpdate.setString(7, subject);
						saveOrUpdate.setString(8, html);
						saveOrUpdate.setString(9, content);
						saveOrUpdate.setInt(10, status);
					} else {
						saveOrUpdate = connection.prepareStatement(updateSql);
						saveOrUpdate.setString(1, subject);
						saveOrUpdate.setString(2, html);
						saveOrUpdate.setString(3, content);
						saveOrUpdate.setString(4, ImageUtil.randomArticleImage());
                        saveOrUpdate.setInt(5, currentStatus == 1 ? currentStatus : status);
                        saveOrUpdate.setString(6, resourceId);
					}
					int result = saveOrUpdate.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
    }
    
    public static List<Map<String, String>> getPageArticles(final Map<String, Integer> pager, final ViewMode viewMode ) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                String sql = "select * from articles where status = 1 order by create_date desc limit ?,10";
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1 , (pager.get("current") - 1) * 10);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        result.add(transfer(resultSet, viewMode));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return result;
            }
        });
    }

    public static List<Map<String, String>> getArticles(final String order, final ViewMode viewMode ) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                String sql = "select * from articles where status = 1 order by " + order + " desc";
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    while (resultSet.next()) {
                        result.add(transfer(resultSet, viewMode));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return result;
            }
        });
    }

    public static Map<String, String> getArticle(final int id, final ViewMode viewMode ) {
        return execute(new Operation<Map<String, String>>() {
            @Override
            public Map<String, String> doInConnection(Connection connection) {
                String sql = "select * from articles where status = 1 and id = " + id;
                Map<String, String> result = new HashMap<String, String>();
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    if (resultSet.next()) {
                        result = transfer(resultSet, viewMode);
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
    
    private static Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> article = new HashMap<String, String>();
        try {
            String id = resultSet.getString("id");
            article.put("id", id);
            if (viewMode == ViewMode.DYNAMIC) {
                article.put("url", "/blog/article.ftl?id=" + id);
            } else {
                article.put("url", "/html/article_" + id + ".html");
            }
            article.put("icon", resultSet.getString("icon"));
            article.put("subject", resultSet.getString("subject"));
            article.put("username", resultSet.getString("username"));
            article.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("create_date")));
            article.put("access_times", resultSet.getString("access_times"));
            article.put("comment_times", resultSet.getString("comment_times"));

            article.put("good_times", resultSet.getString("good_times"));
            article.put("touch_times", resultSet.getString("touch_times"));
            article.put("funny_times", resultSet.getString("funny_times"));
            article.put("happy_times", resultSet.getString("happy_times"));
            article.put("anger_times", resultSet.getString("anger_times"));
            article.put("bored_times", resultSet.getString("bored_times"));
            article.put("water_times", resultSet.getString("water_times"));
            article.put("surprise_times", resultSet.getString("surprise_times"));
            article.put("html", resultSet.getString("html"));
            String content = resultSet.getString("content");
            article.put("summary", content.substring(0, content.length() < SUMMARY_LENGTH ? content.length() : SUMMARY_LENGTH));
            String subject = resultSet.getString("subject");
            article.put("shortSubject", subject.length() < SHORT_SUBJECT_LENGTH ? subject : (subject.substring(0, SHORT_SUBJECT_LENGTH) + " ..."));
            putAllTimesHeight(article);
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
