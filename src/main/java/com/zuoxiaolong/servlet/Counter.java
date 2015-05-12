package com.zuoxiaolong.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.ArticleIdVisitorIpDao;
import com.zuoxiaolong.freemarker.Generators;

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
 * @since 5/8/2015 4:12 PM
 */
public class Counter extends BaseServlet {

	private static final long serialVersionUID = -2655585691431759816L;
	
	private static final Logger logger = Logger.getLogger(Counter.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer articleId = Integer.valueOf(request.getParameter("articleId"));
        String column = request.getParameter("column");
        if (logger.isInfoEnabled()) {
            logger.info("counter param : articleId = " + articleId + "   , column = " + column);
        }
        PrintWriter printWriter = response.getWriter();
        if (!column.equals("access_times")) {
        	if (logger.isInfoEnabled()) {
        		logger.info("there is someone remarking...");
			}
			String ip = getVisitorIp(request);
			if (ArticleIdVisitorIpDao.exsits(articleId, ip)) {
				printWriter.write("exists");
				printWriter.flush();
				if (logger.isInfoEnabled()) {
					logger.info(ip + " has remarked...");
				}
				return ;
			} else {
				ArticleIdVisitorIpDao.save(articleId, ip);
			}
		}
        boolean result = ArticleDao.updateCount(articleId, column);
        if (!result) {
			logger.error("updateCount error!");
			return;
		}
		if (result && logger.isInfoEnabled()) {
			logger.info("updateCount success!");
		}
		printWriter.write("success");
		printWriter.flush();
        Generators.generate(articleId);
    }

}

