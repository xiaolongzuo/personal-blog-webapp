package com.zuoxiaolong.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.freemarker.Generators;
import com.zuoxiaolong.util.HttpUtil;

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
	protected void service() throws ServletException, IOException {
		HttpServletRequest request = getRequest();
		String content = request.getParameter("content");
		Integer articleId = Integer.valueOf(request.getParameter("articleId"));
		if (logger.isInfoEnabled()) {
			logger.info("comment param : articleId = " + articleId + "   , content = " + content);
		}
		String visitorIp = HttpUtil.getVisitorIp(request);
		Map<String, String> user = getUser();
		boolean result = CommentDao.save(articleId, visitorIp, content, (user == null ? null : user.get("username")), (user == null ? null : user.get("nickName")));
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
