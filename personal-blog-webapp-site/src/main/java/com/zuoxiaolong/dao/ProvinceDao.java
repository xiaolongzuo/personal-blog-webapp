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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月29日 上午1:04:31
 */
public class ProvinceDao extends BaseDao {

    public Integer getId(final String name) {
        return execute(new Operation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = connection.prepareStatement("select id from dictionary_province where name=?");
                    statement.setString(1, name);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    error("query dictionary_province failed ...", e);
                }
                return null;
            }
        });
    }

    public List<Map<String, String>> getProvinces() {
        return getAll("dictionary_province");
    }

    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> tag = new HashMap<String, String>();
        try {
            tag.put("id", resultSet.getString("id"));
            tag.put("name", resultSet.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tag;
    }

}
