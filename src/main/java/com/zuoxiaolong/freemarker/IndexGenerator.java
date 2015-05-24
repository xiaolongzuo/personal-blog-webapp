package com.zuoxiaolong.freemarker;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.zuoxiaolong.config.Configuration;
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
 * @since 5/7/2015 3:06 PM
 */
public class IndexGenerator implements Generator {
	
	private static final int DEFAULT_INDEX_ARTICLE_NUMBER = 5;

    public void generate() {
        Writer writer = null;
        try {
            Map<String, Object> data = FreemarkerHelper.buildCommonDataMap();
            List<Map<String, String>> articleList = ArticleDao.getArticles("create_date");
            List<Map<String, String>> randomList = new ArrayList<Map<String,String>>();
            for (int i = 0; i < DEFAULT_INDEX_ARTICLE_NUMBER; i++) {
				randomList.add(articleList.remove(new Random().nextInt(articleList.size())));
			}
            data.put("articles", randomList);
            String htmlPath = Configuration.getContextPath("html/index.html");
            writer = new FileWriter(htmlPath);
            FreemarkerHelper.generate("index", writer, data);
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
