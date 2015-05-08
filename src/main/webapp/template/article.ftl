<!DOCTYPE html>
<html>
<head>
<#include "head.ftl">
<link href="resources/css/article.css" rel="stylesheet">
</head>
<body>
<#include "header.ftl">
<article>
    <div class="l_box f_l">
        <!-- 主题内容模块 -->
        <div class="index_about">
            <h2 class="c_titile">${article.subject}</h2>
            <p class="box_c"><span class="d_time">发布时间：${article.create_date}</span><span>作者：<a href="/">${article.username}</a></span><span>阅读（${article.access_times}）</span><span>评论（${article.comment_times}）</span></p>
            <ul class="infos">
                ${article.content} <br/>
                <#include "remark.ftl">
            </ul>
            <div class="keybq">
                <p><span>关键字</span>：个人博客,Java</p>
            </div>
            <#if nextArticle?? || preArticle?? >
                <div class="nextinfo">
                    <#if preArticle??>
                        <p>上一篇：<a href="article_${preArticle.id}.html">${preArticle.subject}</a></p>
                    </#if>
                    <#if nextArticle??>
                        <p>下一篇：<a href="article_${nextArticle.id}.html">${nextArticle.subject}</a></p>
                    </#if>
                </div>
            </#if>
            <#if relatedArticles?? && relatedArticles?size gt 0>
                <div class="otherlink">
                    <h2>相关文章</h2>
                    <ul>
                        <#list relatedArticles as article>
                            <li><a href="article_${article.id}.html" title="${article.subject}">${article.subject}</a></li>
                        </#list>
                    </ul>
                </div>
            </#if>
            <div id="comments_pager_top"></div>
            <!-- 评论列表 -->
            <div class="feedback_area_title">评论列表</div>
            <div class="feedbackNoItems"></div>
            <div class="feedbackItem">
                <#if comments??>
                    <#list comments as comment>
                        <div class="feedbackListSubtitle">
                            <a href="#" class="layer">#${comment_index + 1}楼</a><span class="comment_date">${comment.create_date}</span> <a href="#" >${comment.visitor}</a>
                        </div>
                        <div class="feedbackCon">
                            <div class="blog_comment_body">${comment.content}</div>
                        </div>
                    </#list>
                </#if>
                暂无评论
            </div>
            <div id="comments_pager_bottom"></div>

            <!-- 提交评论 -->
            <a name="commentform"></a>
            <div id="divCommentShow"></div>
            <div id="comment_form_container"><div id="commentform_title">发表评论</div>
                <div class="commentbox_main">
                    <div class="clear"></div>
                    <textarea id="tbCommentBody" class="comment_textarea" rows="20" cols="50"></textarea>
                </div>
                <p id="commentbox_opt">
                    <input id="btn_comment_submit" class="comment_btn" value="提交评论" type="button">
                </p>
            </div>
        </div>
    </div>
    <#include "right.ftl">
</article>
<#include "footer.ftl">
</body>
</html>