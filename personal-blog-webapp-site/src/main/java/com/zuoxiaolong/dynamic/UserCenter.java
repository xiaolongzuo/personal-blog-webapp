package com.zuoxiaolong.dynamic;

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

import com.zuoxiaolong.client.HttpClient;
import com.zuoxiaolong.client.HttpUriEnums;
import com.zuoxiaolong.mvc.DataMap;
import com.zuoxiaolong.mvc.Namespace;
import com.zuoxiaolong.servlet.AbstractServlet;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年6月16日 上午1:35:04
 */
@Namespace("common")
public class UserCenter implements DataMap {

    @Override
    public void putCustomData(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> user = AbstractServlet.getUser(request);
        if (user == null) {
            throw new RuntimeException();
        }
        if (StringUtils.isNotBlank(user.get("province"))) {
            Integer provinceId = HttpClient.get(Integer.class, HttpUriEnums.PROVINCE_GET_ID, new String[]{"name"}, user.get("province"));
            data.put("cities", HttpClient.get(List.class, HttpUriEnums.CITY_GET_CITIES, new String[]{"provinceId"}, provinceId));
        }
        data.put("provinces", HttpClient.get(List.class, HttpUriEnums.PROVINCE_GET_PROVINCES, new String[]{}, new Object[]{}));
        data.put("languages", HttpClient.get(List.class, HttpUriEnums.DICTIONARY_GET_DICTIONARIES_BY_TYPE, new String[]{"type"}, "LANG"));
    }

}
