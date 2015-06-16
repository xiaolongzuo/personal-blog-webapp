package com.zuoxiaolong.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.dao.CommentIdVisitorIpDao;
import com.zuoxiaolong.generator.Generators;
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
 * @since 5/8/2015 4:12 PM
 */
public class CommentRemark extends AbstractServlet {

	private static final long serialVersionUID = -2655585691431759816L;
	
	private static final Logger logger = Logger.getLogger(CommentRemark.class);
	
    @Override
    protected void service() throws IOException {
    	HttpServletRequest request = getRequest();
    	Integer articleId = Integer.valueOf(request.getParameter("articleId"));
        Integer commentId = Integer.valueOf(request.getParameter("commentId"));
        String column = request.getParameter("column");
        if (logger.isInfoEnabled()) {
            logger.info("CommentRemark param : commentId = " + commentId + "   , column = " + column);
        }
		String ip = HttpUtil.getVisitorIp(request);
		String username = getUsername();
		if (CommentIdVisitorIpDao.exsits(commentId, ip, username)) {
			writeText("exists");
			if (logger.isInfoEnabled()) {
				logger.info(ip + " has remarked...");
			}
			return ;
		} else {
			CommentIdVisitorIpDao.save(commentId, ip, username);
		}
        boolean result = CommentDao.updateCount(commentId, column);
        if (!result) {
			logger.error("updateCount error!");
			return;
		}
		if (result && logger.isInfoEnabled()) {
			logger.info("updateCount success!");
		}
		Generators.generate(articleId);
		writeText("success");
    }

}

