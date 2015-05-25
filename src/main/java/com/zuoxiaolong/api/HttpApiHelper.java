package com.zuoxiaolong.api;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.util.IOUtil;


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
 * @since 2015年5月12日 下午4:58:52
 */
public abstract class HttpApiHelper {
	
	private static final Logger logger = Logger.getLogger(HttpApiHelper.class);

	private static final String BAIDU_AK = Configuration.isProductEnv()? Configuration.get("baidu.ak.product") : Configuration.get("baidu.ak");
	
	private static final String site = Configuration.isProductEnv() ? Configuration.get("site.product") : Configuration.get("site"); 
	
	private static final String token = Configuration.isProductEnv() ? Configuration.get("baidu.push.token.product") : Configuration.get("baidu.push.token");
	
	private static volatile int cursor = 0;
	
	public static int baiduPush() {
		return baiduPush(1);
	}
	
	private static int baiduPush(int remain) {
		if (remain <= 0 ) {
			logger.warn("baidu-push arg lt 0 : " + remain);
			return 0;
		}
		String url = "http://data.zz.baidu.com/urls?site=" + site + "&token=" + token;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/plain");
			OutputStream outputStream = connection.getOutputStream();
			File[] htmlFiles = new File(Configuration.getContextPath("html")).listFiles();
			String contextPath = Configuration.isProductEnv() ? Configuration.get("context.path.product") : Configuration.get("context.path");
			if (remain == 1) {
				outputStream.write(contextPath.getBytes("UTF-8"));
			} else {
				List<String> pushList = new ArrayList<String>();
				if (htmlFiles.length <= remain) {
					for (cursor = 0; cursor < htmlFiles.length; cursor++) {
						pushList.add(contextPath + "/html/" + htmlFiles[cursor].getName());
					}
				} else {
					for (int i = 0; i < remain && cursor < htmlFiles.length; i++, cursor++) {
						pushList.add(contextPath + "/html/" + htmlFiles[cursor].getName());
						if (cursor == htmlFiles.length - 1) {
							cursor = 0;
						}
					}
				}
				for (int i = 0; i < pushList.size(); i++) {
					outputStream.write(pushList.get(i).getBytes("UTF-8"));
					if (i < pushList.size() - 1) {
						outputStream.write("\r\n".getBytes("UTF-8"));
					}
				}
			}
			outputStream.flush();
			String response = IOUtil.read(connection.getInputStream());
			if (logger.isInfoEnabled()) {
				logger.info("baidu-push response : " + response);
			}
			JSONObject result = JSONObject.fromObject(response);
			remain = result.getInt("remain");
			if (remain > 0) {
				return baiduPush(remain);
			} else {
				return 0;
			}
		} catch (Exception e) {
			logger.error("baidu push failed ...", e);
			return 0;
		}
	}
	
	public static String getCity(String ip) {
		String city = "来自星星的";
		String url = "http://api.map.baidu.com/location/ip?ak=" + BAIDU_AK + "&ip=" + ip;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			String json = IOUtil.read(connection.getInputStream());
			if (logger.isInfoEnabled()) {
				logger.info("baidu-ip-api json : " + json);
			}
			JSONObject resultJsonObject = JSONObject.fromObject(json);
			if (resultJsonObject == null) {
				return city;
			}
			JSONObject contentJsonObject = resultJsonObject.containsKey("content") ? resultJsonObject.getJSONObject("content") : null;
			if (contentJsonObject == null) {
				return city;
			}
			JSONObject addressDetailJsonObject = contentJsonObject.containsKey("address_detail") ? contentJsonObject.getJSONObject("address_detail") : null;
			if (addressDetailJsonObject == null) {
				return city;
			}
			String cityInResult = addressDetailJsonObject.getString("city");
			String provinceInResult = addressDetailJsonObject.getString("province");
			String address = StringUtils.isEmpty(cityInResult) ? provinceInResult : cityInResult;
			if (!StringUtils.isEmpty(address)) {
				city = address;
			}
		} catch (Exception e) {
			logger.error("get city failed for ip : " + ip, e);
		}
		return city;
	}
	
}
