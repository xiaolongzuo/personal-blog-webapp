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
 * @since 2015年5月31日 下午2:59:13
 */
public abstract class ImageDao extends BaseDao {

	public static boolean save(final String path, final String resourceUrl) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("insert into images (path,resource_url) values (?,?)");
					statement.setString(1, path);
					statement.setString(2, resourceUrl);
					int result = statement.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					error("save image failed ..." , e);
				}
				return false;
			}
		});
	} 
	
	public static boolean exists(final String resourceUrl) {
		return getId(resourceUrl) != null;
	}
	
	public static Integer getId(final String resourceUrl) {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select id from images where resource_url=?");
					statement.setString(1, resourceUrl);
					ResultSet resultSet = statement.executeQuery();
					if(resultSet.next()) {
						return resultSet.getInt(1);
					}
				} catch (SQLException e) {
					error("query image failed ..." , e);
				}
				return null;
			}
		});
	}
	
}
