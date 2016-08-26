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
import com.zuoxiaolong.dao.QuestionDao;
import com.zuoxiaolong.freemarker.FreemarkerHelper;
import com.zuoxiaolong.freemarker.QuestionListHelper;
import com.zuoxiaolong.orm.DaoFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月10日 下午3:17:37
 */
public class QuestionListGenerator implements Generator {

    @Override
    public int order() {
        return 2;
    }

    @Override
	public void generate() {
        int total = DaoFactory.getDao(QuestionDao.class).getTotal();
        int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
        for (int i = 1; i < page + 1; i++) {
            Writer writer = null;
            try {
                Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(VIEW_MODE);
                QuestionListHelper.putDataMap(null, i, data, VIEW_MODE);
                String htmlPath = Configuration.getContextPath(QuestionListHelper.generateStaticPath(i));
                writer = new FileWriter(htmlPath);
                FreemarkerHelper.generate("question", "question_list", writer, data);
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
