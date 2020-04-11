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

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.RecordDao;
import com.zuoxiaolong.mvc.RequestMapping;
import com.zuoxiaolong.orm.DaoFactory;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author 左潇龙
 * @since 2015年6月18日 下午6:50:20
 */
@RequestMapping("/admin/adminArticleDelete.do")
public class AdminRecordDelete extends AbstractServlet {

	@Override
	protected void service() throws ServletException, IOException {
		Integer id = Integer.valueOf(getRequest().getParameter("id"));
		if (DaoFactory.getDao(RecordDao.class).delete(id)) {
			getResponse().sendRedirect(Configuration.getSiteUrl("/admin/record_manager.ftl"));
		}
	}

}
