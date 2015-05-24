package com.zuoxiaolong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * @since 2015年5月19日 上午12:51:37
 */
public abstract class HeroDao extends BaseDao {

	public static Boolean exsits(final String fullName) {
		return execute(new Operation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select * from hero where full_name=?");
					statement.setString(1, fullName);
					ResultSet resultSet = statement.executeQuery();
					return resultSet.next();
				} catch (SQLException e) {
					error("query hero failed ..." , e);
				}
				return false;
			}
		});
	}

	public static List<String> getList(final String param) {
		return execute(new Operation<List<String>>() {
			@Override
			public List<String> doInConnection(Connection connection) {
				List<String> result = new ArrayList<String>();
				try {
					PreparedStatement statement = connection.prepareStatement("select * from hero where full_name like ? or aliases like ?");
					statement.setString(1, "%" + param + "%");
					statement.setString(2, "%" + param + "%");
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						result.add(resultSet.getString("full_name"));
					}
				} catch (SQLException e) {
					error("find hero error...", e);
				}
				return result;
			}
		});
	}
}
