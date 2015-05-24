package com.zuoxiaolong.freemarker;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.MatchDao;

import freemarker.template.Template;

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
 * @since 2015年5月24日 上午2:04:43
 */
public abstract class FreemarkerHelper {
	
	private static final Logger logger = Logger.getLogger(FreemarkerHelper.class);
	
	private static final Map<String, Map<String, Object>> NAMESPACE_DATA_MAP = new HashMap<>();
	
	private static final String DEFAULT_NAMESPACE = "blog";
	
	static {
		Map<String, Object> blogData = new HashMap<String, Object>();
		blogData.put("accessCharts",ArticleDao.getArticles("access_times"));
		blogData.put("newCharts",ArticleDao.getArticles("create_date"));
		blogData.put("recommendCharts",ArticleDao.getArticles("good_times"));
		blogData.put("imageArticles",ArticleDao.getArticles("good_times"));
		
		Map<String, Object> dotaData = new HashMap<String, Object>();
		List<Map<String, String>> matchList = MatchDao.getAll();
		List<Map<String, Object>> hotCharts = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> winCharts = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> winTimesCharts = new ArrayList<Map<String,Object>>();
		
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
		for (String hero : resultCountMap.keySet()) {
			int[] resultCount = resultCountMap.get(hero);
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
		dotaData.put("hotCharts", hotCharts);
		dotaData.put("winCharts", winCharts);
		dotaData.put("winTimesCharts", winTimesCharts);
		dotaData.put("totalCount", MatchDao.count());
		NAMESPACE_DATA_MAP.put("blog", blogData);
		NAMESPACE_DATA_MAP.put("dota", dotaData);
	}
	
	public static Map<String, Object> buildCommonDataMap() {
		return buildCommonDataMap(DEFAULT_NAMESPACE);
	}

	public static Map<String, Object> buildCommonDataMap(String namespace) {
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("contextPath", Configuration.isProductEnv() ? Configuration.get("context.path.product") : Configuration.get("context.path"));
        data.putAll(NAMESPACE_DATA_MAP.get(namespace));
        return data;
	}
	
	public static void generate(String template, Writer writer) {
		generate(DEFAULT_NAMESPACE, template, writer, buildCommonDataMap());
	}
	
	public static void generate(String template, Writer writer, Map<String, Object> data) {
		generate(DEFAULT_NAMESPACE, template, writer, data);
	}
	
	public static void generate(String namespace, String template, Writer writer) {
		generate(namespace, template, writer, buildCommonDataMap(namespace));
	}
	
	public static void generate(String namespace, String template, Writer writer, Map<String, Object> data) {
		generateByTemplatePath(namespace + "/" + template + ".ftl", writer, data);
	}
	
	public static void generateByTemplatePath(String templatePath, Writer writer) {
		generateByTemplatePath(templatePath, writer, buildCommonDataMap(getNamespace(templatePath)));
	}
	
	public static void generateByTemplatePath(String templatePath, Writer writer, Map<String, Object> data) {
		try {
			Template htmlTemplate = Configuration.getFreemarkerConfiguration().getTemplate(templatePath);
			htmlTemplate.process(data, writer);
		} catch (Exception e) {
			logger.error("get template failed for templatePath:" + templatePath, e);
			throw new RuntimeException(e);
		}
	}
	
	public static String getNamespace(String templatePath) {
		while (templatePath.startsWith("/")) {
			templatePath = templatePath.substring(1);
		}
		return templatePath.substring(0, templatePath.indexOf("/"));
	}
	
}
