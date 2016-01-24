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

import com.zuoxiaolong.model.ViewMode;
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
 * @since 2015年5月31日 下午2:59:13
 */
public class ImageDao extends BaseDao {

	public boolean save(final String path, final String resourceUrl) {
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
	
	public boolean exists(final String resourceUrl) {
		return getId(resourceUrl) != null;
	}

	public boolean update(final String path, final String resourceUrl) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("update images set path=? where resource_url=?");
					statement.setString(1, path);
					statement.setString(2, resourceUrl);
					int result = statement.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					error("update image failed ..." , e);
				}
				return null;
			}
		});
	}
	
	public String getPath(final String resourceUrl) {
		return execute(new Operation<String>() {
			@Override
			public String doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select path from images where resource_url=?");
					statement.setString(1, resourceUrl);
					ResultSet resultSet = statement.executeQuery();
					if(resultSet.next()) {
						return resultSet.getString(1);
					}
				} catch (SQLException e) {
					error("query image failed ..." , e);
				}
				return null;
			}
		});
	}
	
	public Integer getId(final String resourceUrl) {
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

	@Override
	public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
		throw new UnsupportedOperationException();
	}
}
