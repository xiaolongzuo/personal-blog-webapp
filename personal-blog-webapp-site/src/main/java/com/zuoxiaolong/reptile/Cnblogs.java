package com.zuoxiaolong.reptile;

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

import com.zuoxiaolong.blog.client.ArticleTagDubboService;
import com.zuoxiaolong.blog.client.TagDubboService;
import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.*;
import com.zuoxiaolong.dubbo.DubboClientFactory;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.model.Type;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.IOUtil;
import com.zuoxiaolong.util.ImageUtil;
import com.zuoxiaolong.util.JsoupUtil;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 左潇龙
 * @since 2015年5月12日 下午9:24:21
 */
public abstract class Cnblogs {
	
	private static final Logger logger = Logger.getLogger(Cnblogs.class);
	
	private static final String article_url = "http://www.cnblogs.com/zuoxiaolong/default.html";
	private static final String digg_url = "http://www.cnblogs.com/zuoxiaolong/ajax/BlogPostInfo.aspx";
	private static final String category_url = "https://www.cnblogs.com/zuoxiaolong/ajax/CategoriesTags.aspx";
	private static final String comment_url = "https://www.cnblogs.com/zuoxiaolong/ajax/GetComments.aspx";

    private static void saveTagAndCategory(Integer id,String tagsHtml) {
        Document document = Jsoup.parse(tagsHtml);
        Element tagElement = document.getElementById("EntryTag");
        if (tagElement != null) {
            Elements elements = tagElement.getElementsByTag("a");
            if (elements != null) {
                for (Element element : elements) {
                    String tag = element.text().trim();
                    Integer tagId = DubboClientFactory.getClient(TagDubboService.class).getId(tag);
                    if (tagId == null) {
                        tagId = DubboClientFactory.getClient(TagDubboService.class).save(tag);
                    }
                    if (!DubboClientFactory.getClient(ArticleTagDubboService.class).exsits(id, tagId)) {
                        DubboClientFactory.getClient(ArticleTagDubboService.class).save(id, tagId);
                    }
                }
            }
        }
        Element categoryElement = document.getElementById("BlogPostCategory");
        if (categoryElement != null) {
            Elements elements = categoryElement.getElementsByTag("a");
            if (elements != null) {
                for (Element element : elements) {
                    String category = element.text().trim();
                    Integer categoryId = DaoFactory.getDao(CategoryDao.class).getId(category);
                    if (categoryId == null) {
                        categoryId = DaoFactory.getDao(CategoryDao.class).save(category);
                    }
                    if (!DaoFactory.getDao(ArticleCategoryDao.class).exsits(id, categoryId)) {
                        DaoFactory.getDao(ArticleCategoryDao.class).save(id, categoryId);
                    }
                }
            }
        }
    }
    
    private static void saveComment(Integer id, Integer postId) throws IOException {
    	String commentsUrl = comment_url + "?blogApp=zuoxiaolong&postId=" + postId + "&pageIndex=";
        for (int i = 1 ; ; i++ ) {
            String currentCommentUrl = commentsUrl + i;
            String commentsHtml = getArticleHtml(currentCommentUrl);
            Document commentsDocument = Jsoup.parse(commentsHtml);
            Elements commentElements = commentsDocument.getElementsByClass("feedbackItem");
            for (Element commentElement : commentElements) {
                String commentResourceId = commentElement.getElementsByClass("layer").first().attr("href");
                if (commentResourceId.startsWith("#")) {
                    commentResourceId = commentResourceId.substring(1);
                }
                String nickName = commentElement.getElementById("a_comment_author_" + commentResourceId).text().trim();
                Date commentDate = null;
                try {
                    commentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(commentElement.getElementsByClass("comment_date").first().text().trim());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Element commentContentElement = commentElement.getElementById("comment_body_" + commentResourceId);
                Elements refCommentElements = commentContentElement.getElementsByTag("a");
                Integer refCommentId = null;
                Pattern onclickPattern = Pattern.compile(".*?\\((.*?)\\).*");
                for (Element refCommentElement : refCommentElements) {
                    if (refCommentElement.text().trim().equals("@") && refCommentElement.hasAttr("onclick")) {
                        String onclick = refCommentElement.attr("onclick");
                        Matcher onclickMatcher = onclickPattern.matcher(onclick);
                        if (onclickMatcher.find()) {
                        	refCommentId = Integer.valueOf(onclickMatcher.group(1).split(",")[2]);
                        }
                    }
                }
                String goodTimesString = commentElement.getElementsByClass("comment_digg").first().text().trim();
                Integer commentGoodTimes = 0;
                Matcher goodTimesMatcher = onclickPattern.matcher(goodTimesString);
                if (goodTimesMatcher.find()) {
                    commentGoodTimes = Integer.valueOf(goodTimesMatcher.group(1));
                }
                String badTimesString = commentElement.getElementsByClass("comment_burry").first().text().trim();
                Integer commentBadTimes = 0;
                Matcher badTimesMatcher = onclickPattern.matcher(badTimesString);
                if (badTimesMatcher.find()) {
                    commentBadTimes = Integer.valueOf(badTimesMatcher.group(1));
                }
                StringBuffer commentContent = new StringBuffer();
                List<Node> nodes = commentContentElement.childNodes();
                refCommentId = DaoFactory.getDao(CommentDao.class).getId(String.valueOf(refCommentId));
                boolean find = true;
                for (int j = 0 ; j < nodes.size(); j++) {
                	Node node = nodes.get(j);
                    if (find && node instanceof Element && ((Element)node).tagName().equals("a") && ((Element)node).text().trim().equals("@")) {
                        find = false;
                    	commentContent.append("<a class=\"content_reply_a\" href=\"javascript:void(0)\" reference_comment_id=\""+refCommentId+"\">@");
                    	commentContent.append(nodes.get(++j).toString() + "</a>");
                    } else {
                        commentContent.append(node.toString());
                    }
                }
                Integer commentId = DaoFactory.getDao(CommentDao.class).getId(commentResourceId);
                if (commentId != null) {
                    DaoFactory.getDao(CommentDao.class).updateContent(commentId, commentContent.toString());
					continue;
				}
            	commentId = DaoFactory.getDao(CommentDao.class).save(id, "127.0.0.1", commentDate, commentContent.toString(), null, nickName, commentResourceId, refCommentId);
            	if (commentGoodTimes > 0) {
                    DaoFactory.getDao(CommentDao.class).updateCount(commentId, "good_times", commentGoodTimes);
				}
                if (commentBadTimes > 0) {
                    DaoFactory.getDao(CommentDao.class).updateCount(commentId, "bad_times", commentBadTimes);
				}
            }
            if (commentElements.size() < 50) {
                break;
            }
        }
    }
    
    private static Map<String, String> saveImage(Document articleDocument) {
    	Elements elements = articleDocument.getElementsByTag("img");
    	Map<String, String> result = new HashMap<>();
    	for (Element element : elements) {
			String img = element.toString();
			String imgUrl = element.attr("src");
			String path = DaoFactory.getDao(ImageDao.class).getPath(imgUrl);
            if (path != null) {
                try {
                    File file = new File(Configuration.getContextPath(path));
                    if (!file.exists()) {
                        HttpURLConnection connection = (HttpURLConnection) new URL(imgUrl).openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(1000);
                        connection.setReadTimeout(5000);
                        path = ImageUtil.generatePath(imgUrl);
                        IOUtil.copy(connection.getInputStream(), new File(Configuration.getContextPath(path)));
                    }
                } catch (Exception e) {
                    logger.error("save image error for : " + imgUrl, e);
                }
                DaoFactory.getDao(ImageDao.class).update(path, imgUrl);
            } else {
				path = ImageUtil.generatePath(imgUrl);
				try {
					File file = new File(Configuration.getContextPath(path));
					if (!file.exists()) {
                        HttpURLConnection connection = (HttpURLConnection) new URL(imgUrl).openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(1000);
                        connection.setReadTimeout(5000);
                        IOUtil.copy(connection.getInputStream(), file);
					}
				} catch (Exception e) {
					logger.error("save image error for : " + imgUrl, e);
				}
                DaoFactory.getDao(ImageDao.class).save(path, imgUrl);
			}
            result.put(img, img.replace(imgUrl, Configuration.getSiteUrl(path)));
		}
    	return result;
    }
    
    public static void fetchArticlesCommon() throws IOException {
        for (int i = 1; ; i++) {
        	if (logger.isInfoEnabled()) {
        		logger.info("begin fetch the " + i + " page...");
			}
            Document document = Jsoup.connect(article_url + "?page=" + i).get();
            Element mainElement = document.getElementById("mainContent");
            Elements elements = mainElement.getElementsByClass("postTitle");
            int pageSize = 0;
            for (int j = 0; j < elements.size(); j++) {
                try {
                    fetchArticle(elements.get(j));
                    if (logger.isInfoEnabled()) {
                        logger.info("fetch success for pagenumber : " + i + ", pagesize : " + (pageSize + 1));
                    }
                } catch (Throwable e) {
                    logger.error("fetch failed for pagenumber : " + i + ", pagesize : " + (pageSize + 1), e);
                }
                pageSize++;
            }
            if (logger.isInfoEnabled()) {
        		logger.info("fetch success for pagenumber : " + i + ", pagesize : " + pageSize);
			}
            if (pageSize < 10) {
				break;
			}
        }
    }
	
	private static void fetchArticle(Element element) throws IOException {
        Element subjectElement = element.getElementsByTag("a").first();
        String articleUrl = subjectElement.attr("href");
        Document acticleDocument = Jsoup.connect(articleUrl).get();
        
        Map<String, String> imageMap = saveImage(acticleDocument);
        
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
        for (String img : imageMap.keySet()) {
			html = html.replace(img, imageMap.get(img));
		}

        //获取纯文本内容
        StringBuffer stringBuffer = new StringBuffer();
        JsoupUtil.appendText(bodyElement, stringBuffer);
        String content = stringBuffer.toString().replace("'", "\"");

        //获取文章基本属性，使用文章索引里面的postdesc获取
        Element postDescAElement = element.nextElementSibling().nextElementSibling().nextElementSibling();
        String[] attrs = postDescAElement.text().split("\\s");
        String createDate = attrs[2] + " " + attrs[3] + ":00";
        String username = attrs[4];
        Integer accessTimes = Integer.valueOf(attrs[6].substring(attrs[6].indexOf("(") + 1, attrs[6].length() - 1));

        //获取赞的次数
        Document diggCountDocument = Jsoup.connect(digg_url + "?blogId=160491&postId=" + postId + "&blogApp=zuoxiaolong&blogUserGuid=8834a931-b305-e311-8d02-90b11c0b17d6").get();
        Integer goodTimes = Integer.valueOf(diggCountDocument.getElementById("digg_count").html());
        Type articleType = Type.article;
        if (subject.startsWith("一个屌丝程序猿的人生") || subject.startsWith("［异能程序员］")) {
            articleType = Type.novel;
        }
        Status status = Status.published;
        //如果resourceId已经存在则更新，否则保存
		Integer id = DaoFactory.getDao(ArticleDao.class).saveOrUpdate(resourceId, subject, createDate, status, username, accessTimes, goodTimes, html, content, articleType);
        if (logger.isInfoEnabled()) {
    		logger.info("saveOrUpdate article : [" + resourceId + ":" + subject + "]");
		}
        
        //保存标签和分类
        String tagsUrl = category_url + "?blogApp=zuoxiaolong&blogId=160491&postId=" + postId;
        String tagsHtml = getArticleHtml(tagsUrl);
        saveTagAndCategory(id, tagsHtml);
        if (logger.isInfoEnabled()) {
    		logger.info("save article tag and category: [" + tagsHtml + "]");
		}

        //保存评论
        saveComment(id, postId);
	}
	
    private static String getArticleHtml(String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(1000);
        httpURLConnection.setReadTimeout(5000);
        return IOUtil.read(httpURLConnection.getInputStream());
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

    public static void main(String[] args) throws IOException {
		fetchArticlesCommon();
    }
    
}
