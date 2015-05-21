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

import com.zuoxiaolong.dao.HeroDao;
import com.zuoxiaolong.dao.MatchDao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
		if (logger.isInfoEnabled()) {
			logger.info("search servlet's hero is : " + hero);
		}
		List<Map<String,String>> matches = MatchDao.findMatchesResult(hero);
		if (logger.isInfoEnabled()) {
			logger.info("search servlet's matches is : " + matches);
		}
		Map<String, int[]> totalMap = new HashMap<>();
		Map<String, int[]> attackMap = new HashMap<>();
		Map<String, int[]> defendMap = new HashMap<>();
		for (Map<String,String> match : matches) {
			String battle = null;
			Boolean isAttack = null;
			Map<String, int[]> currentMap = null;
			if (match.get("attack").equals(hero)) {
				//目前计算阵容为防守
				battle = match.get("defend");
				currentMap = defendMap;
				isAttack = false;
			} else {
				//目前计算阵容为进攻
				battle = match.get("attack");
				currentMap = attackMap;
				isAttack = true;
			}
			int[] totalResult = totalMap.get(battle);
			int[] currentResult = currentMap.get(battle);
			//0:胜率。1:总场数。2:胜利场数。3:失败场数。
			if (totalResult == null ) {
				totalResult = new int[4];
			}
			if (currentResult == null ) {
				currentResult = new int[4];
			}
			Integer count = Integer.valueOf(match.get("count"));
			Integer result = Integer.valueOf(match.get("result"));
			
			totalResult[1] += count;
			currentResult[1] += count;
			//如果是进攻方，并且进攻方胜利。或者是防守方，并且防守方胜利。则给胜利场数增加，否则给失败场数增加。
			if ((isAttack && result == 1) || (!isAttack && result == 0)) {
				currentResult[2] += count;
				totalResult[2] += count;
			} else {
				currentResult[3] += count;
				totalResult[3] += count;
			}
			currentResult[0] = currentResult[2] * 100 / currentResult[1];
			currentMap.put(battle, currentResult);
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
		if (logger.isInfoEnabled()) {
			logger.info("search servlet's result is : " + result);
		}
		writeJsonObject(response, result);
	}

}
