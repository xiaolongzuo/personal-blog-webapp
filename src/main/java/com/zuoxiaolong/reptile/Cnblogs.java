package com.zuoxiaolong.reptile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.zuoxiaolong.dao.ArticleDao;

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
 * @since 2015年5月12日 下午9:24:21
 */
public abstract class Cnblogs {
	
	private static final Logger logger = Logger.getLogger(Cnblogs.class);
	
	public static void fetchArticles() throws IOException {
        for (int i = 1; ; i++) {
        	if (logger.isInfoEnabled()) {
        		logger.info("begin fetch the " + i + " page...");
			}
            Document document = Jsoup.connect("http://www.cnblogs.com/zuoxiaolong/default.html?page=" + i).get();
            Element mainElement = document.getElementById("mainContent");
            Elements elements = mainElement.getElementsByClass("postTitle");
            int pageSize = 0;
            for (Element element : elements) {
                fetchArticle(element);
                pageSize++;
            }
            if (logger.isInfoEnabled()) {
        		logger.info("fetch success for pagenumber : " + i + ", pagesize : " + pageSize);
			}
            if (pageSize < 10) {
				break;
			}
            pageSize = 0;
        }
    }
	
	private static void fetchArticle(Element element) throws IOException {
        Element subjectElement = element.getElementsByTag("a").first();
        String articleUrl = subjectElement.attr("href");
        Document acticleDocument = Jsoup.connect(articleUrl).get();
        
        //获取标题
        String subject = subjectElement.html().trim();
        
        //获取postid，使用文章页面里的postdesc获取
        Element postIdElement = null;
        Elements postDescAElements = acticleDocument.getElementsByClass("postDesc").first().getElementsByTag("a");
        for (Element aElement : postDescAElements) {
            postIdElement = aElement;
            if (aElement.html().trim().equals("编辑")) {
                break;
            }
        }
        String resourceId = postIdElement.attr("href").substring(postIdElement.attr("href").indexOf("=") + 1);
        Integer postId = Integer.valueOf(resourceId);
        
        //如果postId已经存在则直接退出不再重复保存
        if (ArticleDao.exsits(resourceId)) {
        	if (logger.isInfoEnabled()) {
        		logger.info("skiped exsits article for [" + resourceId + ":" + subject + "]");
			}
			return;
		}
        
        //获取内容
        Element bodyElement = acticleDocument.getElementById("cnblogs_post_body");
        String html = bodyElement.html();
        String originHtml = getArticleHtml(articleUrl);
        List<String> codeList = getPreList(html, false);
        List<String> originCodeList = getPreList(originHtml, true);
        if (codeList.size() != originCodeList.size()) throw new RuntimeException();
        for (int i = 0; i < codeList.size() ; i++) {
            html = html.replace(codeList.get(i), originCodeList.get(i));
        }
        html = html.replace("'", "\"");

        //获取纯文本内容
        StringBuffer stringBuffer = new StringBuffer();
        appendText(bodyElement, stringBuffer);
        String content = stringBuffer.toString().replace("'", "\"");

        //获取文章基本属性，使用文章索引里面的postdesc获取
        Element postDescAElement = element.nextElementSibling().nextElementSibling().nextElementSibling();
        String[] attrs = postDescAElement.text().split("\\s");
        String createDate = attrs[2] + " " + attrs[3] + ":00";
        String username = attrs[4];
        Integer accessTimes = Integer.valueOf(attrs[5].substring(attrs[5].indexOf("(") + 1, attrs[5].length() - 1));

        //获取赞的次数
        Document diggCountDocument = Jsoup.connect("http://www.cnblogs.com/mvc/blog/BlogPostInfo.aspx?blogId=160491&postId=" + postId + "&blogApp=zuoxiaolong&blogUserGuid=8834a931-b305-e311-8d02-90b11c0b17d6").get();
        Integer goodTimes = Integer.valueOf(diggCountDocument.getElementById("digg_count").html());

        ArticleDao.save(resourceId, subject, createDate, username, accessTimes, goodTimes, html, content);
        if (logger.isInfoEnabled()) {
    		logger.info("save article : [" + resourceId + ":" + subject + "]");
		}
	}

    private static String getArticleHtml(String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        InputStream inStream = httpURLConnection.getInputStream();
        byte[] stringBytes = new byte[0];
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inStream.read(bytes)) > 0) {
            byte[] tempStringBytes = new byte[stringBytes.length + len];
            System.arraycopy(stringBytes, 0, tempStringBytes, 0, stringBytes.length);
            System.arraycopy(bytes, 0, tempStringBytes, stringBytes.length, len);
            stringBytes = tempStringBytes;
        }
        return new String(stringBytes, "UTF-8");
    }

    private static List<String> getPreList(String html,boolean filterEnter) {
        List<String> codeList = new ArrayList<String>();
        char[] chars = html.toCharArray();
        StringBuffer stringBuffer = new StringBuffer();
        boolean swich = false;
        for (int i = 0; i < chars.length; i++) {
            if ((i + 4) < (chars.length - 1) && chars[i] == '<' && chars[i + 1] == 'p' && chars[i + 2] == 'r' && chars[i + 3] == 'e' && chars[i + 4] == '>' ) {
                swich = true;
            }
            if ((i + 5) < (chars.length - 1) && chars[i] == '<' && chars[i + 1] == '/' && chars[i + 2] == 'p' && chars[i + 3] == 'r' && chars[i + 4] == 'e' && chars[i + 5] == '>' ) {
                swich = false;
            }
            if (swich) {
                //过滤显示换行
                if (filterEnter && chars[i] == '\\' && chars[i+1] == 'n') {
                    i++;
                } else {
                    stringBuffer.append(chars[i]);
                }
            } else if (stringBuffer.length() > 0) {
                stringBuffer.append("</pre>");
                i += 5;
                codeList.add(stringBuffer.toString());
                stringBuffer = new StringBuffer();
            }
        }
        return codeList;
    }

    private static void appendText(Element element, StringBuffer stringBuffer) {
        List<Node> nodes = element.childNodes();
        if (nodes != null && nodes.size() > 0) {
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if (node instanceof TextNode) {
                    String text = ((TextNode) node).text();
                    if (text.trim().length() > 0) {
                        stringBuffer.append(text);
                    }
                } else {
                    Element child = (Element) node;
                    appendText(child, stringBuffer);
                }
            }
        }
    }

}
