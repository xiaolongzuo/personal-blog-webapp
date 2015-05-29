package com.zuoxiaolong.servlet;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.generator.Generators;
import com.zuoxiaolong.util.DirtyWordsUtil;
import com.zuoxiaolong.util.HttpUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

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
		if (StringUtils.isBlank(content)) {
			writeText("评论不能为空");
			return;
		}
		if (DirtyWordsUtil.isDirtyWords(content)) {
			writeText("评论不合法");
			return;
		}
		Integer articleId = Integer.valueOf(request.getParameter("articleId"));
		if (logger.isInfoEnabled()) {
			logger.info("comment param : articleId = " + articleId + "   , content = " + content);
		}
		String visitorIp = HttpUtil.getVisitorIp(request);
		Map<String, String> user = getUser();
		String username = user == null ? null : user.get("username");
		String nickName = user == null ? null : user.get("nickName");
		Integer result = CommentDao.save(articleId, visitorIp, content, username, nickName, null);
		if (result == null) {
			logger.error("save comment error!");
			writeText("保存评论失败，请稍后再试");
			return;
		}
		boolean finalResult = (result != null) && ArticleDao.updateCount(articleId, "comment_times");
		if (finalResult && logger.isInfoEnabled()) {
			logger.info("save comment and updateCount success!");
		}
		Generators.generate(articleId);
		writeText("success");
	}

}
