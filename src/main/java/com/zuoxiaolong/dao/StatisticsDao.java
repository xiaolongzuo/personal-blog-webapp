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

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年6月6日 下午6:04:32
 */
public class StatisticsDao extends BaseDao {

	public int getSiteTotalVisitorIpNumber() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select count(distinct(visitor_ip)) from access_log");
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	public int getSiteTodayVisitorIpNumber() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select count(distinct(visitor_ip)) from access_log where access_date > ?");
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date today = dateFormat.parse(dateFormat.format(new Date()));
					statement.setTimestamp(1, new Timestamp(today.getTime()));
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	public int getSiteTotalAccessTimes() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select count(*) from access_log");
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public int getSiteTodayAccessTimes() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select count(*) from access_log where access_date > ?");
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date today = dateFormat.parse(dateFormat.format(new Date()));
					statement.setTimestamp(1, new Timestamp(today.getTime()));
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public int getArticleTotalAccessTimes() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select sum(access_times) from articles");
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public int getCommentTotalNumber() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select count(*) from comments");
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public int getCommentTodayNumber() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select count(*) from comments where create_date > ?");
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date today = dateFormat.parse(dateFormat.format(new Date()));
					statement.setTimestamp(1, new Timestamp(today.getTime()));
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public int getUserTotalNumber() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("select count(*) from users");
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	public int getUserTodayNumber() {
		return execute(new Operation<Integer>() {
			@Override
			public Integer doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select count(*) from users where create_date > ?");
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date today = dateFormat.parse(dateFormat.format(new Date()));
					statement.setTimestamp(1, new Timestamp(today.getTime()));
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						return resultSet.getInt(1);
					} else {
						throw new RuntimeException();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Override
	public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
		throw new UnsupportedOperationException();
	}

}
