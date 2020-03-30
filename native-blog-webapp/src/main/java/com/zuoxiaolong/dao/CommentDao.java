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
import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import com.zuoxiaolong.util.JsoupUtil;
import com.zuoxiaolong.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author 左潇龙
 * @since 2015年5月9日 下午11:34:14
 */
public class CommentDao extends BaseDao {

	public boolean updateCount(final int id, final String column) {
		return updateCount(id, column, 1);
	}
	
	public boolean updateCount(final int id, final String column, final int count) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				int finalCount = count;
				if (count <= 0 ) {
					finalCount = 1;
				}
				try {
					PreparedStatement preparedStatement = connection.prepareStatement("update comments set " + column + " = " + column + " + " + finalCount + " where id = ?");
					preparedStatement.setInt(1, id);
					int number = preparedStatement.executeUpdate();
					return number > 0;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	public Integer getId(String resourceId) {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select id from comments where resource_id=?");
					statement.setString(1, resourceId);
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						return resultSet.getInt(1);
					}
				} catch (SQLException e) {
					error("query comments failed ..." , e);
				}
				return null;
			}
		});
	}
	
	public boolean updateContent(Integer id, String content) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement preparedStatement = connection.prepareStatement("update comments set content=? where id = ?");
					preparedStatement.setString(1, content);
					preparedStatement.setInt(2, id);
					int number = preparedStatement.executeUpdate();
					return number > 0;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public Integer save(final Integer articleId, final String visitorIp,final Date commentDate,
		final String content,final String username,final String resourceUsername,final String resourceId,final Integer referenceCommentId) {
		return execute(new TransactionalOperation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = null;
					if (referenceCommentId == null) {
						statement = connection.prepareStatement("insert into comments (visitor_ip,city,content,article_id,"
								+ "create_date,username,resource_username,resource_id) values (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					} else {
						statement = connection.prepareStatement("insert into comments (visitor_ip,city,content,article_id,"
								+ "create_date,username,resource_username,resource_id,reference_comment_id) values (?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					}
					statement.setString(1, visitorIp);
					statement.setString(2, Configuration.isProductEnv() ? HttpApiHelper.getCity(visitorIp) : "来自星星的");
					statement.setString(3, content);
					statement.setInt(4, articleId);
					Date finalCommentDate = commentDate;
					if (commentDate == null) {
						finalCommentDate = new Date();
					}
					statement.setTimestamp(5, new Timestamp(finalCommentDate.getTime()));
					statement.setString(6, username);
					statement.setString(7, resourceUsername);
					statement.setString(8, resourceId);
					if (referenceCommentId != null) {
						statement.setInt(9, referenceCommentId);
					}
					int result = statement.executeUpdate();
					if (result > 0) {
						ResultSet resultSet = statement.getGeneratedKeys();
						if (resultSet.next()) {
							return resultSet.getInt(1);
						}
					}
				} catch (SQLException e) {
					error("save comments failed ..." , e);
				}
				return null;
			}
		});
	}

    public List<Map<String, String>> getLastComments(final Integer size, final ViewMode viewMode) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                List<Map<String, String>> comments = new ArrayList<Map<String,String>>();
                try {
                    PreparedStatement statement = connection.prepareStatement("select * from comments order by create_date DESC limit ? ");
                    statement.setInt(1, size);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        comments.add(transfer(resultSet, viewMode));
                    }
                } catch (SQLException e) {
                    error("get comments for size[" + size + "] failed ..." , e);
                }
                return comments;
            }
        });
    }

	public List<Map<String, String>> getComments(final Integer articleId) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> comments = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from comments where article_id=? order by create_date");
					statement.setInt(1, articleId);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						comments.add(transfer(resultSet, null));
					}
				} catch (SQLException e) {
					error("get comments for article[" + articleId + "] failed ..." , e);
				}
				return comments;
			}
		});
	}
	
	public List<Map<String, String>> getComments(int size) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> comments = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from comments order by create_date desc limit " + size);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						comments.add(transfer(resultSet, null));
					}
				} catch (SQLException e) {
					error("get comments for size[" + size + "] failed ..." , e);
				}
				return comments;
			}
		});
	}
	
	public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode){
		Map<String, String> comment = new HashMap<String, String>();
		try {
			comment.put("id", resultSet.getString("id"));
            String content = resultSet.getString("content");
            String escapeContent = JsoupUtil.getText(content);
			comment.put("content", content);
			comment.put("escapeContent", escapeContent);
            comment.put("shortContent", StringUtil.substring(escapeContent, 40) + (escapeContent.length() > 40 ? "..." : ""));
			comment.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("create_date")));
			String username = resultSet.getString("username");
			String resourceUsername = resultSet.getString("resource_username");
			if (!StringUtils.isEmpty(username)) {
				comment.put("commenter", username);
			} else if (!StringUtils.isEmpty(resourceUsername)) {
				comment.put("commenter", resourceUsername);
			} else {
				comment.put("commenter", resultSet.getString("city") + "网友");
			}
            Integer articleId = resultSet.getInt("article_id");
            if (viewMode == null) {
                viewMode = ViewMode.DYNAMIC;
            }
            Map<String, String> article = DaoFactory.getDao(ArticleDao.class).getArticle(articleId, viewMode);
			comment.put("articleUrl", article.get("url"));
			comment.put("articleSubject", article.get("subject"));
			comment.put("good_times", resultSet.getString("good_times"));
			comment.put("bad_times", resultSet.getString("bad_times"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return comment;
	}
	
}
