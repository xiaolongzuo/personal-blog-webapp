<div class="left_box float_left">
    <!-- 主题内容模块 -->
    <div class="index_about">
        <h2 class="c_titile">${article.subject}</h2>

        <p class="box_c"><span class="d_time">发布时间：${article.create_date}</span><span>作者：<a
                href="/">${article.username}</a></span><span>阅读（${article.access_times}
            ）</span><span>评论（${article.comment_times}）</span></p>
        <ul class="infos">
        ${article.html} <br/>
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
        <div class="feedback_area_title">评论列表（共<span id="comment_size">${comments?size}</span>条评论）</div>
        <div class="feedbackNoItems"></div>
        <div class="feedbackItem" id="comment_list">
        <#if comments?? && comments?size gt 0 >
            <#list comments as comment>
                <div class="feedbackListSubtitle">
                    <a href="#" class="layer">#${comment_index + 1}楼</a>&nbsp;&nbsp;&nbsp;&nbsp;时间：<span
                        class="comment_date">${comment.create_date}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;来源：<a
                        href="#">${comment.city}网友</a>
                </div>
                <div class="feedbackCon">
                    <div class="blog_comment_body">${comment.content}</div>
                </div>
            </#list>
        <#else>
            暂无评论
        </#if>
        </div>

        <!-- 提交评论 -->
        <div id="divCommentShow"></div>
        <div id="comment_form_container">
            <div id="commentform_title">发表评论</div>
            <div class="commentbox_main">
                <div class="clear"></div>
                <textarea name="content" id="tbCommentBody" class="comment_textarea" rows="20" cols="50"></textarea>
            </div>
            <p id="commentbox_opt">
                <input id="btn_comment_submit" class="comment_btn" value="提交评论" type="button">
            </p>
        </div>
    </div>
</div>