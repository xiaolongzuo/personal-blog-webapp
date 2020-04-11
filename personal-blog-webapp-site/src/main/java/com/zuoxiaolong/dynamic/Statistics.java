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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.StatisticsDao;
import com.zuoxiaolong.mvc.DataMap;
import com.zuoxiaolong.mvc.Namespace;
import com.zuoxiaolong.orm.DaoFactory;

/**
 * @author 左潇龙
 * @since 2015年5月27日 上午1:54:53
 */
@Namespace("admin")
public class Statistics implements DataMap {

    @Override
    public void putCustomData(Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        data.put("siteTotalAccessTimes", DaoFactory.getDao(StatisticsDao.class).getSiteTotalAccessTimes());
        data.put("siteTodayAccessTimes", DaoFactory.getDao(StatisticsDao.class).getSiteTodayAccessTimes());
        data.put("siteTotalVisitorIpNumber", DaoFactory.getDao(StatisticsDao.class).getSiteTotalVisitorIpNumber());
        data.put("siteTodayVisitorIpNumber", DaoFactory.getDao(StatisticsDao.class).getSiteTodayVisitorIpNumber());
        data.put("articleTotalAccessTimes", DaoFactory.getDao(StatisticsDao.class).getArticleTotalAccessTimes());
        data.put("commentTotalNumber", DaoFactory.getDao(StatisticsDao.class).getCommentTotalNumber());
        data.put("commentTodayNumber", DaoFactory.getDao(StatisticsDao.class).getCommentTodayNumber());
        data.put("userTotalNumber", DaoFactory.getDao(StatisticsDao.class).getUserTotalNumber());
        data.put("userTodayNumber", DaoFactory.getDao(StatisticsDao.class).getUserTodayNumber());
    }

}
