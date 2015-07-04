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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import org.apache.commons.lang.StringUtils;

import com.zuoxiaolong.freemarker.ArticleHelper;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.util.DateUtil;
import com.zuoxiaolong.util.ImageUtil;
import com.zuoxiaolong.util.StringUtil;

/**
 * @author 左潇龙
 * @since 5/7/2015 3:40 PM
 */
public class ArticleDao extends BaseDao {
	
	public Boolean delete(Integer id) {
    	return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				String updateSql = "update articles set status=0 where id=?";
				try {
					PreparedStatement statement = connection.prepareStatement(updateSql);
					statement.setInt(1, id);
					int result = statement.executeUpdate();
					if (result > 0) {
						return true;
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				return false;
			}
		});
    }
	
    public List<Map<String, String>> getPageArticlesByTag(final Map<String, Integer> pager,final int tagId, ViewMode viewMode) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> result = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_tag where tag_id=? ) and type=0 order by create_date desc limit ?,10");
					statement.setInt(1 , tagId);
					statement.setInt(2 , (pager.get("current") - 1) * 10);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						result.add(transfer(resultSet, viewMode));
					}
				} catch (SQLException e) {
					error("query article_category failed ..." , e);
				}
				return result;
			}
		});
	}
    
    public List<Map<String, String>> getArticlesByTag(final int tagId, ViewMode viewMode) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> result = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_tag where tag_id=?  and type=0 )");
					statement.setInt(1 , tagId);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						result.add(transfer(resultSet, viewMode));
					}
				} catch (SQLException e) {
					error("query article_category failed ..." , e);
				}
				return result;
			}
		});
	}
    
    public List<Map<String, String>> getPageArticlesByCategory(final Map<String, Integer> pager,final int categoryId, ViewMode viewMode) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> result = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_category where category_id=? )  and type=0 order by create_date desc limit ?,10");
					statement.setInt(1, categoryId);
					statement.setInt(2 , (pager.get("current") - 1) * 10);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						result.add(transfer(resultSet, viewMode));
					}
				} catch (SQLException e) {
					error("query article_category failed ..." , e);
				}
				return result;
			}
		});
	}
    
    public List<Map<String, String>> getArticlesByCategory(final int categoryId, ViewMode viewMode) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> result = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_category where category_id=?  and type=0 )");
					statement.setInt(1, categoryId);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						result.add(transfer(resultSet, viewMode));
					}
				} catch (SQLException e) {
					error("query article_category failed ..." , e);
				}
				return result;
			}
		});
	}

    public List<Map<String, String>> getPageArticlesByType(final Map<String, Integer> pager,final int type, ViewMode viewMode) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                List<Map<String, String>> result = new ArrayList<Map<String,String>>();
                try {
                    PreparedStatement statement = connection.prepareStatement("select * from articles where type=? order by create_date desc limit ?,10");
                    statement.setInt(1, type);
                    statement.setInt(2 , (pager.get("current") - 1) * 10);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        result.add(transfer(resultSet, viewMode));
                    }
                } catch (SQLException e) {
                    error("query article_category failed ..." , e);
                }
                return result;
            }
        });
    }

    public List<Map<String, String>> getArticlesByType(final int type, ViewMode viewMode) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                List<Map<String, String>> result = new ArrayList<Map<String,String>>();
                try {
                    PreparedStatement statement = connection.prepareStatement("select * from articles where type=? ");
                    statement.setInt(1, type);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        result.add(transfer(resultSet, viewMode));
                    }
                } catch (SQLException e) {
                    error("query article_category failed ..." , e);
                }
                return result;
            }
        });
    }
    
    public Integer saveOrUpdate(String id, String subject, Integer type, Integer status,String username, String html, String content, String icon) {
    	return execute((TransactionalOperation<Integer>) connection -> {
            String insertSql = "insert into articles (subject,username,icon,create_date," +
                                    "html,content,status,type) values (?,?,?,?,?,?,?,?)";
            String updateSql = "update articles set subject=?,username=?,icon=?,create_date=?,html=?,content=?,status=?,type=? where id=?";
            try {
                PreparedStatement statement = null;
                if (StringUtils.isBlank(id)) {
                    statement = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, subject);
                    statement.setString(2, username);
                    statement.setString(3, icon == null ? ImageUtil.randomArticleImage() : icon);
                    statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    statement.setString(5, html);
                    statement.setString(6, content);
                    statement.setInt(7, status);
                    statement.setInt(8, type);
                } else {
                    statement = connection.prepareStatement(updateSql);
                    statement.setString(1, subject);
                    statement.setString(2, username);
                    statement.setString(3, icon == null ? ImageUtil.randomArticleImage() : icon);
                    statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    statement.setString(5, html);
                    statement.setString(6, content);
                    statement.setInt(7, status);
                    statement.setInt(8, type);
                    statement.setInt(9, Integer.valueOf(id));
                }
                int result = statement.executeUpdate();
                if (result > 0 && StringUtils.isBlank(id)) {
                    ResultSet keyResultSet = statement.getGeneratedKeys();
                    if (keyResultSet.next()) {
                        return keyResultSet.getInt(1);
                    }
                }
                if (result > 0) {
                    return Integer.valueOf(id);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }
    
    public Integer saveOrUpdate(String resourceId, String subject, String createDate, Integer status,String username, Integer accessTimes, Integer goodTimes, String html, String content) {
    	return execute((TransactionalOperation<Integer>) connection -> {
            String selectSql = "select id,status from articles where resource_id=?";
            String insertSql = "insert into articles (resource_id,username,icon,create_date," +
                "access_times,good_times,subject,html,content,status) values (?,?,?,?,?,?,?,?,?,?)";
            String updateSql = "update articles set subject=?,html=?,content=?,icon=?,status=? where resource_id=? and type=0 ";
            try {
                PreparedStatement statement = connection.prepareStatement(selectSql);
                statement.setString(1, resourceId);
                ResultSet resultSet = statement.executeQuery();
                Boolean exsits = false;
                Integer currentStatus = 0;
                Integer id = null;
                if (resultSet.next()) {
                    exsits = true;
                    currentStatus = resultSet.getInt("status");
                    id = resultSet.getInt("id");
                }
                PreparedStatement saveOrUpdate = null;
                if (!exsits) {
                    saveOrUpdate = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
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
                if (!exsits && result > 0) {
                    ResultSet keyResultSet = saveOrUpdate.getGeneratedKeys();
                    if (keyResultSet.next()) {
                        id = keyResultSet.getInt(1);
                    }
                }
                return id;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    public List<Map<String, String>> getPageArticles(final Map<String, Integer> pager,final Status status, final String orderColumn, final ViewMode viewMode ) {
        return execute((Operation<List<Map<String, String>>>) connection -> {
            String sql = "select * from articles where status = ? order by " + orderColumn + " desc limit ?,10";
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1 , status.getIntValue());
                statement.setInt(2 , (pager.get("current") - 1) * 10);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        });
    }

    public List<Map<String, String>> getArticles(final String order,final Status status, final ViewMode viewMode ) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                String sql = "select * from articles where status = ? order by " + order + " desc";
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                try {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setInt(1 , status.getIntValue());
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

    public Map<String, String> getArticle(final int id,final Status status, final ViewMode viewMode ) {
        return execute(new Operation<Map<String, String>>() {
            @Override
            public Map<String, String> doInConnection(Connection connection) {
                String sql = "select * from articles where status = ? and id = " + id;
                Map<String, String> result = new HashMap<String, String>();
                try {
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setInt(1 , status.getIntValue());
                    ResultSet resultSet = statement.executeQuery();
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
    
    public boolean updateCommentCount(final int id) {
		return updateTimesCount("articles", "comment_times", "comments", "article_id", id);
    }

    public boolean updateCount(final int id, final String column) {
		return updateCount(id, "articles", column);
    }

    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> article = new HashMap<String, String>();
        try {
            String id = resultSet.getString("id");
            article.put("id", id);
            if (viewMode == ViewMode.DYNAMIC) {
                article.put("url", ArticleHelper.generateDynamicPath(Integer.valueOf(id)));
            } else {
                article.put("url", ArticleHelper.generateStaticPath(Integer.valueOf(id)));
            }
            article.put("icon", resultSet.getString("icon"));
            article.put("subject", resultSet.getString("subject"));
            article.put("username", resultSet.getString("username"));
            Timestamp createDate = resultSet.getTimestamp("create_date");
            article.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate));
            article.put("us_create_date", DateUtil.rfc822(createDate));
            article.put("status", resultSet.getString("status"));
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
            String html = resultSet.getString("html");
            article.put("html", html);
            article.put("escapeHtml", StringUtil.escapeHtml(html));
            String content = resultSet.getString("content");
            article.put("content", content);
            article.put("summary", StringUtil.substring(content, 100));
            String subject = resultSet.getString("subject");
            article.put("short_subject", StringUtil.substring(subject, 10) + (subject.length() > 10 ? "..." : ""));
            article.put("common_subject", StringUtil.substring(subject, 15) + (subject.length() > 15 ? "..." : ""));
            putAllTimesHeight(article);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return article;
    }

    private void putAllTimesHeight(Map<String, String> article) {
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
