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
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author 左潇龙
 * @since 2015年5月9日 下午11:34:14
 */
public class AnswerDao extends BaseDao {

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
					PreparedStatement preparedStatement = connection.prepareStatement("update answers set " + column + " = " + column + " + " + finalCount + " where id = ?");
					preparedStatement.setInt(1, id);
					int number = preparedStatement.executeUpdate();
					return number > 0;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	public boolean updateContent(Integer id, String answer) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement preparedStatement = connection.prepareStatement("update answers set answer=? where id = ?");
					preparedStatement.setString(1, answer);
					preparedStatement.setInt(2, id);
					int number = preparedStatement.executeUpdate();
					return number > 0;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public Integer save(final Integer questionId, final String visitorIp,final Date answerDate,
		final String answer,final String username,final Integer referenceAnswerId) {
		return execute(new TransactionalOperation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = null;
					if (referenceAnswerId == null) {
						statement = connection.prepareStatement("insert into answers (visitor_ip,city,answer,question_id,"
								+ "answer_date,username) values (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					} else {
						statement = connection.prepareStatement("insert into answers (visitor_ip,city,answer,question_id,"
								+ "answer_date,username,reference_answer_id) values (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					}
					statement.setString(1, visitorIp);
					statement.setString(2, Configuration.isProductEnv() ? HttpApiHelper.getCity(visitorIp) : "来自星星的");
					statement.setString(3, answer);
					statement.setInt(4, questionId);
					Date finalCommentDate = answerDate;
					if (answerDate == null) {
						finalCommentDate = new Date();
					}
					statement.setTimestamp(5, new Timestamp(finalCommentDate.getTime()));
					statement.setString(6, username);
					if (referenceAnswerId != null) {
						statement.setInt(7, referenceAnswerId);
					}
					int result = statement.executeUpdate();
					if (result > 0) {
						ResultSet resultSet = statement.getGeneratedKeys();
						if (resultSet.next()) {
							return resultSet.getInt(1);
						}
					}
				} catch (SQLException e) {
					error("save answers failed ..." , e);
				}
				return null;
			}
		});
	}

	public List<Map<String, String>> getAnswers(final Integer questionId) {
		return execute(new Operation<List<Map<String, String>>>() {
			@Override
			public List<Map<String, String>> doInConnection(Connection connection) {
				List<Map<String, String>> comments = new ArrayList<Map<String,String>>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from answers where question_id=? order by good_times DESC , answer_date ASC ");
					statement.setInt(1, questionId);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						comments.add(transfer(resultSet));
					}
				} catch (SQLException e) {
					error("get answers for question[" + questionId + "] failed ..." , e);
				}
				return comments;
			}
		});
	}
	
	public List<Map<String, String>> getAnswers() {
		return getAll("answers", "answer_date");
	}
	
	public Map<String, String> transfer(ResultSet resultSet){
		Map<String, String> comment = new HashMap<String, String>();
		try {
			comment.put("id", resultSet.getString("id"));
			comment.put("answer", resultSet.getString("answer"));
			comment.put("answer_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("answer_date")));
			String username = resultSet.getString("username");
			if (!StringUtils.isEmpty(username)) {
				comment.put("answerer", username);
			} else {
				comment.put("answerer", resultSet.getString("city") + "网友");
			}
			comment.put("good_times", resultSet.getString("good_times"));
			comment.put("bad_times", resultSet.getString("bad_times"));
			comment.put("is_solution", resultSet.getString("is_solution"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return comment;
	}
	
}
