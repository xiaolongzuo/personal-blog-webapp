package com.zuoxiaolong.api;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.HtmlPageDao;
import com.zuoxiaolong.util.IOUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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
	
	public static void baiduPush(int remain) {
		String pushUrl = HtmlPageDao.findPushUrl();
		if (pushUrl == null) {
			if (logger.isInfoEnabled()) {
				logger.info("all html page has been pushed!");
			}
			return;
		}
		if (remain <= 0) {
			if (logger.isInfoEnabled()) {
				logger.info("there has no remain[" + remain + "]!");
			}
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info("find push url : " + pushUrl);
		}
		String url = "http://data.zz.baidu.com/urls?site=" + site + "&token=" + token;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/plain");
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(pushUrl.getBytes("UTF-8"));
			outputStream.write("\r\n".getBytes("UTF-8"));
			outputStream.flush();
			int status = connection.getResponseCode();
			if (logger.isInfoEnabled()) {
				logger.info("baidu-push response code : " + status);
			}
			if (status == HttpServletResponse.SC_OK) {
				String response = IOUtil.read(connection.getInputStream());
				if (logger.isInfoEnabled()) {
					logger.info("baidu-push response : " + response);
				}
				JSONObject result = JSONObject.fromObject(response);
				if (result.getInt("success") >= 1) {
					HtmlPageDao.updateIsPush(pushUrl);
				} else {
					logger.warn("push url failed : " + pushUrl);
				}
				baiduPush(result.getInt("remain"));
			} else {
				logger.error("baidu-push error : " + IOUtil.read(connection.getErrorStream()));
			}
		} catch (Exception e) {
			logger.error("baidu push failed ...", e);
		}
	}
	
	public static String getCity(String ip) {
		String city = "来自星星的";
		if (ip.equals("127.0.0.1")) {
			return city;
		}
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
