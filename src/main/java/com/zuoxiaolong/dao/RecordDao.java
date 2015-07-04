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

import com.zuoxiaolong.freemarker.QuestionHelper;
import com.zuoxiaolong.freemarker.RecordHelper;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import com.zuoxiaolong.util.ImageUtil;
import com.zuoxiaolong.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 15/6/21 00:25
 */
public class RecordDao extends BaseDao {

    public boolean updateCount(Integer id) {
        return updateCount(id, "records", "access_times");
    }

    public boolean updateGoodTimes(Integer id) {
        return updateCount(id, "records", "good_times");
    }

    public Map<String, String> getRecord(final Integer id, ViewMode viewMode) {
        return getById("records", id, viewMode);
    }

    public Integer save(String username, String title, String record, String content) {
        return execute(new TransactionalOperation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                String sql = "insert into records (username,title,record,content,create_date) values (?,?,?,?,?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, username);
                    statement.setString(2, title);
                    statement.setString(3, record);
                    statement.setString(4, content);
                    statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    int result = statement.executeUpdate();
                    if (result > 0) {
                        ResultSet keyResultSet = statement.getGeneratedKeys();
                        if (keyResultSet.next()) {
                            return keyResultSet.getInt(1);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    public Integer getTotal() {
        return getTotal("records");
    }

    public List<Map<String, String>> getAll(ViewMode viewMode) {
        return getAll("records", viewMode);
    }

    public List<Map<String, String>> getRecords(Map<String, Integer> pager, ViewMode viewMode) {
        return getPager(pager, "records", "good_times", viewMode);
    }

    public Integer saveOrUpdate(String id, String title, String username, String html, String content) {
        return execute(new TransactionalOperation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                String insertSql = "insert into records (title,username,create_date," +
                        "record,content) values (?,?,?,?,?)";
                String updateSql = "update records set title=?,username=?,create_date=?,record=?,content=? where id=?";
                try {
                    PreparedStatement statement = null;
                    if (StringUtils.isBlank(id)) {
                        statement = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
                        statement.setString(1, title);
                        statement.setString(2, username);
                        statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                        statement.setString(4, html);
                        statement.setString(5, content);
                    } else {
                        statement = connection.prepareStatement(updateSql);
                        statement.setString(1, title);
                        statement.setString(2, username);
                        statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                        statement.setString(4, html);
                        statement.setString(5, content);
                        statement.setInt(6, Integer.valueOf(id));
                    }
                    int result = statement.executeUpdate();
                    if (result > 0 && StringUtils.isBlank(id)) {
                        ResultSet keyResultSet = statement.getGeneratedKeys();
                        if (keyResultSet.next()) {
                            return keyResultSet.getInt(1);
                        }
                    }
                    if (result > 0) {
                        return Integer.valueOf(id);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> record = new HashMap<>();
        try {
            record.put("id", resultSet.getString("id"));
            record.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("create_date")));
            record.put("title", resultSet.getString("title"));
            record.put("record", resultSet.getString("record"));
            record.put("escapeHtml", StringUtil.escapeHtml(record.get("record")));
            String content = resultSet.getString("content");
            record.put("content", content);
            record.put("summary", StringUtil.substring(content, 100));
            record.put("username", resultSet.getString("username"));
            record.put("access_times", resultSet.getString("access_times"));
            record.put("good_times", resultSet.getString("good_times"));
            if (ViewMode.DYNAMIC == viewMode) {
                record.put("url", RecordHelper.generateDynamicPath(Integer.valueOf(record.get("id"))));
            } else {
                record.put("url", RecordHelper.generateStaticPath(Integer.valueOf(record.get("id"))));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return record;
    }

}
