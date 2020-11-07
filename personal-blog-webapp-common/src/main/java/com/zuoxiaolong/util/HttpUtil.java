package com.zuoxiaolong.util;

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

import com.google.gson.Gson;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 左潇龙
 * @since 2015年5月24日 上午12:23:01
 */
public abstract class HttpUtil {

	private static HttpURLConnection httpURLConnection(String url, String method) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod(method);
		connection.setReadTimeout(3000);
		connection.setConnectTimeout(1000);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}

	public static String sendHttpJsonRequest(String url, String method , Object params) {
		try {
			HttpURLConnection connection = httpURLConnection(url, method);
			connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
			connection.connect();
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(JsonUtils.toJson(params).getBytes("UTF-8"));
			outputStream.flush();
			return new String(IOUtil.readStreamBytes(connection), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static String sendHttpRequest(String url, String method , Map<String, Object> params) {
		return sendHttpRequest(url, method, null, params);
	}

	public static String sendHttpRequest(String url, String method , Map<String, Object> headerMap, Map<String, Object> params) {
		try {
			StringBuffer stringBuffer = new StringBuffer();
			for (String key : params.keySet()) {
				stringBuffer.append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), "UTF-8")).append("&");
			}
			if (method.equals("GET") && (params != null && params.size() > 0)) {
				url = url + "?" + stringBuffer.substring(0, stringBuffer.length() - 1);
			}
			HttpURLConnection connection = httpURLConnection(url, method);
			if (headerMap != null) {
				for (String headerName : headerMap.keySet()) {
					connection.setRequestProperty(headerName, headerMap.get(headerName).toString());
				}
			}
			connection.connect();
			if (method.equals("POST") && (params != null && params.size() > 0)) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(stringBuffer.substring(0, stringBuffer.length() - 1).getBytes("UTF-8"));
				outputStream.flush();
			}
			return new String(IOUtil.readStreamBytes(connection), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

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
	
}
