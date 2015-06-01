package com.zuoxiaolong.freemarker;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CategoryDao;
import com.zuoxiaolong.dao.TagDao;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.search.LuceneHelper;
import com.zuoxiaolong.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * @author 左潇龙
 * @since 2015年5月31日 下午5:14:48
 */
public class ArticleListHelper {

	public static void putArticleListDataMap(Map<String, Object> data, ViewMode viewMode, String orderColumn , int current , int total) {
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		Map<String, Integer> pager = new HashMap<String, Integer>();
		pager.put("current", current);
		pager.put("total", total);
		pager.put("page", page);
		data.put("pageArticles", ArticleDao.getPageArticles(pager, orderColumn, viewMode));
		data.put("pager", pager);
		if (viewMode == ViewMode.STATIC) {
			data.put("firstArticleListUrl", ArticleListHelper.generateStaticPath(orderColumn, 1));
			data.put("preArticleListUrl", ArticleListHelper.generateStaticPath(orderColumn, current - 1));
			data.put("nextArticleListUrl", ArticleListHelper.generateStaticPath(orderColumn, current + 1));
			data.put("lastArticleListUrl", ArticleListHelper.generateStaticPath(orderColumn, page));
		} else {
			data.put("firstArticleListUrl", ArticleListHelper.generateDynamicPath(orderColumn, 1));
			data.put("preArticleListUrl", ArticleListHelper.generateDynamicPath(orderColumn, current - 1));
			data.put("nextArticleListUrl", ArticleListHelper.generateDynamicPath(orderColumn, current + 1));
			data.put("lastArticleListUrl", ArticleListHelper.generateDynamicPath(orderColumn, page));
		}
	}

	public static void putArticleListDataMapBySearchText(Map<String, Object> data, String searchText, int current) {
		List<Map<String, String>> articles = LuceneHelper.search(searchText);
		int total = articles.size();
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		Map<String, Integer> pager = new HashMap<>();
		pager.put("current", current);
		pager.put("total", total);
		pager.put("page", page);
		data.put("searchText", searchText);
		data.put("pageArticles", articles.subList((current - 1) * 10, current * 10 > articles.size() ? articles.size() : current * 10));
		data.put("pager", pager);
		data.put("firstArticleListUrl", ArticleListHelper.generateDynamicSearchTextPath(searchText, 1));
		data.put("preArticleListUrl", ArticleListHelper.generateDynamicSearchTextPath(searchText, current - 1));
		data.put("nextArticleListUrl", ArticleListHelper.generateDynamicSearchTextPath(searchText, current + 1));
		data.put("lastArticleListUrl", ArticleListHelper.generateDynamicSearchTextPath(searchText, page));
	}
	
	public static void putArticleListDataMapByTag(Map<String, Object> data, ViewMode viewMode, String tag, int current) {
		int tagId = TagDao.getId(tag);
		int total = ArticleDao.getArticlesByTag(tagId, viewMode).size();
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		Map<String, Integer> pager = new HashMap<String, Integer>();
		pager.put("current", current);
		pager.put("total", total);
		pager.put("page", page);
		data.put("pageArticles", ArticleDao.getPageArticlesByTag(pager, tagId, viewMode));
		data.put("pager", pager);
		data.put("firstArticleListUrl", ArticleListHelper.generateDynamicTagPath(tag, 1));
		data.put("preArticleListUrl", ArticleListHelper.generateDynamicTagPath(tag, current - 1));
		data.put("nextArticleListUrl", ArticleListHelper.generateDynamicTagPath(tag, current + 1));
		data.put("lastArticleListUrl", ArticleListHelper.generateDynamicTagPath(tag, page));
	}
	
	public static void putArticleListDataMapByCategory(Map<String, Object> data, ViewMode viewMode, String category, int current) {
		int categoryId = CategoryDao.getId(category);
		int total = ArticleDao.getArticlesByCategory(categoryId, viewMode).size();
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		Map<String, Integer> pager = new HashMap<String, Integer>();
		pager.put("current", current);
		pager.put("total", total);
		pager.put("page", page);
		data.put("pageArticles", ArticleDao.getPageArticlesByCategory(pager, categoryId, viewMode));
		data.put("pager", pager);
		data.put("firstArticleListUrl", ArticleListHelper.generateDynamicCategoryPath(category, 1));
		data.put("preArticleListUrl", ArticleListHelper.generateDynamicCategoryPath(category, current - 1));
		data.put("nextArticleListUrl", ArticleListHelper.generateDynamicCategoryPath(category, current + 1));
		data.put("lastArticleListUrl", ArticleListHelper.generateDynamicCategoryPath(category, page));
	}

	public static String generateStaticPath(String column, int current) {
		return "/html/article_list_" + column + "_" + current + ".html";
	}

	public static String generateDynamicPath(String staticPath) {
		String name = staticPath.substring(staticPath.lastIndexOf("/") + 1, staticPath.lastIndexOf("."));
		String[] names = name.split("_");
		int current = Integer.valueOf(names[names.length - 1]);
		String column = names[2];
		for (int i = 3; i < names.length - 1; i++) {
			column = column + "_" + names[i];
		}
		return generateDynamicPath(column, current);
	}
	
	public static String generateDynamicPath(String orderColumn, int current) {
		return "/blog/article_list.ftl?orderColumn=" + orderColumn + "&current=" + current;
	}
	
	public static String generateDynamicTagPath(String tag, int current) {
		return "/blog/article_list.ftl?tag=" + StringUtil.urlEncode(tag) + "&current=" + current;
	}
	
	public static String generateDynamicCategoryPath(String category, int current) {
		return "/blog/article_list.ftl?category=" + StringUtil.urlEncode(category) + "&current=" + current;
	}
	
	public static String generateDynamicSearchTextPath(String searchText, int current) {
		return "/blog/article_list.ftl?searchText=" + StringUtil.urlEncode(searchText) + "&current=" + current;
	}
	
}
