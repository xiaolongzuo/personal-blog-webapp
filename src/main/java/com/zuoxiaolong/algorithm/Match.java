package com.zuoxiaolong.algorithm;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

/**
 * @author 左潇龙
 * @since 2015年5月25日 上午12:15:40
 */
public abstract class Match {

	public static Map<String, int[]> computeHeroCharts(List<Map<String, String>> matchList) {
		Map<String, int[]> resultCountMap = new HashMap<>();
		for (Map<String, String> match : matchList) {
			JSONArray attack = JSONArray.fromObject(match.get("attack"));
			JSONArray defend = JSONArray.fromObject(match.get("defend"));
			Integer count = Integer.valueOf(match.get("count"));
			Integer result = Integer.valueOf(match.get("result"));
			for (int i = 0; i < attack.size(); i++) {
				String hero = attack.getString(i);
				int[] resultCount = resultCountMap.get(hero);
				if (resultCount == null) {
					resultCount = new int[3];
				}
				resultCount[1] += count;
				if (result == 1) {
					resultCount[2] += count;
				}
				resultCount[0] = resultCount[2] * 100 / resultCount[1];
				resultCountMap.put(hero, resultCount);
			}
			for (int i = 0; i < defend.size(); i++) {
				String hero = defend.getString(i);
				int[] resultCount = resultCountMap.get(hero);
				if (resultCount == null) {
					resultCount = new int[3];
				}
				resultCount[1] += count;
				if (result == 0) {
					resultCount[2] += count;
				}
				resultCount[0] = resultCount[2] * 100 / resultCount[1];
				resultCountMap.put(hero, resultCount);
			}
		}
		return resultCountMap;
	}
	
	public static void fillHeroCharts(Map<String, int[]> countMap, List<Map<String, Object>> hotCharts, List<Map<String, Object>> winCharts, List<Map<String, Object>> winTimesCharts) {
		if (hotCharts == null || winCharts == null || winTimesCharts == null) {
			throw new NullPointerException("hotCharts and winCharts and winTimesCharts can't be null!");
		}
		for (String hero : countMap.keySet()) {
			int[] resultCount = countMap.get(hero);
			Map<String, Object> heroMap = new HashMap<>();
			heroMap.put("fullName", hero);
			heroMap.put("win", resultCount[0]);
			heroMap.put("times", resultCount[1]);
			heroMap.put("winTimes", resultCount[2]);
			List<Map<String, Object>> temp = new ArrayList<>(hotCharts);
			for (int i =0; i< temp.size() ;i++) {
				if (resultCount[1] > (int) temp.get(i).get("times")) {
					hotCharts.add(i,heroMap);
					break;
				}
			}
			if (temp.size() == hotCharts.size()) {
				hotCharts.add(heroMap);
			}
			
			temp = new ArrayList<>(winCharts);
			for (int i =0; i< temp.size() ;i++) {
				if (resultCount[0] > (int) temp.get(i).get("win") && resultCount[1] > 10) {
					winCharts.add(i,heroMap);
					break;
				}
			}
			if (temp.size() == winCharts.size() && resultCount[1] > 10) {
				winCharts.add(heroMap);
			}
			
			temp = new ArrayList<>(winTimesCharts);
			for (int i =0; i< temp.size() ;i++) {
				if (resultCount[2] > (int) temp.get(i).get("winTimes")) {
					winTimesCharts.add(i,heroMap);
					break;
				}
			}
			if (temp.size() == winTimesCharts.size()) {
				winTimesCharts.add(heroMap);
			}
		}
	}
	
	public static Map<String, int[]> computeBattleCharts(List<Map<String, String>> matchList, String currentBattle) {
		Map<String, int[]> totalMap = new HashMap<>();
		for (Map<String,String> match : matchList) {
			String battle = null;
			Boolean isAttack = null;
			Integer count = Integer.valueOf(match.get("count"));
			Integer result = Integer.valueOf(match.get("result"));
			if (match.get("attack").equals(currentBattle)) {
				battle = match.get("defend");
				isAttack = false;
			} else {
				battle = match.get("attack");
				isAttack = true;
			}
			int[] totalResult = totalMap.get(battle);
			if (totalResult == null ) {
				totalResult = new int[3];
			}
			totalResult[1] += count;
			if ((isAttack && result == 1) || (!isAttack && result == 0)) {
				totalResult[2] += count;
			}
			totalResult[0] = totalResult[2] * 100 / totalResult[1];
			totalMap.put(battle, totalResult);
		}
		return totalMap;
	}
	
	public static void fillOrderList(Map<String, int[]> countMap, List<String> orderList) {
		for (String battle : countMap.keySet()) {
			int successRate = countMap.get(battle)[0];
			List<String> temp = new ArrayList<>(orderList);
			for (int i =0; i< temp.size() ;i++) {
				int maxSuccessRate = countMap.get(temp.get(i))[0];
				if (successRate > maxSuccessRate && successRate > 0) {
					orderList.add(i,battle);
					break;
				}
			}
			if (temp.size() == orderList.size() && successRate > 0) {
				orderList.add(battle);
			}
		}
	}
	
}
