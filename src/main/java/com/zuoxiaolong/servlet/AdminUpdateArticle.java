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

import java.io.IOException;

import javax.servlet.ServletException;

import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.JsoupUtil;
import org.jsoup.Jsoup;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.mvc.RequestMapping;
import com.zuoxiaolong.reptile.Cnblogs;
import com.zuoxiaolong.util.StringUtil;

/**
 * @author 左潇龙
 * @since 2015年6月18日 上午2:28:03
 */
@RequestMapping("/admin/updateArticle.do")
public class AdminUpdateArticle extends AbstractServlet {

	@Override
	protected void service() throws ServletException, IOException {
		String id = getRequest().getParameter("id");
		String subject = getRequest().getParameter("subject");
		String html = getRequest().getParameter("content");
		String status = getRequest().getParameter("status");
		String icon = getRequest().getParameter("icon");
		html = handleQuote(html);
		
		StringBuffer stringBuffer = new StringBuffer();
		JsoupUtil.appendText(Jsoup.parse(html), stringBuffer);
		DaoFactory.getDao(ArticleDao.class).saveOrUpdate(id, subject, Integer.valueOf(status), "左潇龙", html, stringBuffer.toString(), icon);
		writeText("success");
	}

}
