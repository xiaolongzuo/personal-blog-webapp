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
 * @since 2015年5月29日 上午1:04:31
 */
public class ArticleTagDao extends BaseDao {
	
	public boolean save(final int articleId, final int tagId) {
		return execute(new TransactionalOperation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("insert into article_tag (tag_id,article_id) values (?,?)");
					statement.setInt(1, tagId);
					statement.setInt(2, articleId);
					int result = statement.executeUpdate();
					return result > 0;
				} catch (SQLException e) {
					error("save article_tag failed ..." , e);
				}
				return false;
			}
		});
	} 
	
	public boolean exsits(final int articleId, final int tagId) {
		return execute(new Operation<Boolean>() {
			@Override
			public Boolean doInConnection(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement("select * from article_tag where tag_id=? and article_id=?");
					statement.setInt(1, tagId);
					statement.setInt(2, articleId);
					ResultSet resultSet = statement.executeQuery();
					return resultSet.next();
				} catch (SQLException e) {
					error("query article_tag failed ..." , e);
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
