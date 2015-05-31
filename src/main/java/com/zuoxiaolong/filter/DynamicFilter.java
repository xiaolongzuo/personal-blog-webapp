package com.zuoxiaolong.filter;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.zuoxiaolong.dynamic.DataMap;
import com.zuoxiaolong.dynamic.DataMapLoader;
import com.zuoxiaolong.freemarker.FreemarkerHelper;
import com.zuoxiaolong.freemarker.IndexHelper;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.servlet.BaseServlet;
import com.zuoxiaolong.util.StringUtil;

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
 * @since 2015年5月24日 上午1:24:45
 */
public class DynamicFilter implements Filter {
	
	private Map<String, DataMap> dataMap = new HashMap<String, DataMap>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String requestUri = null;
		try {
			requestUri = ((HttpServletRequest)request).getRequestURI();
			if (StringUtils.isEmpty(StringUtil.replaceStartSlant(requestUri))) {
				requestUri = IndexHelper.generateDynamicPath();
			}
			response.setCharacterEncoding("UTF-8");
			Writer writer = response.getWriter();
			Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(FreemarkerHelper.getNamespace(requestUri), ViewMode.DYNAMIC);
			String dataMapKey = requestUri.substring(0,requestUri.lastIndexOf("."));
			DataMap current = dataMap.get(dataMapKey);
			if (current == null) {
				if (dataMapKey.startsWith("/")) {
					current = dataMap.get(dataMapKey.substring(1));
				} else {
					current = dataMap.get("/" + dataMapKey);
				}
			}
			if (current != null) {
				current.putCustomData(data, (HttpServletRequest)request, (HttpServletResponse)response);
			}
			Map<String, String> user = BaseServlet.getUser((HttpServletRequest) request);
			if (user != null) {
				data.put("nickName", user.get("nickName"));
				data.put("avatarUrl", user.get("avatarUrl"));
			}
			FreemarkerHelper.generateByTemplatePath(dataMapKey + ".ftl", writer, data);
		} catch (Exception e) {
			throw new RuntimeException(requestUri,e);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		dataMap = DataMapLoader.load();
	}
	
}
