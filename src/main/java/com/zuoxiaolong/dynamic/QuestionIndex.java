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
import com.zuoxiaolong.dao.QuestionDao;
import com.zuoxiaolong.mvc.DataMap;
import com.zuoxiaolong.mvc.Namespace;
import com.zuoxiaolong.orm.DaoFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 15/6/21 00:56
 */
@Namespace("question")
public class QuestionIndex implements DataMap {

    @Override
    public void putCustomData(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        Integer current = 1;
        if (request.getParameter("current") != null) {
            current = Integer.valueOf(request.getParameter("current"));
        }
        Map<String, Integer> pager = new HashMap<>();
        int total = DaoFactory.getDao(QuestionDao.class).getTotal();
        int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
        pager.put("current", current);
        pager.put("total", total);
        pager.put("page", page);
        data.put("pager", pager);
        data.put("questions", DaoFactory.getDao(QuestionDao.class).getQuestions(pager));
        data.put("firstPageUrl", "/question/question_index.ftl?current=1");
        data.put("prePageUrl", "/question/question_index.ftl?current=" + (current - 1));
        data.put("nextPageUrl", "/question/question_index.ftl?current=" + (current + 1));
        data.put("lastPageUrl", "/question/question_index.ftl?current=" + page);
    }

}
