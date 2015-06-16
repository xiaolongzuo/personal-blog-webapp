package com.zuoxiaolong.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.zuoxiaolong.dao.UserDao;
import com.zuoxiaolong.util.DirtyWordsUtil;

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
public class Login extends AbstractServlet {

	private static final String pattern = "[a-zA-Z0-9_\u4e00-\u9fa5]+";

	private static final Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fa5]{1,1}");
	
	@Override
	protected void service() throws ServletException, IOException {
		Map<String, Object> result = new HashMap<>();
		HttpServletRequest request = getRequest();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(username)) {
			writeText("用户名不能为空");
			return;
		}
		int chineseNumber = 0;
		Matcher matcher = chinesePattern.matcher(username);
		while (matcher.find()) {
			chineseNumber++;
		}
		int length = chineseNumber * 6 + (username.length() - chineseNumber);
		if (length < 6) {
			writeText("用户名长度不能小于6（汉字长度计为6）");
			return;
		}
		if (length > 30) {
			writeText("用户名长度不能超过30（汉字长度计为6）");
			return;
		}
		if (!Pattern.matches(pattern, username)) {
			writeText("用户名只能是字母，数字，汉字，下划线");
			return;
		}
		if (DirtyWordsUtil.isDirtyWords(username)) {
			writeText("用户名不合法");
			return;
		}
		if (StringUtils.isBlank(password)) {
			writeText("密码不能为空");
			return;
		}
		if (password.length() < 6) {
			writeText("密码长度不能小于6");
			return;
		}
		if (password.length() > 30) {
			writeText("密码长度不能超过30");
			return;
		}
		Map<String, String> user = UserDao.login(username, password);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			result.put("success", true);
			result.put("url", getDynamicUrl());
			writeJsonObject(result);
			return;
		}
		Map<String, String> userInDB = UserDao.getUser(username);
		if (userInDB != null) {
			writeText("用户名已存在或密码错误");
			return;
		}
		if (UserDao.saveCommonLogin(username, password)) {
			Map<String, String> newUser = UserDao.getUser(username);
			request.getSession().setAttribute("user", newUser);
			result.put("success", true);
			result.put("url", getDynamicUrl());
			writeJsonObject(result);
			return;
		}
		writeText("服务器内部错误，请联系站长");
	}

}
