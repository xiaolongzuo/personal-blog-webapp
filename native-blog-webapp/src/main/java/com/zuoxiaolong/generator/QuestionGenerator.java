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
import com.zuoxiaolong.dao.QuestionDao;
import com.zuoxiaolong.freemarker.ArticleHelper;
import com.zuoxiaolong.freemarker.FreemarkerHelper;
import com.zuoxiaolong.freemarker.QuestionHelper;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.orm.DaoFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 15/6/29 21:34
 */
public class QuestionGenerator implements Generator {

    @Override
    public int order() {
        return 2;
    }

    @Override
    public void generate() {
        List<Map<String, String>> questions = DaoFactory.getDao(QuestionDao.class).getAll(VIEW_MODE);
        for (int i = 0; i < questions.size(); i++) {
            generateQuestion(Integer.valueOf(questions.get(i).get("id")));
        }
    }

    void generateQuestion(Integer id) {
        Writer writer = null;
        try {
            Map<String, Object> data = FreemarkerHelper.buildCommonDataMap("question", VIEW_MODE);
            QuestionHelper.putDataMap(null, data, VIEW_MODE, id);
            String htmlPath = Configuration.getContextPath(QuestionHelper.generateStaticPath(id));
            writer = new FileWriter(htmlPath);
            FreemarkerHelper.generate("question", "question", writer, data);
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
