package com.zuoxiaolong.dynamic;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
 * @since 5/28/2015 7:08 PM
 */
@Namespace
public class Feed implements DataMap {

    @Override
    public void putCustomData(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Content-Type","text/xml; charset=utf-8");
        Map<String, Integer> pager = new HashMap<>();
        pager.put("current", 1);
        data.put("articles", ArticleDao.getPageArticles(pager ,VIEW_MODE));
        data.put("lastBuildDate", DateUtil.rfc822(new Date()));
    }

}
