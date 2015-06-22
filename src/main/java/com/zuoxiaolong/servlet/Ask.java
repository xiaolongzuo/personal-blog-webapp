package com.zuoxiaolong.servlet;

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

import com.zuoxiaolong.dao.QuestionDao;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.JsoupUtil;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 6/19/2015 6:18 PM
 */
public class Ask extends AbstractServlet {

    @Override
    protected void service() throws ServletException, IOException {
        String title = getRequest().getParameter("title");
        String description = getRequest().getParameter("description");
        if (!isLogin()) {
            throw new RuntimeException();
        }
        String content = "";
        if (description.trim().length() > 0) {
            content = JsoupUtil.getText(description);
        }
        Integer id = DaoFactory.getDao(QuestionDao.class).save(getUsername(), title, handleQuote(description), content);
        if (id != null) {
            writeText("success");
        }
    }

}
