package com.zuoxiaolong.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.zuoxiaolong.freemarker.ArticleHelper;
import com.zuoxiaolong.freemarker.ArticleListHelper;
import com.zuoxiaolong.freemarker.IndexHelper;

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
 * @since 2015年5月10日 上午1:30:40
 */
public abstract class AbstractServlet implements Servlet {

	protected final Logger logger = Logger.getLogger(getClass());
	
	private static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();

	private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		requestThreadLocal.set(request);
		response.setCharacterEncoding("UTF-8");
		responseThreadLocal.set(response);
		service();
	}

	protected abstract void service() throws ServletException, IOException ;

	/*  user profile method */
	protected String getUsername() {
		Map<String, String> user = getUser();
		if (user != null) {
			return user.get("username");
		}
		return null;
	}
	
	protected boolean isLogin() {
		return getUser() != null;
	}

	protected Map<String, String> getUser() {
		return getUser(getRequest());
	}
	
	/*  util method */

	protected String getDynamicUrl() {
		return getDynamicUrl(getRequest());
	}

	protected HttpServletRequest getRequest() {
		return requestThreadLocal.get();
	}
	
	protected HttpServletResponse getResponse() {
		return responseThreadLocal.get();
	}

	protected void writeJsonObject(Object object) {
		writeJsonObject(getResponse(), JSONObject.fromObject(object).toString());
	}

	protected void writeJsonArray(List<?> list) {
		writeJsonArray(getResponse(), list);
	}

	protected void writeText(String text){
		writeText(getResponse(), text);
	}

	/* static method */

	private static final Pattern STATIC_ARTICLE_PATTERN = Pattern.compile("html/article_[0-9]+\\.html");

	private static final Pattern STATIC_ARTICLE_LIST_PATTERN = Pattern.compile("/html/article_list_[a-zA-Z_]+_[0-9]+\\.html");

	@SuppressWarnings("unchecked")
	public static Map<String, String> getUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("user") != null) {
			return (Map<String, String>) session.getAttribute("user");
		}
		return null;
	}

	public static String getDynamicUrl(HttpServletRequest request) {
		String requestUri = request.getHeader("Referer");
		Matcher matcher = STATIC_ARTICLE_PATTERN.matcher(requestUri);
		if (matcher.find()) {
			return ArticleHelper.generateDynamicPath(matcher.group());
		}
		matcher = STATIC_ARTICLE_LIST_PATTERN.matcher(requestUri);
		if (matcher.find()) {
			return ArticleListHelper.generateDynamicPath(matcher.group());
		}
		return IndexHelper.generateDynamicPath();
	}

	public static void writeJsonObject(HttpServletResponse response, Object object) {
		response.setContentType("application/json");
		writeText(response, JSONObject.fromObject(object).toString());
	}

	public static void writeJsonArray(HttpServletResponse response, List<?> list) {
		response.setContentType("application/json");
		writeText(response, JSONArray.fromObject(list).toString());
	}

	public static void writeText(HttpServletResponse response, String text){
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.write(text);
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
