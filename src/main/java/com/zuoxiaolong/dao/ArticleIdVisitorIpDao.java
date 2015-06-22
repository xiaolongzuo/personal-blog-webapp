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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月12日 下午5:47:40
 */
public class ArticleIdVisitorIpDao extends BaseDao {

	public boolean save(final int articleId, final String visitorIp, final String username) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("insert into article_id_visitor_ip (visitor_ip,article_id,username) values (?,?,?)");
					statement.setString(1, visitorIp);
					statement.setInt(2, articleId);
					statement.setString(3, username);
					int result = statement.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					error("save remarkVisitorIp failed ..." , e);
				}
				return false;
			}
		});
	} 
	
	public boolean exsits(final int articleId, final String visitorIp, final String username) {
		return execute(new Operation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select * from article_id_visitor_ip where visitor_ip=? and article_id=?");
					statement.setString(1, visitorIp);
					statement.setInt(2, articleId);
					ResultSet resultSet = statement.executeQuery();
					boolean result = resultSet.next();
					statement = connection.prepareStatement("select * from article_id_visitor_ip where username=? and article_id=?");
					statement.setString(1, username);
					statement.setInt(2, articleId);
					resultSet = statement.executeQuery();
					result = result || resultSet.next();
					return result;
				} catch (SQLException e) {
					error("query remarkVisitorIp failed ..." , e);
				}
				return false;
			}
		});
	}

	@Override
	public Map<String, String> transfer(ResultSet resultSet) {
		throw new UnsupportedOperationException();
	}
}
