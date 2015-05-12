package com.zuoxiaolong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	
	public static boolean save(final Integer articleId, final String visitorIp, final String city, final String content) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("insert into comments (visitor_ip,city,content,article_id,create_date) values (?,?,?,?,?)");
					statement.setString(1, visitorIp);
					statement.setString(2, city);
					statement.setString(3, content);
					statement.setInt(4, articleId);
					statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
					int result = statement.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					error("save comments failed ..." , e);
				}
				return false;
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
			comment.put("city", resultSet.getString("city"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return comment;
	}
	
}
