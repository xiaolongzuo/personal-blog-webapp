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

import com.zuoxiaolong.api.HttpApiHelper;
import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author 左潇龙
 * @since 2015年5月9日 下午11:34:14
 */
public class MessageDao extends BaseDao {

    public Integer getTotal() {
        return getTotal("messages");
    }

    public Integer save(final String visitorIp, final Date createDate,
                        final String message, final String username, final Integer referenceMessageId) {
        return execute(new TransactionalOperation<Integer>() {
            @Override
            public Integer doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = null;
                    if (referenceMessageId == null) {
                        statement = connection.prepareStatement("insert into messages (visitor_ip,city,message,"
                                + "create_date,username) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    } else {
                        statement = connection.prepareStatement("insert into messages (visitor_ip,city,message,"
                                + "create_date,username,reference_message_id) values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    }
                    statement.setString(1, visitorIp);
                    statement.setString(2, Configuration.isProductEnv() ? HttpApiHelper.getCity(visitorIp) : "来自星星的");
                    statement.setString(3, message);
                    Date finalCommentDate = createDate;
                    if (createDate == null) {
                        finalCommentDate = new Date();
                    }
                    statement.setTimestamp(4, new Timestamp(finalCommentDate.getTime()));
                    statement.setString(5, username);
                    if (referenceMessageId != null) {
                        statement.setInt(6, referenceMessageId);
                    }
                    int result = statement.executeUpdate();
                    if (result > 0) {
                        ResultSet resultSet = statement.getGeneratedKeys();
                        if (resultSet.next()) {
                            return resultSet.getInt(1);
                        }
                    }
                } catch (SQLException e) {
                    error("save messages failed ...", e);
                }
                return null;
            }
        });
    }

    public List<Map<String, String>> getAll() {
        return getAll("messages", "create_date", ViewMode.DYNAMIC);
    }

    public List<Map<String, String>> getMessages(Map<String, Integer> pager) {
        return getPager(pager, "messages", "create_date");
    }

    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> message = new HashMap<>();
        try {
            message.put("id", resultSet.getString("id"));
            message.put("message", resultSet.getString("message"));
            message.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("create_date")));
            String username = resultSet.getString("username");
            if (!StringUtils.isEmpty(username)) {
                message.put("commenter", username);
            } else {
                message.put("commenter", resultSet.getString("city") + "网友");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

}
