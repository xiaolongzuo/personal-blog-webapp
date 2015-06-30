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

import com.zuoxiaolong.dao.AnswerDao;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.generator.Generators;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.DirtyWordsUtil;
import com.zuoxiaolong.util.HttpUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月9日 下午11:22:01
 */
public class Answer extends AbstractServlet {

	@Override
	protected void service() throws ServletException, IOException {
		HttpServletRequest request = getRequest();
		String content = request.getParameter("content");
		String referenceCommentIdString = request.getParameter("referenceCommentId");
		String referenceCommenter = request.getParameter("referenceCommenter");
		if (StringUtils.isBlank(content)) {
			writeText("答案不能为空");
			return;
		}
		if (DirtyWordsUtil.isDirtyWords(content)) {
			writeText("答案不合法");
			return;
		}
		Integer questionId = Integer.valueOf(request.getParameter("questionId"));
		if (logger.isInfoEnabled()) {
			logger.info("comment param : questionId = " + questionId + "   , content = " + content);
		}
		String visitorIp = HttpUtil.getVisitorIp(request);
		Map<String, String> user = getUser();
		String username = user == null ? null : user.get("username");
		Integer referenceCommentId = null;
		if (!StringUtils.isBlank(referenceCommentIdString) && !StringUtils.isBlank(referenceCommenter)) {
			referenceCommentId = Integer.valueOf(referenceCommentIdString);
			content = "<a href=\"javascript:void(0);\" class=\"content_reply_a\" reference_comment_id=\""+referenceCommentId+"\">@"+referenceCommenter+"</a><br/>" + content;
		}
		content = handleQuote(content);
		Integer id = DaoFactory.getDao(AnswerDao.class).save(questionId, visitorIp, new Date(), content, username,referenceCommentId);
		if (id == null) {
			logger.error("save answer error!");
			writeText("保存评论失败，请稍后再试");
			return;
		}
		if (DaoFactory.getDao(ArticleDao.class).updateCount(questionId, "comment_times") && logger.isInfoEnabled()) {
			logger.info("save answer and updateCount success!");
		} else {
			writeText("保存评论失败，请稍后再试");
			return;
		}
		Generators.generateArticle(questionId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("id", id);
		result.put("content", content);
		writeJsonObject(result);
	}

}
