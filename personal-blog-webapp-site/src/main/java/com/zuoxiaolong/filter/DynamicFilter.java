package com.zuoxiaolong.filter;

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

import com.zuoxiaolong.cache.CacheManager;
import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.freemarker.FreemarkerHelper;
import com.zuoxiaolong.freemarker.IndexHelper;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.mvc.DataMap;
import com.zuoxiaolong.mvc.DataMapLoader;
import com.zuoxiaolong.servlet.AbstractServlet;
import com.zuoxiaolong.util.StringUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author 左潇龙
 * @since 2015年5月24日 上午1:24:45
 */
public class DynamicFilter implements Filter {

    private Map<String, DataMap> dataMap = new HashMap<String, DataMap>();

    private String[] loginFilterUrls;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestUri = StringUtil.replaceSlants(((HttpServletRequest) request).getRequestURI());
        try {
            Map<String, Object> data = FreemarkerHelper.buildCommonDataMap(FreemarkerHelper.getNamespace(requestUri), ViewMode.DYNAMIC);
            boolean forbidden = loginFilter(data, requestUri, request);
            if (forbidden) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            String template = putCustomData(data, requestUri, request, response);
            response.setCharacterEncoding("UTF-8");
            FreemarkerHelper.generateByTemplatePath(template + ".ftl", response.getWriter(), data);
        } catch (Exception e) {
            throw new RuntimeException(requestUri, e);
        }
    }

    private String putCustomData(Map<String, Object> data, String requestUri, ServletRequest request, ServletResponse response) {
        if (StringUtils.isEmpty(StringUtil.replaceStartSlant(requestUri))) {
            requestUri = IndexHelper.generateDynamicPath();
        }
        String dataMapKey = requestUri.substring(((HttpServletRequest) request).getContextPath().length(), requestUri.lastIndexOf("."));
        DataMap current = dataMap.get(dataMapKey);
        if (current == null) {
            if (dataMapKey.startsWith("/")) {
                current = dataMap.get(dataMapKey.substring(1));
            } else {
                current = dataMap.get("/" + dataMapKey);
            }
        }
        if (current != null) {
            current.putCustomData(data, (HttpServletRequest) request, (HttpServletResponse) response);
        }
        return dataMapKey;
    }

    private boolean loginFilter(Map<String, Object> data, String requestUri, ServletRequest request) {
        Map<String, String> user = AbstractServlet.getUser((HttpServletRequest) request);
        if (user != null) {
            data.put("user", user);
            return false;
        }
        Set<String> cachedLoginFilterExcludeUrls = (Set<String>) CacheManager.getConcurrentHashMapCache().get("loginFilterExcludeUrls");
        if (cachedLoginFilterExcludeUrls != null && cachedLoginFilterExcludeUrls.contains(requestUri)) {
            return false;
        }
        Set<String> cachedLoginFilterUrls = (Set<String>) CacheManager.getConcurrentHashMapCache().get("loginFilterUrls");
        if (cachedLoginFilterUrls != null && cachedLoginFilterUrls.contains(requestUri)) {
            return true;
        }
        boolean isLoginFilterUrl = false;
        for (int i = 0; i < loginFilterUrls.length; i++) {
            if (Pattern.matches(loginFilterUrls[i], requestUri)) {
                isLoginFilterUrl = true;
                break;
            }
        }
        synchronized (this) {
            if (isLoginFilterUrl) {
                if (cachedLoginFilterUrls == null) {
                    cachedLoginFilterUrls = new HashSet<>();
                }
                cachedLoginFilterUrls.add(requestUri);
                CacheManager.getConcurrentHashMapCache().set("loginFilterUrls", cachedLoginFilterUrls);
            } else {
                if (cachedLoginFilterExcludeUrls == null) {
                    cachedLoginFilterExcludeUrls = new HashSet<>();
                }
                cachedLoginFilterExcludeUrls.add(requestUri);
                CacheManager.getConcurrentHashMapCache().set("loginFilterExcludeUrls", cachedLoginFilterExcludeUrls);
            }
        }
        if (isLoginFilterUrl) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        dataMap = DataMapLoader.load();
        loginFilterUrls = Configuration.get("login.filter.url") == null ? new String[0] : Configuration.get("login.filter.url").split(",");
    }

}
