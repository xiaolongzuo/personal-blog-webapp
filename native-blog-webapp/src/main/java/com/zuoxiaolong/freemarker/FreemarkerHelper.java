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

import com.zuoxiaolong.algorithm.Match;
import com.zuoxiaolong.algorithm.Random;
import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.ArticleDao;
import com.zuoxiaolong.dao.CommentDao;
import com.zuoxiaolong.dao.MatchDao;
import com.zuoxiaolong.dao.TagDao;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.model.Type;
import com.zuoxiaolong.model.ViewMode;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.StringUtil;
import freemarker.template.Template;
import org.apache.log4j.Logger;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 左潇龙
 * @since 2015年5月24日 上午2:04:43
 */
public abstract class FreemarkerHelper {

    private static final Logger logger = Logger.getLogger(FreemarkerHelper.class);

    private static final String DEFAULT_NAMESPACE = "blog";

    private static final int DEFAULT_RIGHT_ARTICLE_NUMBER = 5;

    private static final int DEFAULT_RIGHT_COMMENT_NUMBER = 5;

    private static final int DEFAULT_RIGHT_TAG_NUMBER = 8;

    public static Map<String, Object> buildCommonDataMap(ViewMode viewMode) {
        return buildCommonDataMap(DEFAULT_NAMESPACE, viewMode);
    }

    public static Map<String, Object> buildCommonDataMap(String namespace, ViewMode viewMode) {
        Map<String, Object> data = new HashMap<>();
        String contextPath = Configuration.getSiteUrl();
        data.put("contextPath", contextPath);
        data.put("questionUrl", contextPath + "/question/question_index.ftl");
        if (ViewMode.DYNAMIC == viewMode) {
            data.put("indexUrl", IndexHelper.generateDynamicPath());
            data.put("questionIndexUrl", QuestionListHelper.generateDynamicPath(1));
            data.put("recordIndexUrl", RecordListHelper.generateDynamicPath(1));
            data.put("novelIndexUrl", ArticleListHelper.generateDynamicTypePath(1, 1));
        } else {
            data.put("indexUrl", IndexHelper.generateStaticPath());
            data.put("questionIndexUrl", QuestionListHelper.generateStaticPath(1));
            data.put("recordIndexUrl", RecordListHelper.generateStaticPath(1));
            data.put("novelIndexUrl", ArticleListHelper.generateStaticPath("novel", 1));
        }
        if (namespace.equals("dota")) {
            List<Map<String, String>> matchList = DaoFactory.getDao(MatchDao.class).getAll();
            List<Map<String, Object>> hotCharts = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> winCharts = new ArrayList<Map<String,Object>>();
            List<Map<String, Object>> winTimesCharts = new ArrayList<Map<String,Object>>();
            Map<String, int[]> resultCountMap = Match.computeHeroCharts(matchList);
            Match.fillHeroCharts(resultCountMap, hotCharts, winCharts, winTimesCharts);
            data.put("hotCharts", hotCharts);
            data.put("winCharts", winCharts);
            data.put("winTimesCharts", winTimesCharts);
            data.put("totalCount", DaoFactory.getDao(MatchDao.class).count());
        } else {
        	List<Map<String, String>> articleList = DaoFactory.getDao(ArticleDao.class).getArticles("create_date", Status.published, Type.article, viewMode);
            data.put("accessCharts",DaoFactory.getDao(ArticleDao.class).getArticles("access_times", Status.published, Type.article, viewMode));
            data.put("newCharts",DaoFactory.getDao(ArticleDao.class).getArticles("create_date", Status.published, viewMode));
            data.put("recommendCharts",DaoFactory.getDao(ArticleDao.class).getArticles("good_times", Status.published, Type.article, viewMode));
            data.put("imageArticles",Random.random(articleList, DEFAULT_RIGHT_ARTICLE_NUMBER));
            data.put("hotTags", Random.random(DaoFactory.getDao(TagDao.class).getHotTags(), DEFAULT_RIGHT_TAG_NUMBER));
            data.put("newComments", DaoFactory.getDao(CommentDao.class).getLastComments(DEFAULT_RIGHT_COMMENT_NUMBER, viewMode));
            if (ViewMode.DYNAMIC == viewMode) {
                data.put("accessArticlesUrl", ArticleListHelper.generateDynamicPath("access_times", 1));
                data.put("newArticlesUrl", ArticleListHelper.generateDynamicPath("create_date", 1));
                data.put("recommendArticlesUrl", ArticleListHelper.generateDynamicPath("good_times", 1));
            } else {
            	data.put("accessArticlesUrl", ArticleListHelper.generateStaticPath("access_times", 1));
                data.put("newArticlesUrl", ArticleListHelper.generateStaticPath("create_date", 1));
                data.put("recommendArticlesUrl", ArticleListHelper.generateStaticPath("good_times", 1));
            }
		}
        return data;
    }

    public static void generate(String template, Writer writer, ViewMode viewMode) {
        generate(DEFAULT_NAMESPACE, template, writer, buildCommonDataMap(viewMode));
    }

    public static void generate(String template, Writer writer, Map<String, Object> data) {
        generate(DEFAULT_NAMESPACE, template, writer, data);
    }

    public static void generate(String namespace, String template, Writer writer, ViewMode viewMode) {
        generate(namespace, template, writer, buildCommonDataMap(namespace, viewMode));
    }

    public static void generate(String namespace, String template, Writer writer, Map<String, Object> data) {
        generateByTemplatePath(namespace + "/" + template + ".ftl", writer, data);
    }

    public static void generateByTemplatePath(String templatePath, Writer writer, ViewMode viewMode) {
        generateByTemplatePath(templatePath, writer, buildCommonDataMap(getNamespace(templatePath), viewMode));
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

    /**
     * @param templatePath
     * @return
     */
    public static String getNamespace(String templatePath) {
        templatePath = StringUtil.replaceStartSlant(templatePath);
        return templatePath.substring(0, templatePath.indexOf("/"));
    }
    
}
