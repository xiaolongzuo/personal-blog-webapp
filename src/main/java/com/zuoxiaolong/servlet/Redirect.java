package com.zuoxiaolong.servlet;

import java.io.IOException;

import javax.servlet.ServletException;

import com.qq.connect.QQConnectException;
import com.qq.connect.oauth.Oauth;

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
 * @since 2015年5月26日 下午9:20:28
 */
public class Redirect extends AbstractServlet {

	private static final long serialVersionUID = 3204459453635451541L;

	@Override
	protected void service() throws ServletException, IOException {
		try {
			getResponse().sendRedirect(new Oauth().getAuthorizeURL(getRequest()));
		} catch (QQConnectException e) {
			logger.error("getAuthorizeURL failed!",e);
			writeText("跳转qq登录失败！");
		}
	}

}
