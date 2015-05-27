package com.zuoxiaolong.generator;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.util.FreemarkerUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
 * @since 2015年5月10日 下午3:17:37
 */
public class ArticleListGenerator implements Generator {
	
	@Override
	public void generate() {
		List<Map<String, String>> articles = ArticleDao.getArticles("create_date", VIEW_MODE);
		int total = articles.size();
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		for (int i = 1; i < page + 1; i++) {
			Map<String, Integer> pager = new HashMap<String, Integer>();
			pager.put("current", i);
			pager.put("total", total);
			pager.put("page", page);
	        Writer writer = null;
	        try {
	            Map<String, Object> data = FreemarkerUtil.buildCommonDataMap(VIEW_MODE);
	            data.put("pageArticles", ArticleDao.getPageArticles(pager, VIEW_MODE));
	            data.put("pager", pager);
				data.put("firstArticleListUrl","/html/article_list_1.html");
				data.put("preArticleListUrl","/html/article_list_" + (i - 1) + ".html");
				data.put("nextArticleListUrl","/html/article_list_" + (i + 1) + ".html");
				data.put("lastArticleListUrl","/html/article_list_" + page + ".html");
	            String htmlPath = Configuration.getContextPath("html/article_list_" + i + ".html");
	            writer = new FileWriter(htmlPath);
				FreemarkerUtil.generate("article_list", writer, data);
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        } finally {
	            try {
	            	if (writer != null) {
						writer.close();
					}
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	        }
		}
	}
	
}
