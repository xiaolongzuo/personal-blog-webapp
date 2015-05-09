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
package com.zuoxiaolong.freemarker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author zuoxiaolong
 *
 */
public class ArticleListGenerator implements Generator {
	
	private static final Logger logger = Logger.getLogger(ArticleGenerator.class);

	@Override
	public void generate() {
		List<Map<String, String>> articles = ArticleDao.getArticles("create_date");
		int total = articles.size();
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		for (int i = 1; i < page + 1; i++) {
			Map<String, Integer> pager = new HashMap<String, Integer>();
			pager.put("current", i);
			pager.put("total", total);
			pager.put("page", page);
			freemarker.template.Configuration configuration = Configuration.getFreemarkerConfiguration();
	        Writer writer = null;
	        try {
	            Template template = configuration.getTemplate("article_list.ftl", "UTF-8");
	            Map<String, Object> data = new HashMap<String, Object>();
	            data.put("accessCharts", ArticleDao.getArticles("access_times"));
	            data.put("newCharts", articles);
	            data.put("recommendCharts", ArticleDao.getArticles("good_times"));
	            data.put("imageArticles", articles);
	            data.put("pageArticles", ArticleDao.getPageArticles(pager));
	            data.put("pager", pager);
	            String htmlPath = Configuration.getContextPath("article_list_" + i + ".html");
	            if (logger.isInfoEnabled()) {
	                logger.info("htmlPath : " + htmlPath);
	            }
	            writer = new FileWriter(htmlPath);
	            template.process(data, writer);
	            writer.flush();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        } catch (TemplateException e) {
	            throw new RuntimeException(e);
	        } finally {
	            try {
	                writer.close();
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	        }
		}
	}
	
}
