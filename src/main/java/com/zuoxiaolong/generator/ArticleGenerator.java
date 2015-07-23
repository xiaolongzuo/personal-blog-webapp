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

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.freemarker.ArticleHelper;
import com.zuoxiaolong.freemarker.FreemarkerHelper;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.orm.DaoFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 5/7/2015 6:06 PM
 */
public class ArticleGenerator implements Generator {

    @Override
    public int order() {
        return 1;
    }

    @Override
	public void generate() {
		List<Map<String, String>> articles = DaoFactory.getDao(ArticleDao.class).getArticles("create_date", Status.published, VIEW_MODE);
		for (int i = 0; i < articles.size(); i++) {
			generateArticle(Integer.valueOf(articles.get(i).get("id")));
		}
	}

	void generateArticle(Integer id) {
		Writer writer = null;
		try {
			Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(VIEW_MODE);
			ArticleHelper.putDataMap(data, VIEW_MODE, id);
			String htmlPath = Configuration.getContextPath(ArticleHelper.generateStaticPath(id));
			writer = new FileWriter(htmlPath);
			FreemarkerHelper.generate("article", writer, data);
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
