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
import com.zuoxiaolong.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月29日 上午1:04:31
 */
public class CategoryDao extends BaseDao {

    public boolean delete(final int articleId) {
        return execute((TransactionalOperation<Boolean>) connection -> {
            try {
                PreparedStatement statement = connection.prepareStatement("delete from article_category where article_id=?");
                statement.setInt(1, articleId);
                int result = statement.executeUpdate();
                return result > 0;
            } catch (SQLException e) {
                error("delete article_category failed ...", e);
            }
            return false;
        });
    }

    public List<Map<String, String>> getAll() {
        return getAll("categories");
    }

    public List<Map<String, String>> getCategories(final int articleId) {
        return execute(new Operation<List<Map<String, String>>>() {
            @Override
            public List<Map<String, String>> doInConnection(Connection connection) {
                List<Map<String, String>> result = new ArrayList<Map<String, String>>();
                try {
                    PreparedStatement statement = connection.prepareStatement("select * from categories where id in (select category_id from article_category where article_id=?)");
                    statement.setInt(1, articleId);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Map<String, String> category = new HashMap<String, String>();
                        category.put("id", resultSet.getString("id"));
                        category.put("category_name", resultSet.getString("category_name"));
                        result.add(category);
                    }
                } catch (SQLException e) {
                    error("query article_category failed ...", e);
                }
                return result;
            }
        });
    }

    public Integer save(final String categoryName) {
        return execute(new TransactionalOperation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = connection.prepareStatement("insert into categories (category_name) values (?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, categoryName);
                    int result = statement.executeUpdate();
                    if (result > 0) {
                        ResultSet resultSet = statement.getGeneratedKeys();
                        if (resultSet.next()) {
                            return resultSet.getInt(1);
                        }
                    }
                } catch (SQLException e) {
                    error("save categories failed ...", e);
                }
                return null;
            }
        });
    }

    public Integer getId(final String categoryName) {
        return execute(new Operation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = connection.prepareStatement("select id from categories where category_name=?");
                    statement.setString(1, categoryName);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    error("query categories failed ...", e);
                }
                return null;
            }
        });
    }

    @Override
    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> category = new HashMap<String, String>();
        try {
            category.put("id", resultSet.getString("id"));
            String categoryName = resultSet.getString("category_name");
            category.put("category_name", categoryName);
            category.put("short_category_name", StringUtil.substring(categoryName, 4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }
}
