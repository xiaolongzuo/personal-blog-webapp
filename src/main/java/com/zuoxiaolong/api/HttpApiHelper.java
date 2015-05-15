package com.zuoxiaolong.api;

import com.zuoxiaolong.config.Configuration;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.InputStream;
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
	
	public static String getCity(String ip) {
		String url = "http://api.map.baidu.com/location/ip?ak=" + BAIDU_AK + "&ip=" + ip;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			InputStream inputStream = connection.getInputStream();
			byte[] bytes = new byte[2048];
			byte[] result = new byte[0];
			int len = 0;
			while ((len = inputStream.read(bytes)) > 0) {
				byte[] temp = new byte[result.length + len];
				System.arraycopy(result, 0, temp, 0, result.length);
				System.arraycopy(bytes, 0, temp, result.length, len);
				result = temp;
			}
			String json = new String(result,"UTF-8");
			if (logger.isInfoEnabled()) {
				logger.info("baidu-ip-api json : " + json);
			}
			JSONObject addressDetail = JSONObject.fromObject(json).getJSONObject("content").getJSONObject("address_detail");
			String address = StringUtils.isEmpty(addressDetail.getString("city")) ? addressDetail.getString("province") : addressDetail.getString("city");
			if (!StringUtils.isEmpty(address)) {
				return address;
			}
		} catch (Exception e) {
			logger.error("get city failed for ip : " + ip, e);
		}
		return "来自星星的";
	}
	
}
