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

import com.zuoxiaolong.config.Configuration;
import com.zuoxiaolong.dao.*;
import com.zuoxiaolong.model.Status;
import com.zuoxiaolong.orm.DaoFactory;
import com.zuoxiaolong.util.EnrypyUtil;
import com.zuoxiaolong.util.IOUtil;
import com.zuoxiaolong.util.ImageUtil;
import com.zuoxiaolong.util.JsoupUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
	
	private static final String loginUrl = "http://passport.cnblogs.com/user/signin";

	private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCp0wHYbg/NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0/nhzCzBK8ZC9eCWqd0aHbdgOQT6CuFQBMjbyGYvlVYU2ZP7kG9Ft6YV6oc9ambuO7nPZh+bvXH0zDKfi02prknrScAKC0XhadTHT3Al0QIDAQAB";
	
	private static final String username = "左潇龙";

	public static void fetchArticlesAfterLogin() throws IOException {
		String cookie;
		try {
			cookie = login();
		} catch (Exception e) {
			logger.error("login failed ...", e);
			return;
		}
        for (int i = 1; ; i++) {
        	if (logger.isInfoEnabled()) {
        		logger.info("begin fetch the " + i + " page...");
			}
            Document document = Jsoup.parse(getHtmlUseCookie(cookie, "http://i.cnblogs.com/posts?page=" + i));
            Element mainElement = document.getElementById("post_list");
            Elements elements = mainElement.getElementsByTag("tr");
            int pageSize = 0;
            for (int j = 1 ; j < elements.size(); j++) {
                try {
                    fetchArticle(elements.get(j),cookie);
                    if (logger.isInfoEnabled()) {
                        logger.info("fetch success for pagenumber : " + i + ", pagesize : " + (pageSize + 1));
                    }
                } catch (Throwable e) {
                    logger.error("fetch failed for pagenumber : " + i + ", pagesize : " + (pageSize + 1), e);
                }
                pageSize++;
            }
            if (pageSize < 10) {
				break;
			}
        }
    }
	
	private static String login() throws Exception{
		HttpURLConnection loginPageConnection = (HttpURLConnection) new URL(loginUrl).openConnection();
        loginPageConnection.setRequestProperty("Connection","keep-alive");
        loginPageConnection.setRequestMethod("GET");
        loginPageConnection.connect();
        String body = IOUtil.read(loginPageConnection.getInputStream());
        String cookie = loginPageConnection.getHeaderField("Set-Cookie");
        Pattern pattern = Pattern.compile("'VerificationToken':\\s*?'(.*?)'");
        Matcher matcher = pattern.matcher(body);
        String token = null;
        if (matcher.find()) {
            token = matcher.group(1);
        }

        HttpURLConnection loginConnection = (HttpURLConnection) new URL(loginUrl).openConnection();
        loginConnection.setDoInput(true);
        loginConnection.setDoOutput(true);
        loginConnection.setRequestMethod("POST");
        loginConnection.setRequestProperty("Connection", "keep-alive");
        loginConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        loginConnection.setRequestProperty("VerificationToken",token);
        loginConnection.setRequestProperty("Cookie",cookie);
        loginConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");
        loginConnection.setRequestProperty("Referer","http://passport.cnblogs.com/user/signin?ReturnUrl=http%3A%2F%2Fhome.cnblogs.com%2Fu%2Fzuoxiaolong%2F");
        loginConnection.setRequestProperty("X-Requested-With","XMLHttpRequest");
        loginConnection.connect();
        OutputStream outputStream = loginConnection.getOutputStream();
        Map<String,Object> params = new HashMap<>();
        String username = Configuration.get("cnblogs.username.product");
        String password = Configuration.get("cnblogs.password.product");
        params.put("input1",EnrypyUtil.publicEnrypy(publicKey, username));
        params.put("input2",EnrypyUtil.publicEnrypy(publicKey, password));
        params.put("remember", false);
        outputStream.write(JSONObject.fromObject(params).toString().getBytes("UTF-8"));
        outputStream.flush();
        String json = IOUtil.read(loginConnection.getInputStream());
        if (JSONObject.fromObject(json).containsKey("success") && JSONObject.fromObject(json).getBoolean("success")) {
        	List<String> cooks = loginConnection.getHeaderFields().get("Set-Cookie");
            return cooks.get(0) + ";" + cooks.get(1);
		} else {
			throw new RuntimeException("login failed ...");
		}
	}
	
	private static void fetchArticle(Element element,String cookie) throws IOException {
		Elements tdElements = element.getElementsByTag("td");
		Element subjectTdElement = tdElements.get(0);
		Element subjectElement = subjectTdElement.getElementsByTag("a").first();
        String articleUrl = subjectElement.attr("href");
        
        String originArticleHtml = getHtmlUseCookie(cookie, articleUrl);
        Document articleDocument = Jsoup.parse(originArticleHtml);
        
        Map<String, String> imageMap = saveImage(articleDocument);
        
        //获取标题
        String subject = subjectElement.html().trim();
        
        //获取postid
        String resourceId = element.attr("id").split("\\-")[2];
        Integer postId = Integer.valueOf(resourceId);
        
        //获取内容
        Element bodyElement = articleDocument.getElementById("cnblogs_post_body");
        if (bodyElement == null) {
            logger.warn("can't get article html , url : " + articleUrl);
            return;
        }
        String html = bodyElement.html();
        List<String> codeList = getPreList(html, false);
        List<String> originCodeList = getPreList(originArticleHtml, true);
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
        List<Node> subjectNodes = subjectTdElement.childNodes();
        String createDateText = ((TextNode)subjectNodes.get(subjectNodes.size() - 1)).text().trim();
        Pattern pattern = Pattern.compile("（(.*?)）");
        Matcher matcher = pattern.matcher(createDateText);
        String createDate = null;
        if (matcher.find()) {
        	createDate = matcher.group(1) + ":00";
		} else {
			logger.error("get create_date failed for " + postId);
			return;
		}
        Status status = null;
        TextNode statusNode = (TextNode) tdElements.get(1).childNodes().get(0);
        String statusNodeText = statusNode.text().trim();
        if (statusNodeText.length() == 0) {
        	Element statusElement = (Element) tdElements.get(1).childNodes().get(1);
			status = statusElement.text().trim().equals("未发布") ? Status.draft : Status.published;
		} else {
			status = statusNodeText.equals("未发布") ? Status.draft : Status.published;
		}
        Integer accessTimes = Integer.valueOf(tdElements.get(3).html().trim());

        //获取赞的次数
        Integer goodTimes = 0;
        if (status == Status.published) {
        	Document diggCountDocument = Jsoup.connect("http://www.cnblogs.com/mvc/blog/BlogPostInfo.aspx?blogId=160491&postId=" + postId + "&blogApp=zuoxiaolong&blogUserGuid=8834a931-b305-e311-8d02-90b11c0b17d6").get();
            goodTimes = Integer.valueOf(diggCountDocument.getElementById("digg_count").html());
		}

        //如果resourceId已经存在则更新，否则保存
		Integer id = DaoFactory.getDao(ArticleDao.class).saveOrUpdate(resourceId, subject, createDate, status, username, accessTimes, goodTimes, html, content);
        if (logger.isInfoEnabled()) {
    		logger.info("saveOrUpdate article : [" + resourceId + ":" + subject + "]");
		}

        //保存标签和分类
        String tagsUrl = "http://www.cnblogs.com/mvc/blog/CategoriesTags.aspx?blogApp=zuoxiaolong&blogId=160491&postId=" + postId;
        String tagsJson = getHtmlUseCookie(cookie, tagsUrl);
        saveTagAndCategory(id, tagsJson);
        if (logger.isInfoEnabled()) {
    		logger.info("save article tag and category: [" + tagsJson + "]");
		}

        //保存评论
        saveComment(id, postId, cookie);
	}

    private static String getHtmlUseCookie(String cookie, String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Cookie",cookie);
        return IOUtil.read(httpURLConnection.getInputStream());
    }

    private static void saveTagAndCategory(Integer id,String json) {
        JSONObject tagsJsonObject = JSONObject.fromObject(json);
        String[] tags = new String[0];
        if (tagsJsonObject.getString("Tags").split("标签:").length > 1) {
            tags = tagsJsonObject.getString("Tags").split("标签:")[1].trim().split(",");
        }
        String[] categories = new String[0];
        if (tagsJsonObject.getString("Categories").split("分类:").length > 1) {
            categories = tagsJsonObject.getString("Categories").split("分类:")[1].trim().split(",");
        }
        for (int i = 0; i < tags.length; i++) {
            String tag = Jsoup.parseBodyFragment(tags[i]).text().trim();
            Integer tagId = DaoFactory.getDao(TagDao.class).getId(tag);
            if (tagId == null) {
                tagId = DaoFactory.getDao(TagDao.class).save(tag);
            }
            if (!DaoFactory.getDao(ArticleTagDao.class).exsits(id, tagId)) {
                DaoFactory.getDao(ArticleTagDao.class).save(id, tagId);
            }
        }
        for (int i = 0; i < categories.length; i++) {
            String category = Jsoup.parseBodyFragment(categories[i]).text().trim();
            Integer categoryId = DaoFactory.getDao(CategoryDao.class).getId(category);
            if (categoryId == null) {
                categoryId = DaoFactory.getDao(CategoryDao.class).save(category);
            }
            if (!DaoFactory.getDao(ArticleCategoryDao.class).exsits(id, categoryId)) {
                DaoFactory.getDao(ArticleCategoryDao.class).save(id, categoryId);
            }
        }
    }
    
    private static void saveComment(Integer id, Integer postId, String cookie) throws IOException {
    	String commentsUrl = "http://www.cnblogs.com/mvc/blog/GetComments.aspx?blogApp=zuoxiaolong&postId=" + postId + "&pageIndex=";
        for (int i = 1 ; ; i++ ) {
            String currentCommentUrl = commentsUrl + i;
            String commentsJson = null;
            if (cookie != null) {
				commentsJson = getHtmlUseCookie(cookie, currentCommentUrl);
			} else {
				commentsJson = getArticleHtml(currentCommentUrl);
			}
            Document commentsDocument = Jsoup.parseBodyFragment(JSONObject.fromObject(commentsJson).getString("commentsHtml"));
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
                String badTimesString = commentElement.getElementsByClass("comment_bury").first().text().trim();
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
        Integer accessTimes = Integer.valueOf(attrs[5].substring(attrs[5].indexOf("(") + 1, attrs[5].length() - 1));

        //获取赞的次数
        Document diggCountDocument = Jsoup.connect("http://www.cnblogs.com/mvc/blog/BlogPostInfo.aspx?blogId=160491&postId=" + postId + "&blogApp=zuoxiaolong&blogUserGuid=8834a931-b305-e311-8d02-90b11c0b17d6").get();
        Integer goodTimes = Integer.valueOf(diggCountDocument.getElementById("digg_count").html());

        Status status = Status.published;
        //如果resourceId已经存在则更新，否则保存
		Integer id = DaoFactory.getDao(ArticleDao.class).saveOrUpdate(resourceId, subject, createDate, status, username, accessTimes, goodTimes, html, content);
        if (logger.isInfoEnabled()) {
    		logger.info("saveOrUpdate article : [" + resourceId + ":" + subject + "]");
		}
        
        //保存标签和分类
        String tagsUrl = "http://www.cnblogs.com/mvc/blog/CategoriesTags.aspx?blogApp=zuoxiaolong&blogId=160491&postId=" + postId;
        String tagsJson = getArticleHtml(tagsUrl);
        saveTagAndCategory(id, tagsJson);
        if (logger.isInfoEnabled()) {
    		logger.info("save article tag and category: [" + tagsJson + "]");
		}

        //保存评论
        saveComment(id, postId, null);
	}
	
    private static String getArticleHtml(String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod("GET");
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
