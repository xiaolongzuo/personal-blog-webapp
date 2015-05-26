package com.zuoxiaolong.dynamic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuoxiaolong.dao.ArticleDao;

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
@Namespace("blog")
public class ArticleList implements DataMap {

	@Override
	public void putCustomData(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Integer> pager = new HashMap<String, Integer>();
		int total = ArticleDao.getArticles("create_date").size();
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		pager.put("current", Integer.valueOf(request.getParameter("current")));
		pager.put("total", total);
		pager.put("page", page);
		data.put("pageArticles", ArticleDao.getPageArticles(pager));
        data.put("pager", pager);
	}

}
