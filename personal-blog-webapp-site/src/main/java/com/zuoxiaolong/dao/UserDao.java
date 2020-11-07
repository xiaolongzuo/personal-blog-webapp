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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.zuoxiaolong.client.HttpClient;
import com.zuoxiaolong.client.HttpUriEnums;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import com.zuoxiaolong.util.EnrypyUtil;

/**
 * @author 左潇龙
 * @since 2015年5月27日 上午12:04:43
 */
public class UserDao extends BaseDao {

    public Map<String, String> login(String username, String password) {
        return execute(new Operation<Map<String, String>>() {
            public Map<String, String> doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = connection.prepareStatement("select * from users where username=? and password=?");
                    statement.setString(1, username);
                    statement.setString(2, EnrypyUtil.md5(password));
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return transfer(resultSet, null);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    public Map<String, String> getUser(String username) {
        return execute(new Operation<Map<String, String>>() {
            @Override
            public Map<String, String> doInConnection(Connection connection) {
                try {
                    PreparedStatement statement = connection.prepareStatement("select * from users where username=?");
                    statement.setString(1, username);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return transfer(resultSet, null);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    public boolean updatePassword(String username, String password) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                String sql = "update users set password=? where username=?";
                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, EnrypyUtil.md5(password));
                    statement.setString(2, username);
                    int result = statement.executeUpdate();
                    return result > 0;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public boolean uploadImage(String username, String imagePath) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                String sql = "update users set image_path=? where username=?";
                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, imagePath);
                    statement.setString(2, username);
                    int result = statement.executeUpdate();
                    return result > 0;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public boolean updateProfile(String username, String province, String city, Integer languageId) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                String sql = "update users set province=?,city=?,language_id=? where username=?";
                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, province);
                    statement.setString(2, city);
                    statement.setInt(3, languageId);
                    statement.setString(4, username);
                    int result = statement.executeUpdate();
                    return result > 0;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public boolean saveCommonLogin(String username, String password) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                String insertSql = "insert into users (username,password,nick_name,create_date) values (?,?,?,?)";
                try {
                    PreparedStatement statement = connection.prepareStatement(insertSql);
                    statement.setString(1, username);
                    statement.setString(2, EnrypyUtil.md5(password));
                    statement.setString(3, username);
                    statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    int result = statement.executeUpdate();
                    return result > 0;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public boolean saveOrUpdateQqLogin(String qqOpenId, String nickName, String imagePath) {
        return execute(new TransactionalOperation<Boolean>() {
            @Override
            public Boolean doInConnection(Connection connection) {
                String selectSql = "select id from users where qq_open_id=?";
                String insertSql = "insert into users (username,nick_name,qq_open_id,image_path,create_date) values (?,?,?,?,?)";
                String updateSql = "update users set username=?,nick_name=?,image_path=? where qq_open_id=?";
                try {
                    PreparedStatement statement = connection.prepareStatement(selectSql);
                    statement.setString(1, qqOpenId);
                    ResultSet resultSet = statement.executeQuery();
                    boolean exists = false;
                    if (resultSet.next()) {
                        exists = true;
                    }
                    PreparedStatement saveOrUpdate = null;
                    if (!exists) {
                        saveOrUpdate = connection.prepareStatement(insertSql);
                        saveOrUpdate.setString(1, qqOpenId);
                        saveOrUpdate.setString(2, nickName);
                        saveOrUpdate.setString(3, qqOpenId);
                        saveOrUpdate.setString(4, imagePath);
                        saveOrUpdate.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    } else {
                        saveOrUpdate = connection.prepareStatement(updateSql);
                        saveOrUpdate.setString(1, qqOpenId);
                        saveOrUpdate.setString(2, nickName);
                        saveOrUpdate.setString(3, imagePath);
                        saveOrUpdate.setString(4, qqOpenId);
                    }
                    int result = saveOrUpdate.executeUpdate();
                    return result > 0;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> user = new HashMap<String, String>();
        try {
            user.put("username", resultSet.getString("username"));
            user.put("nickName", resultSet.getString("nick_name"));
            user.put("qqOpenId", resultSet.getString("qq_open_id"));
            user.put("imagePath", resultSet.getString("image_path"));
            user.put("province", resultSet.getString("province"));
            user.put("city", resultSet.getString("city"));
            Integer languageId = resultSet.getInt("language_id");
            user.put("languageId", String.valueOf(languageId));
            user.put("language", HttpClient.get(String.class, HttpUriEnums.DICTIONARY_GET_NAME, new String[]{"languageId"}, languageId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

}
