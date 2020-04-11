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

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月26日 上午1:14:47
 */
public class HtmlPageDao extends BaseDao {

    public void flush() {
        String contextPath = Configuration.getSiteUrl();
        File[] htmlFiles = new File(Configuration.getContextPath("html")).listFiles();
        List<String> htmlPageList = new ArrayList<String>();
        htmlPageList.add(contextPath);
        for (int i = 0; i < htmlFiles.length; i++) {
            htmlPageList.add(contextPath + "/html/" + htmlFiles[i].getName());
        }
        for (String url : htmlPageList) {
            save(url);
        }
        info("flush html_page success...");
    }

    public boolean save(final String url) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                try {
                    PreparedStatement findStatement = connection.prepareStatement("select id from html_page where url=?");
                    findStatement.setString(1, url);
                    if (findStatement.executeQuery().next()) {
                        return true;
                    }
                    PreparedStatement saveStatement = connection.prepareStatement("insert into html_page (url,is_push,create_date) values (?,?,?)");
                    saveStatement.setString(1, url);
                    saveStatement.setInt(2, 0);
                    saveStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    int result = saveStatement.executeUpdate();
                    return result > 0;
                } catch (SQLException e) {
                    error("save html_page failed ...", e);
                }
                return false;
            }
        });
    }

    public boolean updateIsPush(final String url) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                try {
                    PreparedStatement saveStatement = connection.prepareStatement("update html_page set is_push=1,push_date=? where url=?");
                    saveStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    saveStatement.setString(2, url);
                    int result = saveStatement.executeUpdate();
                    return result > 0;
                } catch (SQLException e) {
                    error("update html_page failed ...", e);
                }
                return false;
            }
        });
    }

    public String findPushUrl() {
        return execute(new Operation<String>() {
            @Override
            public String doInConnection(Connection connection) {
                try {
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select url from html_page where is_push=0 limit 0,1");
                    if (resultSet.next()) {
                        return resultSet.getString("url");
                    }
                } catch (SQLException e) {
                    error("query html_page failed ...", e);
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
