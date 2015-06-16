package com.zuoxiaolong.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.generator.Generators;
import com.zuoxiaolong.util.DirtyWordsUtil;
import com.zuoxiaolong.util.HttpUtil;
import com.zuoxiaolong.util.StringUtil;

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
public class Comment extends AbstractServlet {

	private static final long serialVersionUID = 1250411762987530784L;
	
	@Override
	protected void service() throws ServletException, IOException {
		HttpServletRequest request = getRequest();
		String content = request.getParameter("content");
		String referenceCommentIdString = request.getParameter("referenceCommentId");
		String referenceCommenter = request.getParameter("referenceCommenter");
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
		Integer referenceCommentId = null;
		if (!StringUtils.isBlank(referenceCommentIdString) && !StringUtils.isBlank(referenceCommenter)) {
			referenceCommentId = Integer.valueOf(referenceCommentIdString);
			content = "<a href=\"javascript:void(0);\" class=\"content_reply_a\" reference_comment_id=\""+referenceCommentId+"\">@"+referenceCommenter+"</a><br/>" + content;
		}
		String quotePrefix = "<fieldset class=\"comment_quote\"><legend>引用</legend>";
		String quoteSuffix = "</fieldset>";
		content = StringUtil.replace(content, "[quote]", "[/quote]", quotePrefix, quoteSuffix);
		Integer id = CommentDao.save(articleId, visitorIp, new Date(), content, username, nickName, null, referenceCommentId);
		if (id == null) {
			logger.error("save comment error!");
			writeText("保存评论失败，请稍后再试");
			return;
		}
		if (ArticleDao.updateCount(articleId, "comment_times") && logger.isInfoEnabled()) {
			logger.info("save comment and updateCount success!");
		} else {
			writeText("保存评论失败，请稍后再试");
			return;
		}
		Generators.generate(articleId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("id", id);
		result.put("content", content);
		writeJsonObject(result);
	}

}
