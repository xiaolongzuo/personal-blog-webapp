package com.zuoxiaolong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * @since 2015年5月12日 下午5:47:40
 */
public abstract class ArticleIdVisitorIpDao extends BaseDao {

	public static boolean save(final int articleId, final String visitorIp) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("insert into article_id_visitor_ip (visitor_ip,article_id) values (?,?)");
					statement.setString(1, visitorIp);
					statement.setInt(2, articleId);
					int result = statement.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					error("save remarkVisitorIp failed ..." , e);
				}
				return false;
			}
		});
	} 
	
	public static boolean exsits(final int articleId, final String visitorIp) {
		return execute(new Operation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select * from article_id_visitor_ip where visitor_ip=? and article_id=?");
					statement.setString(1, visitorIp);
					statement.setInt(2, articleId);
					ResultSet resultSet = statement.executeQuery();
					return resultSet.next();
				} catch (SQLException e) {
					error("query remarkVisitorIp failed ..." , e);
				}
				return false;
			}
		});
	}
	
}
