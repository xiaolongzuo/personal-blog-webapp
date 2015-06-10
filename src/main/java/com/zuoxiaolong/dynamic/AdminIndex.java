package com.zuoxiaolong.dynamic;

import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.dao.StatisticsDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * @since 2015年5月27日 上午1:54:53
 */
@Namespace("admin")
public class AdminIndex implements DataMap {
	
	@Override
	public void putCustomData(Map<String, Object> data,HttpServletRequest request, HttpServletResponse response) {
		data.put("newComments", CommentDao.getComments());
		data.put("siteTotalAccessTimes", StatisticsDao.getSiteTotalAccessTimes());
		data.put("siteTodayAccessTimes", StatisticsDao.getSiteTodayAccessTimes());
		data.put("siteTotalVisitorIpNumber", StatisticsDao.getSiteTotalVisitorIpNumber());
		data.put("siteTodayVisitorIpNumber", StatisticsDao.getSiteTodayVisitorIpNumber());
		data.put("articleTotalAccessTimes", StatisticsDao.getArticleTotalAccessTimes());
		data.put("commentTotalNumber", StatisticsDao.getCommentTotalNumber());
		data.put("commentTodayNumber", StatisticsDao.getCommentTodayNumber());
		data.put("userTotalNumber", StatisticsDao.getUserTotalNumber());
		data.put("userTodayNumber", StatisticsDao.getUserTodayNumber());
	}

}
