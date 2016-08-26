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

import com.zuoxiaolong.freemarker.ArticleHelper;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.model.Type;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.BaseDao;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.orm.Operation;
import com.zuoxiaolong.orm.TransactionalOperation;
import com.zuoxiaolong.util.DateUtil;
import com.zuoxiaolong.util.ImageUtil;
import com.zuoxiaolong.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author 左潇龙
 * @since 5/7/2015 3:40 PM
 */
public class ArticleDao extends BaseDao {
	
	public Boolean delete(Integer id) {
    	return execute((TransactionalOperation<Boolean>) connection -> {
            String updateSql = "update articles set status=0 where id=?";
            try {
                PreparedStatement statement = connection.prepareStatement(updateSql);
                statement.setInt(1, id);
                int result = statement.executeUpdate();
                if (result > 0) {
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        });
    }
	
    public List<Map<String, String>> getPageArticlesByTag(final Map<String, Integer> pager,final int tagId, ViewMode viewMode) {
		return execute((Operation<List<Map<String, String>>>) connection -> {
            List<Map<String, String>> result = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_tag where tag_id=? ) and type=0 order by create_date desc limit ?,10");
                statement.setInt(1 , tagId);
                statement.setInt(2 , (pager.get("current") - 1) * 10);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                error("query article_category failed ..." , e);
            }
            return result;
        });
	}
    
    public List<Map<String, String>> getArticlesByTag(final int tagId, ViewMode viewMode) {
		return execute((Operation<List<Map<String, String>>>) connection -> {
            List<Map<String, String>> result = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_tag where tag_id=?  and type=0 )");
                statement.setInt(1 , tagId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                error("query article_category failed ..." , e);
            }
            return result;
        });
	}
    
    public List<Map<String, String>> getPageArticlesByCategory(final Map<String, Integer> pager,final int categoryId, ViewMode viewMode) {
		return execute((Operation<List<Map<String, String>>>) connection -> {
            List<Map<String, String>> result = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_category where category_id=? )  and type=0 order by create_date desc limit ?,10");
                statement.setInt(1, categoryId);
                statement.setInt(2 , (pager.get("current") - 1) * 10);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                error("query article_category failed ..." , e);
            }
            return result;
        });
	}
    
    public List<Map<String, String>> getArticlesByCategory(final int categoryId, ViewMode viewMode) {
		return execute((Operation<List<Map<String, String>>>) connection -> {
            List<Map<String, String>> result = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement("select * from articles where id in (select article_id from article_category where category_id=?  and type=0 )");
                statement.setInt(1, categoryId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                error("query article_category failed ..." , e);
            }
            return result;
        });
	}

    public List<Map<String, String>> getPageArticlesByType(final Map<String, Integer> pager,final Type type, final Status status, ViewMode viewMode) {
        return execute((Operation<List<Map<String, String>>>) connection -> {
            List<Map<String, String>> result = new ArrayList<>();
            String sql = "select * from articles where type=? and status=? order by create_date desc limit ?,10";
            if (status == null) {
                sql = "select * from articles where type=? order by create_date desc limit ?,10";
            }
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, type.getIntValue());
                if (status == null) {
                    statement.setInt(2 , (pager.get("current") - 1) * 10);
                } else {
                    statement.setInt(2, status.getIntValue());
                    statement.setInt(3, (pager.get("current") - 1) * 10);
                }
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                error("query articles failed ..." , e);
            }
            return result;
        });
    }

    public List<Map<String, String>> getArticlesByType(final Type type, final Status status, ViewMode viewMode) {
        return execute((Operation<List<Map<String, String>>>) connection -> {
            List<Map<String, String>> result = new ArrayList<>();
            String sql = "select * from articles where type=? and status=? order by create_date desc";
            if (status == null) {
                sql = "select * from articles where type=? order by create_date desc";
            }
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, type.getIntValue());
                if (status != null) {
                    statement.setInt(2, status.getIntValue());
                }
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                error("query articles failed ..." , e);
            }
            return result;
        });
    }
    
    public Integer saveOrUpdate(String id, String subject, Status status, Type type, Integer updateCreateTime
            , String username, String html, String content, String icon) {
    	return execute((TransactionalOperation<Integer>) connection -> {
            String insertSql = "insert into articles (subject,username,icon,create_date," +
                                    "html,content,status,type) values (?,?,?,?,?,?,?,?)";
            String updateSql = "update articles set subject=?,username=?,icon=?,html=?,content=?,status=?,type=? where id=?";
            if (updateCreateTime == 1) {
                updateSql = "update articles set subject=?,username=?,icon=?,html=?,content=?,status=?,type=?,create_date=? where id=?";
            }
            try {
                PreparedStatement statement = null;
                if (StringUtils.isBlank(id)) {
                    statement = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, subject);
                    statement.setString(2, username);
                    statement.setString(3, icon == null ? ImageUtil.randomArticleImage(subject, type) : icon);
                    statement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    statement.setString(5, html);
                    statement.setString(6, content);
                    statement.setInt(7, status.getIntValue());
                    statement.setInt(8, type.getIntValue());
                } else {
                    statement = connection.prepareStatement(updateSql);
                    statement.setString(1, subject);
                    statement.setString(2, username);
                    statement.setString(3, icon == null ? ImageUtil.randomArticleImage(subject, type) : icon);
                    statement.setString(4, html);
                    statement.setString(5, content);
                    statement.setInt(6, status.getIntValue());
                    statement.setInt(7, type.getIntValue());
                    if (updateCreateTime == 1) {
                        statement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                        statement.setInt(9, Integer.valueOf(id));
                    } else {
                        statement.setInt(8, Integer.valueOf(id));
                    }
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
        });
    }
    
    public Integer saveOrUpdate(String resourceId, String subject, String createDate, Status status
            , String username, Integer accessTimes, Integer goodTimes, String html, String content) {
    	return execute((TransactionalOperation<Integer>) connection -> {
            String selectSql = "select id,status from articles where resource_id=?";
            String insertSql = "insert into articles (resource_id,username,icon,create_date," +
                "access_times,good_times,subject,html,content,status) values (?,?,?,?,?,?,?,?,?,?)";
            String updateSql = "update articles set subject=?,html=?,content=?,icon=?,status=?,create_date=? where resource_id=? ";
            try {
                PreparedStatement statement = connection.prepareStatement(selectSql);
                statement.setString(1, resourceId);
                ResultSet resultSet = statement.executeQuery();
                Boolean exists = false;
                Status currentStatus = Status.draft;
                Integer id = null;
                if (resultSet.next()) {
                    exists = true;
                    currentStatus = Status.valueOf(resultSet.getInt("status"));
                    id = resultSet.getInt("id");
                }
                PreparedStatement saveOrUpdate = null;
                if (!exists) {
                    saveOrUpdate = connection.prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
                    saveOrUpdate.setString(1, resourceId);
                    saveOrUpdate.setString(2, username);
                    saveOrUpdate.setString(3, ImageUtil.randomArticleImage(subject));
                    saveOrUpdate.setString(4, createDate);
                    saveOrUpdate.setInt(5, accessTimes);
                    saveOrUpdate.setInt(6, goodTimes);
                    saveOrUpdate.setString(7, subject);
                    saveOrUpdate.setString(8, html);
                    saveOrUpdate.setString(9, content);
                    saveOrUpdate.setInt(10, status.getIntValue());
                } else {
                    saveOrUpdate = connection.prepareStatement(updateSql);
                    saveOrUpdate.setString(1, subject);
                    saveOrUpdate.setString(2, html);
                    saveOrUpdate.setString(3, content);
                    saveOrUpdate.setString(4, ImageUtil.randomArticleImage(subject));
                    saveOrUpdate.setInt(5, currentStatus == Status.published ? currentStatus.getIntValue() : status.getIntValue());
                    saveOrUpdate.setString(6, createDate);
                    saveOrUpdate.setString(7, resourceId);
                }
                int result = saveOrUpdate.executeUpdate();
                if (!exists && result > 0) {
                    ResultSet keyResultSet = saveOrUpdate.getGeneratedKeys();
                    if (keyResultSet.next()) {
                        id = keyResultSet.getInt(1);
                    }
                }
                return id;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    public List<Map<String, String>> getPageArticles(final Map<String, Integer> pager,final Status status, final String orderColumn, final ViewMode viewMode ) {
        return execute((Operation<List<Map<String, String>>>) connection -> {
            String sql = "select * from articles where status = ? order by " + orderColumn + " desc limit ?,10";
            if (status == null ) {
                sql = "select * from articles order by " + orderColumn + " desc limit ?,10";
            }
            List<Map<String, String>> result = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                if (status == null ) {
                    statement.setInt(1 , (pager.get("current") - 1) * 10);
                } else {
                    statement.setInt(1 , status.getIntValue());
                    statement.setInt(2 , (pager.get("current") - 1) * 10);
                }
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        });
    }

    public List<Map<String, String>> getArticles(final String orderColumn, final ViewMode viewMode ) {
        return getArticles(orderColumn, null, null, viewMode);
    }

    public List<Map<String, String>> getArticles(final String orderColumn, final Type type, final ViewMode viewMode ) {
        return getArticles(orderColumn, null, type, viewMode);
    }

    public List<Map<String, String>> getArticles(final String orderColumn, final Status status, final ViewMode viewMode ) {
        return getArticles(orderColumn, status, null, viewMode);
    }

    public List<Map<String, String>> getArticles(final String orderColumn,final Status status, final Type type, final ViewMode viewMode ) {
        return execute((Operation<List<Map<String, String>>>) connection -> {
            String sql = "select * from articles order by " + orderColumn + " desc";
            if (status != null && type == null) {
                sql = "select * from articles where status = ? order by " + orderColumn + " desc";
            }
            if (status == null && type != null) {
                sql = "select * from articles where type = ? order by " + orderColumn + " desc";
            }
            if (status != null && type != null) {
                sql = "select * from articles where status = ? and type = ? order by " + orderColumn + " desc";
            }
            List<Map<String, String>> result = new ArrayList<>();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                if (status != null && type == null) {
                    statement.setInt(1 , status.getIntValue());
                }
                if (status == null && type != null) {
                    statement.setInt(1 , type.getIntValue());
                }
                if (status != null && type != null) {
                    statement.setInt(1 , status.getIntValue());
                    statement.setInt(2 , type.getIntValue());
                }
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result.add(transfer(resultSet, viewMode));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        });
    }

    public Map<String, String> getPreArticle(final int id, final Date createDate, final ViewMode viewMode) {
        return execute((Operation<Map<String, String>>) connection -> {
            String sql = "SELECT * FROM articles WHERE create_date<? AND status=1 AND " +
                    "TYPE=(SELECT TYPE FROM articles WHERE id=?) ORDER BY create_date DESC LIMIT 0,1";
            Map<String, String> result = null;
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setTimestamp(1, new Timestamp(createDate.getTime()));
                statement.setInt(2 , id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    result = transfer(resultSet, viewMode);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        });
    }

    public Map<String, String> getNextArticle(final int id, final Date createDate, final ViewMode viewMode) {
        return execute((Operation<Map<String, String>>) connection -> {
            String sql = "SELECT * FROM articles WHERE create_date>? AND status=1 AND " +
                    "TYPE=(SELECT TYPE FROM articles WHERE id=?) ORDER BY create_date ASC LIMIT 0,1";
            Map<String, String> result = null;
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setTimestamp(1, new Timestamp(createDate.getTime()));
                statement.setInt(2 , id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    result = transfer(resultSet, viewMode);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        });
    }

    public Map<String, String> getArticle(final int id, final ViewMode viewMode ) {
        return execute((Operation<Map<String, String>>) connection -> {
            String sql = "select * from articles where id=?";
            Map<String, String> result = null;
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1 , id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    result = transfer(resultSet, viewMode);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return result;
        });
    }
    
    public boolean updateCommentCount(final int id) {
		return updateTimesCount("articles", "comment_times", "comments", "article_id", id);
    }

    public boolean updateCount(final int id, final String column) {
		return updateCount(id, "articles", column);
    }

    public Map<String, String> transfer(ResultSet resultSet, ViewMode viewMode) {
        Map<String, String> article = new HashMap<String, String>();
        try {
            String id = resultSet.getString("id");
            article.put("id", id);
            if (viewMode == ViewMode.DYNAMIC) {
                article.put("url", ArticleHelper.generateDynamicPath(Integer.valueOf(id)));
            } else {
                article.put("url", ArticleHelper.generateStaticPath(Integer.valueOf(id)));
            }
            List<Map<String,String>> tags = DaoFactory.getDao(TagDao.class).getTags(Integer.valueOf(id));
            StringBuffer stringBuffer = new StringBuffer("");
            if (tags != null && tags.size() > 0) {
                for (int i = 0; i < tags.size(); i++) {
                    Map<String, String> tag = tags.get(i);
                    if (i > 0) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(tag.get("tag_name"));
                }
            }
            article.put("tags", stringBuffer.toString());
            article.put("icon", resultSet.getString("icon"));
            article.put("subject", resultSet.getString("subject"));
            article.put("username", resultSet.getString("username"));
            Timestamp createDate = resultSet.getTimestamp("create_date");
            article.put("create_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createDate));
            article.put("us_create_date", DateUtil.rfc822(createDate));
            article.put("status", resultSet.getString("status"));
            article.put("type", resultSet.getString("type"));
            article.put("access_times", resultSet.getString("access_times"));
            article.put("comment_times", resultSet.getString("comment_times"));

            article.put("good_times", resultSet.getString("good_times"));
            article.put("touch_times", resultSet.getString("touch_times"));
            article.put("funny_times", resultSet.getString("funny_times"));
            article.put("happy_times", resultSet.getString("happy_times"));
            article.put("anger_times", resultSet.getString("anger_times"));
            article.put("bored_times", resultSet.getString("bored_times"));
            article.put("water_times", resultSet.getString("water_times"));
            article.put("surprise_times", resultSet.getString("surprise_times"));
            String html = resultSet.getString("html");
            article.put("html", html);
            article.put("escapeHtml", StringUtil.escapeHtml(html));
            String content = resultSet.getString("content");
            article.put("content", content);
            article.put("summary", StringUtil.substring(content, 100));
            String subject = resultSet.getString("subject");
            article.put("short_subject", StringUtil.substring(subject, 10) + (subject.length() > 10 ? "..." : ""));
            article.put("common_subject", StringUtil.substring(subject, 15) + (subject.length() > 15 ? "..." : ""));
            putAllTimesHeight(article);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return article;
    }

    private void putAllTimesHeight(Map<String, String> article) {
        int max = 10;
        for (String key : article.keySet()) {
            if (key.endsWith("_times") && !key.startsWith("access") && !key.startsWith("comment")) {
                Integer times = Integer.valueOf(article.get(key));
                if (times > max) {
                    max = times;
                }
            }
        }
        Map<String, String> tempArticle = new HashMap<String, String>(article);
        for (String key : tempArticle.keySet()) {
            if (key.endsWith("_times") && !key.startsWith("access") && !key.startsWith("comment")) {
                article.put(key + "_height", String.valueOf(Integer.valueOf(article.get(key)) * 50 / max));
            }
        }
    }

}
