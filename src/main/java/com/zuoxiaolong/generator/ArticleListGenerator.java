package com.zuoxiaolong.generator;

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

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.freemarker.ArticleListHelper;
import com.zuoxiaolong.freemarker.FreemarkerHelper;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.orm.DaoFactory;

/**
 * @author 左潇龙
 * @since 2015年5月10日 下午3:17:37
 */
public class ArticleListGenerator implements Generator {

    @Override
    public int order() {
        return 1;
    }

    @Override
	public void generate() {
		generateArticleList("create_date");
		generateArticleList("access_times");
		generateArticleList("good_times");
        generateNovelList();
	}
	
	private void generateArticleList(String orderColumn) {
		List<Map<String, String>> articles = DaoFactory.getDao(ArticleDao.class).getArticles(orderColumn, Status.published, VIEW_MODE);
		int total = articles.size();
		int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
		for (int i = 1; i < page + 1; i++) {
	        Writer writer = null;
	        try {
	            Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(VIEW_MODE);
	            ArticleListHelper.putDataMap(data, VIEW_MODE, orderColumn, i, total);
	            String htmlPath = Configuration.getContextPath(ArticleListHelper.generateStaticPath(orderColumn, i));
	            writer = new FileWriter(htmlPath);
				FreemarkerHelper.generate("article_list", writer, data);
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

    private void generateNovelList() {
        List<Map<String, String>> articles = DaoFactory.getDao(ArticleDao.class).getArticlesByType(1, Status.published, VIEW_MODE);
        int total = articles.size();
        int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
        for (int i = 1; i < page + 1; i++) {
            Writer writer = null;
            try {
                Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(VIEW_MODE);
                ArticleListHelper.putDataMapForNovel(data, VIEW_MODE, i, total);
                String htmlPath = Configuration.getContextPath(ArticleListHelper.generateStaticPath("novel", i));
                writer = new FileWriter(htmlPath);
                FreemarkerHelper.generate("article_list", writer, data);
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
