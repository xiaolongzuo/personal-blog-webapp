package com.zuoxiaolong.dynamic;

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
import com.zuoxiaolong.freemarker.ArticleListHelper;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.mvc.DataMap;
import com.zuoxiaolong.mvc.Namespace;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
        String type = request.getParameter("type");
		String currentString = request.getParameter("current");
		int current = 1;
		if (StringUtils.isNotBlank(currentString)) {
			current = Integer.valueOf(currentString);
		}
		if (StringUtils.isNotBlank(orderColumn)) {
			int total = DaoFactory.getDao(ArticleDao.class).getArticles(orderColumn, Status.published, VIEW_MODE).size();
			ArticleListHelper.putDataMap(data, VIEW_MODE, orderColumn, current, total);

		} else if (StringUtils.isNotBlank(searchText)) {
			searchText = StringUtil.urlDecode(searchText);
			ArticleListHelper.putDataMapBySearchText(data, searchText, current);

		} else if (StringUtils.isNotBlank(category)) {
			category = StringUtil.urlDecode(category);
			ArticleListHelper.putDataMapByCategory(data, VIEW_MODE, category, current);

		} else if (StringUtils.isNotBlank(tag)) {
			tag = StringUtil.urlDecode(tag);
			ArticleListHelper.putDataMapByTag(data, VIEW_MODE, tag, current);

		} else if (StringUtils.isNotBlank(type)) {
            ArticleListHelper.putDataMapByType(data, VIEW_MODE, Integer.valueOf(type), current);

        } else {
			throw new IllegalArgumentException();
		}
	}
	
}
