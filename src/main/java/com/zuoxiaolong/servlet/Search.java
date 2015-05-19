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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class Search extends BaseServlet {

	private static final long serialVersionUID = 4998713126399162358L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] h = request.getParameter("h").split(",");
		if (h.length != 5) {
			writeText(response, "请填满五个英雄！");
			return;
		}
		SortedSet<String> hSet = new TreeSet<>();
		for (int i = 0 ; i < h.length;i++) {
			if (h[i].trim().length() == 0) {
				writeText(response, "第" + (i + 1) + "位英雄为空");
				return;
			}
			if (hSet.contains(h[i])) {
				writeText(response, "第" + (i + 1) + "位英雄重复");
				return;
			}
			if (!HeroDao.exsits(h[i].trim())) {
				writeText(response, "第" + (i + 1) + "位英雄在英雄库中没找到，请按照提示输入英雄");
				return;
			}
			hSet.add(h[i]);
		}
		String hero = JSONArray.fromObject(hSet).toString();
		List<Map<String,String>> matches = MatchDao.findMatchesResult(hero);
		Map<String, int[]> totalMap = new HashMap<>();
		Map<String, int[]> attackMap = new HashMap<>();
		Map<String, int[]> defendMap = new HashMap<>();
		for (Map<String,String> match : matches) {
			String battle = null;
			boolean isAttack = false;
			if (match.get("attack").equals(hero)) {
				//目前计算阵容为防守
				battle = match.get("defend");
			} else {
				//目前计算阵容为进攻
				battle = match.get("attack");
				isAttack = true;
			}

			int[] totalResult = totalMap.get(battle);
			if (totalResult == null ) {
				totalResult = new int[4];
			}
			totalResult[1]++;
			if (!isAttack) {
				//目前计算阵容为防守
				int[] result = defendMap.get(battle);
				if (result == null ) {
					result = new int[4];
				}
				result[1]++;
				if (match.get("result").equals("1")) {
					result[3]++;
					totalResult[3]++;
				} else {
					result[2]++;
					totalResult[2]++;
				}
				result[0] = result[2] * 100 / result[1];
				defendMap.put(battle, result);
			} else {
				//目前计算阵容为进攻
				int[] result = attackMap.get(battle);
				if (result == null ) {
					result = new int[4];
				}
				result[1]++;
				if (match.get("result").equals("1")) {
					result[2]++;
					totalResult[2]++;
				} else {
					result[3]++;
					totalResult[3]++;
				}
				result[0] = result[2] * 100 / result[1];
				attackMap.put(battle, result);
			}
			totalResult[0] = totalResult[2] * 100 / totalResult[1];
			totalMap.put(battle, totalResult);
		}
		Map<String,Object> result = new HashMap<>();
		result.put("attackMap", attackMap);
		result.put("defendMap", defendMap);
		result.put("totalMap", totalMap);
		List<String> orderList = new ArrayList<>();
		for (String battle : totalMap.keySet()) {
			int successRate = totalMap.get(battle)[0];
			List<String> temp = new ArrayList<>(orderList);
			for (int i =0; i< temp.size() ;i++) {
				int maxSuccessRate = totalMap.get(temp.get(i))[0];
				if (successRate > maxSuccessRate) {
					orderList.add(i,battle);
					break;
				}
			}
			if (temp.size() == orderList.size()) {
				orderList.add(battle);
			}
		}
		result.put("orderList", orderList);
		writeJsonObject(response, result);
	}

}
