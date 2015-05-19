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
 * @since 2015年5月19日 上午1:11:48
 */
package com.zuoxiaolong.servlet;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.zuoxiaolong.dao.HeroDao;
import com.zuoxiaolong.dao.MatchDao;

/**
 * @author zuoxiaolong
 *
 */
public class SaveMatch extends BaseServlet {

	private static final long serialVersionUID = 4998713126399162358L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] a = request.getParameter("a").split(",");
		String[] d = request.getParameter("d").split(",");
		Integer result = Integer.valueOf(request.getParameter("result"));
		Integer count = null;
		String countString = request.getParameter("count");
		if (countString != null ) {
			try {
				count = Integer.valueOf(countString);
			} catch (Exception e) {
				writeText(response, "对战场数必须为数字！");
				return;
			}
		}
		if (a.length != 5 || d.length != 5 ) {
			writeText(response, "请填满十个英雄！");
			return;
		}
		SortedSet<String> aSet = new TreeSet<>();
		SortedSet<String> dSet = new TreeSet<>();
		for (int i = 0 ; i < a.length && i < d.length;i++) {
			if (a[i].trim().length() == 0) {
				writeText(response, "进攻方第" + (i + 1) + "位英雄为空！");
				return;
			}
			if (d[i].trim().length() == 0) {
				writeText(response, "防守方第" + (i + 1) + "位英雄为空！");
				return;
			}
			if (aSet.contains(a[i])) {
				writeText(response, "进攻方第" + (i + 1) + "位英雄重复！");
				return;
			}
			if (dSet.contains(d[i])) {
				writeText(response, "防守方第" + (i + 1) + "位英雄重复！");
				return;
			}
			if (!HeroDao.exsits(a[i].trim())) {
				writeText(response, "进攻方第" + (i + 1) + "位英雄在英雄库中没找到，请按照提示输入英雄！");
				return;
			}
			if (!HeroDao.exsits(d[i].trim())) {
				writeText(response, "防守方第" + (i + 1) + "位英雄在英雄库中没找到，请按照提示输入英雄");
				return;
			}
			aSet.add(a[i]);
			dSet.add(d[i]);
		}
		String attack = JSONArray.fromObject(aSet).toString();
		String defend = JSONArray.fromObject(dSet).toString();
		if (attack.equals(defend)) {
			writeText(response, "进攻方和防守方阵容一样，不能进行保存！");
			return;
		}
		if (MatchDao.save(attack,defend,result,count)) {
			writeText(response, "success");
		} else {
			writeText(response, "发生未知错误，请联系男神！");
		}
	}

}
