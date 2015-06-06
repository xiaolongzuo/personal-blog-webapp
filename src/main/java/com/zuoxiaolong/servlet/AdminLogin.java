package com.zuoxiaolong.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.util.EnrypyUtil;

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
 * @since 2015年5月27日 下午7:55:28
 */
public class AdminLogin extends BaseServlet {

	private static final long serialVersionUID = -6892022662210485620L;
	
	@Override
	protected void service() throws ServletException, IOException {
		HttpServletRequest request = getRequest();
		String password = request.getParameter("password");
		String correctPassword = Configuration.isProductEnv() ? Configuration.get("admin.password.product") : Configuration.get("admin.password");
		String contextPath = Configuration.isProductEnv() ? Configuration.get("context.path.product") : Configuration.get("context.path");
		try {
			if (password != null && EnrypyUtil.md5(password).equals(correctPassword)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("admin", "admin");
				getResponse().sendRedirect(contextPath + "/admin/admin_index.ftl");
			} else {
				getResponse().sendRedirect(contextPath + "/admin/login.ftl");
			}
		} catch (Exception e) {
			getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
}
