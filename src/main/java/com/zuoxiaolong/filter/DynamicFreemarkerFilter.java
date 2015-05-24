package com.zuoxiaolong.filter;

import java.io.File;
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

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dynamic.DataMap;
import com.zuoxiaolong.dynamic.Namespace;
import com.zuoxiaolong.freemarker.FreemarkerHelper;

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
public class DynamicFreemarkerFilter implements Filter {
	
	private Map<String, DataMap> dataMap = new HashMap<String, DataMap>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			String requestUri = ((HttpServletRequest)request).getRequestURI();
			response.setCharacterEncoding("UTF-8");
			Writer writer = response.getWriter();
			Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(FreemarkerHelper.getNamespace(requestUri));
			DataMap current = dataMap.get(requestUri);
			if (current != null) {
				data.putAll(current.buildDataMap((HttpServletRequest)request, (HttpServletResponse)response));
			}
			FreemarkerHelper.generateByTemplatePath(requestUri, writer, data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		File[] files = Configuration.getClasspathFile("com/zuoxiaolong/dynamic").listFiles();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			if (fileName.endsWith(".class")) {
				fileName = fileName.substring(0, fileName.lastIndexOf(".class"));
			}
			try {
				Class<?> clazz = Configuration.getClassLoader().loadClass("com.zuoxiaolong.dynamic." + fileName);
				if (DataMap.class.isAssignableFrom(clazz) && clazz != DataMap.class) {
					String lowerCaseFileName = fileName.toLowerCase();
					char[] originChars = fileName.toCharArray();
					char[] lowerChars = lowerCaseFileName.toCharArray();
					StringBuffer key = new StringBuffer();
					for (int j = 0; j < originChars.length; j++) {
						if (j == 0) {
							key.append(lowerChars[j]);
						} else if (originChars[j] != lowerChars[j]) {
							key.append('_').append(lowerChars[j]);
						} else {
							key.append(originChars[j]);
						}
					}
					Namespace namespaceAnnotation = clazz.getDeclaredAnnotation(Namespace.class);
					if (namespaceAnnotation == null) {
						throw new RuntimeException(clazz.getName() + " must has annotation with @Namespace");
					}
					dataMap.put(namespaceAnnotation.value() + "/" + key.toString() + ".ftl", (DataMap) clazz.newInstance());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
