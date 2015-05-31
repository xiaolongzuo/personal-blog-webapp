package com.zuoxiaolong.dynamic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.freemarker.ArticleListHelper;

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
 * @since 2015年5月27日 上午2:13:35
 */
@Namespace
public class ArticleList implements DataMap {

	@Override
	public void putCustomData(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
		String tag = request.getParameter("tag");
		String category = request.getParameter("category");
		String searchText = request.getParameter("searchText");
		String orderColumn = request.getParameter("orderColumn");
		String currentString = request.getParameter("current");
		int current = 1;
		if (StringUtils.isNotBlank(currentString)) {
			current = Integer.valueOf(currentString);
		}
		if (StringUtils.isNotBlank(orderColumn)) {
			int total = ArticleDao.getArticles(orderColumn, VIEW_MODE).size();
			ArticleListHelper.putArticleListDataMap(data, VIEW_MODE, orderColumn, current, total);
		} else if (StringUtils.isNotBlank(searchText)) {
			ArticleListHelper.putArticleListDataMapBySearchText(data, VIEW_MODE, searchText, current);
		} else if (StringUtils.isNotBlank(category)) {
			ArticleListHelper.putArticleListDataMapByCategory(data, VIEW_MODE, category, current);
		} else if (StringUtils.isNotBlank(tag)) {
			ArticleListHelper.putArticleListDataMapByTag(data, VIEW_MODE, tag, current);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
