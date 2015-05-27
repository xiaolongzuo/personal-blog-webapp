package com.zuoxiaolong.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.zuoxiaolong.dao.UserDao;

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
public class Login extends BaseServlet {

	private static final long serialVersionUID = -6892022662210485620L;
	
	private static final String pattern = "[a-zA-Z0-9_\u4e00-\u9fa5]{6,30}";
	
	@Override
	protected void service() throws ServletException, IOException {
		HttpServletRequest request = getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(username)) {
			writeText("用户名不能为空！");
			return;
		}
		if (username.length() < 6) {
			writeText("用户名长度不能小于6！");
			return;
		}
		if (username.length() > 30) {
			writeText("用户名长度不能超过30！");
			return;
		}
		if (!Pattern.matches(pattern, username)) {
			writeText("用户名只能是字母，数字，汉字，下划线！");
			return;
		}
		if (StringUtils.isEmpty(password)) {
			writeText("密码不能为空！");
			return;
		}
		if (password.length() < 6) {
			writeText("密码长度不能小于6！");
			return;
		}
		if (password.length() > 30) {
			writeText("密码长度不能超过30！");
			return;
		}
		Map<String, String> user = UserDao.login(username, password);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			writeText("success");
			return;
		}
		if (user == null && UserDao.saveOrUpdate(username, password, username, null, username, null)) {
			user = new HashMap<>();
			user.put("username", username);
			user.put("nickName", username);
			request.getSession().setAttribute("user", user);
			writeText("success");
			return;
		} 
		writeText("error");
	}

}
