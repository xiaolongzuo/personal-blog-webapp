package com.zuoxiaolong.fetch;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;


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
 * @since 2015年5月10日 上午11:55:49
 */
public class FetchFromMyCnblogs {

	public static void main(String[] args) throws IOException {
		FileWriter fileWriter = new FileWriter("data.sql");
		for (int i = 1; i < 15; i++) {
			Document document = Jsoup.connect("http://www.cnblogs.com/zuoxiaolong/default.html?page=" + i).get();
			Element mainElement = document.getElementById("mainContent");
			Elements elements = mainElement.getElementsByClass("postTitle");
			int pageSize = 0;
			for (Element element : elements) {
				Element subjectElement = element.getElementsByTag("a").first();
				String articleUrl = subjectElement.attr("href");
				String subject = subjectElement.html().trim();
				Document acticleDocument = Jsoup.connect(articleUrl).get();
				Element bodyElement = acticleDocument.getElementById("cnblogs_post_body");
				String html = bodyElement.html().replace("'", "\"");
				StringBuffer stringBuffer = new StringBuffer();
				appendText(bodyElement, stringBuffer);
				String content = stringBuffer.toString().replace("'", "\"");
				//获取文章一些基本属性时，使用文章索引里面的postdesc获取
				Element postDescAElement = element.nextElementSibling().nextElementSibling().nextElementSibling();
				String[] attrs  = postDescAElement.text().split("\\s");
				String createDate = attrs[2] + " " + attrs[3] + ":00";
				String username = attrs[4];
				Integer accessTimes = Integer.valueOf(attrs[5].substring(attrs[5].indexOf("(") + 1,attrs[5].length()-1));
				//获取postid时使用文章页面里的postdesc获取
				Element postIdElement = null;
				Elements postDescAElements = acticleDocument.getElementsByClass("postDesc").first().getElementsByTag("a");
				for (Element aElement : postDescAElements) {
					postIdElement = aElement;
					if (aElement.html().trim().equals("编辑")) {
						break;
					}
				}
				Integer postId = Integer.valueOf(postIdElement.attr("href").substring(postIdElement.attr("href").indexOf("=") + 1));
				Document diggCountDocument = Jsoup.connect("http://www.cnblogs.com/mvc/blog/BlogPostInfo.aspx?blogId=160491&postId=" + postId + "&blogApp=zuoxiaolong&blogUserGuid=8834a931-b305-e311-8d02-90b11c0b17d6").get();
				Integer goodTimes = Integer.valueOf(diggCountDocument.getElementById("digg_count").html());
				fileWriter.write(generateSql(subject, createDate, username, accessTimes, goodTimes, html,content));
				pageSize ++ ;
			}
			System.out.println("第" + i + "页：" + pageSize);
			pageSize = 0;
		}
		fileWriter.flush();
		fileWriter.close();
	}
	
	private static void appendText(Element element,StringBuffer stringBuffer) {
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
					appendText(child,stringBuffer);
				}
			}
		}
	}
	
	private static String generateSql(String subject,String createDate,String username,Integer accessTimes,Integer goodTimes,String html,String content){
		String sql = "insert into articles (username,icon,create_date," +
						"access_times,good_times,subject,html,content) values (\"" + username;
		sql += "\",\"resources/img/article.jpg\",\"" + createDate + "\"," + accessTimes + "," + goodTimes + ","; 
		sql += "\"" + subject + "\",'" + html + "','" + content + "');\r\n";  
		return sql;
	}
	
}

