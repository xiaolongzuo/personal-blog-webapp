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

import com.zuoxiaolong.dao.*;
import com.zuoxiaolong.mvc.RequestMapping;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.JsoupUtil;
import org.jsoup.Jsoup;

import javax.servlet.ServletException;
import java.io.IOException;

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
        String type = getRequest().getParameter("type");
		String icon = getRequest().getParameter("icon");
        String updateCreateTime = getRequest().getParameter("updateCreateTime");
        String[] categories = getRequest().getParameter("categories").split(",");
        String[] tags = getRequest().getParameter("tags").split(",");
		html = handleQuote(html);
		
		StringBuffer stringBuffer = new StringBuffer();
		JsoupUtil.appendText(Jsoup.parse(html), stringBuffer);
        Integer articleId = DaoFactory.getDao(ArticleDao.class).saveOrUpdate(id, subject, Integer.valueOf(status), Integer.valueOf(type), Integer.valueOf(updateCreateTime), "左潇龙", html, stringBuffer.toString(), icon);
		if (tags == null || categories == null) {
            return;
        }
        DaoFactory.getDao(TagDao.class).delete(articleId);
        for (int i = 0; i < tags.length; i++) {
            String tag = tags[i].trim();
            if (tag.length() == 0) {
                continue;
            }
            Integer tagId = DaoFactory.getDao(TagDao.class).getId(tag);
            if (tagId == null) {
                tagId = DaoFactory.getDao(TagDao.class).save(tag);
            }
            DaoFactory.getDao(ArticleTagDao.class).save(articleId, tagId);
        }
        DaoFactory.getDao(CategoryDao.class).delete(articleId);
        for (int i = 0; i < categories.length; i++) {
            String categoryId = categories[i].trim();
            if (categoryId.length() == 0) {
                continue;
            }
            DaoFactory.getDao(ArticleCategoryDao.class).save(articleId, Integer.valueOf(categoryId));
        }
        writeText("success");
	}

}
