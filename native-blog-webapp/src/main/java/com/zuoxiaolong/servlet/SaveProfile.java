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
import javax.servlet.http.HttpServletRequest;

import com.zuoxiaolong.dao.UserDao;
import com.zuoxiaolong.orm.DaoFactory;

/**
 * @author 左潇龙
 * @since 2015年6月16日 上午12:05:05
 */
public class SaveProfile extends AbstractServlet {

	@Override
	protected void service() throws ServletException, IOException {
		if (!isLogin()) {
			throw new RuntimeException();
		}
		HttpServletRequest request = getRequest();
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		Integer languageId = Integer.valueOf(request.getParameter("languageId"));
		if (DaoFactory.getDao(UserDao.class).updateProfile(getUsername(), province, city, languageId)) {
			writeText("success");
		} else {
			writeText("更新资料失败");
		}
	}

}
