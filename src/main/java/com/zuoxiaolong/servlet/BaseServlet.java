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
package com.zuoxiaolong.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @author zuoxiaolong
 *
 */
public abstract class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = -6921810339176306346L;
	
	protected final Logger logger = Logger.getLogger(getClass());

	public static String getVisitorIp(HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { 
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	protected void writeJsonObject(HttpServletResponse response,Object object) {
		PrintWriter writer = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			writer.write(JSONObject.fromObject(object).toString());
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void writeJsonArray(HttpServletResponse response,List<?> list) {
		PrintWriter writer = null;
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			writer.write(JSONArray.fromObject(list).toString());
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void writeText(HttpServletResponse response,String text){
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("UTF-8");
			printWriter = response.getWriter();
			printWriter.write(text);
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
