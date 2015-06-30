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

import com.zuoxiaolong.dao.*;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.DaoFactory;

import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月31日 下午5:07:46
 */
public abstract class QuestionHelper {

	public static void putQuestionDataMap(Map<String, Object> data, ViewMode viewMode,int questionId) {
		DaoFactory.getDao(QuestionDao.class).updateCommentCount(questionId);
		Map<String, String> question = DaoFactory.getDao(QuestionDao.class).getQuestion(questionId);
		data.put("question", question);
		data.put("answers", DaoFactory.getDao(AnswerDao.class).getAnswers(questionId));
	}
	
	public static String generateStaticPath(int questionId) {
		return "/html/question_" + questionId + ".html";
	}
	
	public static String generateDynamicPath(String staticPath) {
		String name = staticPath.substring(staticPath.lastIndexOf("/") + 1 , staticPath.lastIndexOf("."));
		return generateDynamicPath(Integer.valueOf(name.split("_")[1]));
	}
	
	public static String generateDynamicPath(int id) {
		return "/question/question.ftl?id=" + id;
	}
	
}
