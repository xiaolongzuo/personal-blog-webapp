package com.zuoxiaolong.servlet;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.freemarker.Generators;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
 * @since 2015年5月9日 下午11:22:01
 */
public class Comment extends BaseServlet {

	private static final long serialVersionUID = 1250411762987530784L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		Integer articleId = Integer.valueOf(request.getParameter("articleId"));
		if (logger.isInfoEnabled()) {
			logger.info("comment param : articleId = " + articleId + "   , content = " + content);
		}
		String visitorIp = getVisitorIp(request);
		boolean result = CommentDao.save(articleId, visitorIp, content);
		if (!result) {
			logger.error("save comment error!");
			return;
		}
		result = result && ArticleDao.updateCount(articleId, "comment_times");
		if (result && logger.isInfoEnabled()) {
			logger.info("save comment and updateCount success!");
		}
		Generators.generate(articleId);
	}

}
