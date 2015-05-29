package com.zuoxiaolong.dao;

import com.zuoxiaolong.api.HttpApiHelper;
import org.apache.commons.lang.StringUtils;

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
 * @since 2015年5月9日 下午11:34:14
 */
public abstract class CommentDao extends BaseDao {

	public static boolean updateCount(final int id, final String column) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement preparedStatement = connection.prepareStatement("update comments set " + column + " = " + column + " + 1 where id = ?");
					preparedStatement.setInt(1, id);
					int number = preparedStatement.executeUpdate();
					return number > 0;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	public static boolean exists(String resourceId) {
		return execute(new Operation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				return null;
			}
		});
	}
	
	public static Integer save(final Integer articleId, final String visitorIp,
		final String content,final String username,final String nickName,final String resourceId) {
		return execute(new TransactionalOperation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("insert into comments (visitor_ip,city,content,article_id,"
						+ "create_date,username,nick_name,resource_id) values (?,?,?,?,?,?,?,?)");
					statement.setString(1, visitorIp);
					statement.setString(2, HttpApiHelper.getCity(visitorIp));
					statement.setString(3, content);
					statement.setInt(4, articleId);
					statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
					statement.setString(6, username);
					statement.setString(7, nickName);
					statement.setString(8, resourceId);
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

	public static List<Map<String, String>> getComments(final Integer articleId) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> comments = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from comments where article_id=?");
					statement.setInt(1, articleId);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						comments.add(transfer(resultSet));
					}
				} catch (SQLException e) {
					error("get comments for article[" + articleId + "] failed ..." , e);
				}
				return comments;
			}
		});
	}
	
	public static Map<String, String> transfer(ResultSet resultSet){
		Map<String, String> comment = new HashMap<String, String>();
		try {
			comment.put("content", resultSet.getString("content"));
			comment.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("create_date")));
			String nickName = resultSet.getString("nick_name");
			if (!StringUtils.isEmpty(nickName)) {
				comment.put("commenter", nickName);
			} else {
				comment.put("commenter", resultSet.getString("city") + "网友");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return comment;
	}
	
}
