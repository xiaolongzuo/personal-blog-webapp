package com.zuoxiaolong.freemarker;

import java.util.List;
import java.util.Map;

import com.zuoxiaolong.algorithm.Random;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.model.ViewMode;

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
 * @since 2015年5月31日 下午5:07:46
 */
public abstract class IndexHelper {

	private static final int DEFAULT_INDEX_ARTICLE_NUMBER = 8;

	public static void putArticleDataMap(Map<String, Object> data, ViewMode viewMode) {
		List<Map<String, String>> randomList = Random.random(ArticleDao.getArticles("create_date", viewMode), DEFAULT_INDEX_ARTICLE_NUMBER);
        data.put("articles", randomList);
	}
	
	public static String generateStaticPath() {
		return "/html/index.html";
	}
	
	public static String generateDynamicPath() {
		return "/blog/index.ftl";
	}
	
}
