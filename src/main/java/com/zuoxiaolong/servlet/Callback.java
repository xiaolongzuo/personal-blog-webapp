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

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.zuoxiaolong.dao.UserDao;
import com.zuoxiaolong.orm.DaoFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月26日 下午9:23:04
 */
public class Callback extends HttpServlet {

	private static final Logger logger = Logger.getLogger(Callback.class);

	private static final long serialVersionUID = 7842406487110985418L;

	@Override
	protected void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		try {
			String accessToken = new Oauth().getAccessTokenByRequest(request).getAccessToken();
			String openId = new OpenID(accessToken).getUserOpenID();
			UserInfo qzoneUserInfo = new UserInfo(accessToken, openId);
			UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
			if (userInfoBean.getRet() == 0) {
				Map<String, String> userMap = new HashMap<>();
				String nickName = userInfoBean.getNickname();
				String imagePath = userInfoBean.getAvatar().getAvatarURL100();
				userMap.put("username", openId);
				userMap.put("nickName", nickName);
				userMap.put("imagePath", imagePath);
				DaoFactory.getDao(UserDao.class).saveOrUpdateQqLogin(openId, nickName, imagePath);
				request.getSession().setAttribute("user", userMap);
				response.sendRedirect("/blog/question_index.ftl");
			} else {
				AbstractServlet.writeText(response, "很抱歉，我们没能正确获取到您的信息，原因是：" + userInfoBean.getMsg());
			}
		} catch (QQConnectException e) {
			logger.error("getAccessTokenByRequest failed", e);
			AbstractServlet.writeText(response, "qq登陆授权失败！");
		}
	}

}
