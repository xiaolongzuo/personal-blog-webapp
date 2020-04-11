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

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.orm.DaoFactory;
import org.apache.commons.lang.StringUtils;

import com.zuoxiaolong.dao.UserDao;

/**
 * @author 左潇龙
 * @since 2015年6月16日 下午10:58:20
 */
public class UpdatePassword extends AbstractServlet {

	@Override
	protected void service() throws ServletException, IOException {
		HttpServletRequest request = getRequest();
		String username = getUsername();
		String originPassword = request.getParameter("originPassword");
		String newPassword = request.getParameter("newPassword");
		if (StringUtils.isBlank(newPassword)) {
			writeText("密码不能为空");
			return;
		}
		if (newPassword.length() < 6) {
			writeText("密码长度不能小于6");
			return;
		}
		if (newPassword.length() > 30) {
			writeText("密码长度不能超过30");
			return;
		}
		if (DaoFactory.getDao(UserDao.class).login(username, originPassword) == null) {
			writeText("原密码不正确");
			return;
		} 
		if (DaoFactory.getDao(UserDao.class).updatePassword(username, newPassword)) {
			writeText("success");
		} else {
			writeText("更新密码失败");
		}
	}

}
