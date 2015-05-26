package com.zuoxiaolong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
 * @since 2015年5月27日 上午12:04:43
 */
public abstract class UserDao extends BaseDao {
	
	public static Map<String, String> getUser(String username) {
		return execute(new Operation<Map<String, String>>() {
			@Override
			public Map<String, String> doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select * from users where username=?");
					statement.setString(1, username);
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						Map<String, String> result = new HashMap<String, String>();
						result.put("username", username);
						result.put("nickName", resultSet.getString("nick_name"));
						result.put("qqOpenId", resultSet.getString("qq_open_id"));
						result.put("qqNickName", resultSet.getString("qq_nick_name"));
						result.put("qqAvatarUrl30", resultSet.getString("qq_avatar_url_30"));
						return result;
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				return null;
			}
		});
	}

	public static boolean saveOrUpdate(String username, String password, String nickName, String qqOpenId, String qqNickName, String qqAvatarUrl30) {
    	return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				String finalUsername = username;
				if (StringUtils.isEmpty(username)) {
					finalUsername = qqOpenId;
				}
				String finalNickName = nickName;
				if (StringUtils.isEmpty(nickName)) {
					finalNickName = qqNickName;
				}
				String selectSql = "select id from users where username=?";
				String insertSql = "insert into users (username,password,nick_name,qq_open_id,qq_nick_name,qq_avatar_url_30) values (?,?,?,?,?,?)";
				String updateSql = "update users set qq_open_id=?,nick_name=?,qq_nick_name=?,qq_avatar_url_30=? where username=?";
				try {
					PreparedStatement statement = connection.prepareStatement(selectSql);
					statement.setString(1, finalUsername);
					ResultSet resultSet = statement.executeQuery();
					boolean exsits = false;
					if (resultSet.next()) {
						exsits = true;
					}
					PreparedStatement saveOrUpdate = null;
					if (!exsits) {
						saveOrUpdate = connection.prepareStatement(insertSql);
						saveOrUpdate.setString(1, finalUsername);
						saveOrUpdate.setString(2, password);
						saveOrUpdate.setString(3, finalNickName);
						saveOrUpdate.setString(4, qqOpenId);
						saveOrUpdate.setString(5, qqNickName);
						saveOrUpdate.setString(6, qqAvatarUrl30);
					} else {
						saveOrUpdate = connection.prepareStatement(updateSql);
						saveOrUpdate.setString(1, qqOpenId);
						saveOrUpdate.setString(2, finalNickName);
						saveOrUpdate.setString(3, qqNickName);
						saveOrUpdate.setString(4, qqAvatarUrl30);
						saveOrUpdate.setString(5, finalUsername);
					}
					int result = saveOrUpdate.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
    }
	
}
