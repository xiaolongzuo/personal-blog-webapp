package com.zuoxiaolong.freemarker;

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

import com.zuoxiaolong.dao.QuestionDao;
import com.zuoxiaolong.dao.RecordDao;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.search.LuceneHelper;
import com.zuoxiaolong.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月31日 下午5:07:46
 */
public abstract class RecordListHelper {

	public static void putDataMap(int current, Map<String, Object> data, ViewMode viewMode) {
        Map<String, Integer> pager = new HashMap<>();
        int total = DaoFactory.getDao(RecordDao.class).getTotal();
        int page = (total % 10 == 0) ? (total / 10) : (total / 10 + 1);
        pager.put("current", current);
        pager.put("total", total);
        pager.put("page", page);
        data.put("pager", pager);
        data.put("records", DaoFactory.getDao(RecordDao.class).getRecords(pager, viewMode));
        if (ViewMode.DYNAMIC == viewMode) {
            data.put("firstPageUrl", generateDynamicPath(1));
            data.put("prePageUrl", generateDynamicPath(current - 1));
            data.put("nextPageUrl", generateDynamicPath(current + 1));
            data.put("lastPageUrl", generateDynamicPath(page));
        } else {
            data.put("firstPageUrl", generateStaticPath(1));
            data.put("prePageUrl", generateStaticPath(current - 1));
            data.put("nextPageUrl", generateStaticPath(current + 1));
            data.put("lastPageUrl", generateStaticPath(page));
        }
	}

    public static String generateStaticPath(int current) {
        return "/html/record_list_"+ current +".html";
    }

    public static String generateDynamicPath(int current) {
        return "/record/record_list.ftl?current=" + current;
    }

    public static String generateDynamicPath(String staticPath) {
        String name = staticPath.substring(staticPath.lastIndexOf("/") + 1 , staticPath.lastIndexOf("."));
        return generateDynamicPath(Integer.valueOf(name.split("_")[2]));
    }

}
