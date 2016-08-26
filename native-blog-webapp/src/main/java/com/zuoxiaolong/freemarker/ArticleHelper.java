package com.zuoxiaolong.freemarker;

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

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CategoryDao;
import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.dao.TagDao;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.DaoFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月31日 下午5:07:46
 */
public abstract class ArticleHelper {

	public static void putDataMap(Map<String, Object> data, ViewMode viewMode,int articleId) {
		DaoFactory.getDao(ArticleDao.class).updateCommentCount(articleId);
		Map<String, String> article = DaoFactory.getDao(ArticleDao.class).getArticle(articleId, viewMode);
		data.put("article", article);
		data.put("comments", DaoFactory.getDao(CommentDao.class).getComments(articleId));
		data.put("tags", DaoFactory.getDao(TagDao.class).getTags(articleId));
		data.put("categories", DaoFactory.getDao(CategoryDao.class).getCategories(articleId));
		Date createDate = null;
		try {
			createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(article.get("create_date"));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Map<String, String> preArticle = DaoFactory.getDao(ArticleDao.class).getPreArticle(articleId, createDate, viewMode);
		Map<String, String> nextArticle = DaoFactory.getDao(ArticleDao.class).getNextArticle(articleId, createDate, viewMode);
		if (preArticle != null) {
			data.put("preArticle", preArticle);
		}
		if (nextArticle != null) {
			data.put("nextArticle", nextArticle);
		}
	}
	
	public static String generateStaticPath(int articleId) {
		return "/html/article_" + articleId + ".html";
	}
	
	public static String generateDynamicPath(String staticPath) {
		String name = staticPath.substring(staticPath.lastIndexOf("/") + 1 , staticPath.lastIndexOf("."));
		return generateDynamicPath(Integer.valueOf(name.split("_")[1]));
	}
	
	public static String generateDynamicPath(int id) {
		return "/blog/article.ftl?id=" + id;
	}
	
}
