package com.zuoxiaolong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
 * @since 2015年5月29日 上午1:04:31
 */
public abstract class TagDao extends BaseDao {

	public static Integer save(final String tagName) {
		return execute(new TransactionalOperation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("insert into tags (tag_name) values (?)",Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, tagName);
					int result = statement.executeUpdate();
					if (result > 0) {
						ResultSet resultSet = statement.getGeneratedKeys();
						if (resultSet.next()) {
							return resultSet.getInt(1);
						}
					}
				} catch (SQLException e) {
					error("save tags failed ..." , e);
				}
				return null;
			}
		});
	} 
	
	public static Integer getId(final String tagName) {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select id from tags where tag_name=?");
					statement.setString(1, tagName);
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						return resultSet.getInt(1);
					}
				} catch (SQLException e) {
					error("query tags failed ..." , e);
				}
				return null;
			}
		});
	}
	
}
