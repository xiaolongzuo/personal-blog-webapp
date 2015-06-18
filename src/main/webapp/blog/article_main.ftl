    <!-- 主题内容模块 -->
    <div class="index_about">
        <h2 class="c_titile">${article.subject}</h2>

        <p class="box_c"><span class="d_time">发布时间：${article.create_date}</span><span>作者：<a
                href="#">${article.username}</a></span><span>阅读（${article.access_times}
            ）</span><span>评论（${article.comment_times}）</span></p>
        <ul class="infos">
        ${article.html} <br/>
        <#include "remark.ftl">
        </ul>
        <#if tags?? && tags?size gt 0 >
            <div class="article_tag_div">
                <p>关键字：

                    <#list tags as tag>
                        <a onclick="javascript:searchArticles('tag','${tag.tag_name}')" href="javascript:void(0)" >${tag.tag_name}</a>
                    </#list>
                </p>
            </div>
        </#if>
        <#if categories?? && categories?size gt 0 >
            <div class="article_category_div">
                <p>分类：
                    <#list categories as category>
                        <a onclick="javascript:searchArticles('category','${category.category_name}')" href="javascript:void(0)" >${category.category_name}</a>
                    </#list>
                </p>
            </div>
        </#if>
        <#if nextArticle?? || preArticle?? >
            <div class="nextinfo">
                <#if preArticle??>
                    <p>上一篇：<a href="${contextPath}${preArticle.url}">${preArticle.subject}</a></p>
                </#if>
                <#if nextArticle??>
                    <p>下一篇：<a href="${contextPath}${nextArticle.url}">${nextArticle.subject}</a></p>
                </#if>
            </div>
        </#if>
        <#if relatedArticles?? && relatedArticles?size gt 0>
            <div class="otherlink">
                <h2>相关文章</h2>
                <ul>
                    <#list relatedArticles as article>
                        <li><a href="${contextPath}${article.url}" title="${article.subject}">${article.subject}</a></li>
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
			<div id="comment_div_${comment.id}" class="feedbackItem">
                <div class="feedbackListSubtitle">
                	<div class="feedbackManage">
					<span class="comment_actions">
						<a comment_id="${comment.id}" class="reply_button" href="javascript:void(0)">回复</a>
						<a comment_id="${comment.id}" class="quite_button" href="javascript:void(0)">引用</a>
					</span>
					</div>
                    <a href="javascript:void(0)" class="layer">#${comment_index + 1}楼</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    时间：<span class="comment_date">${comment.create_date}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    来源：<a id="commenter_a_${comment.id}" href="javascript:void(0)">${comment.commenter}</a>
                </div>
                <div class="feedbackCon">
                    <div id="comment_content_${comment.id}" class="blog_comment_body">${comment.content}</div>
					<div class="comment_vote">
						<a class="comment_remark_button" comment_id="${comment.id}" column="good_times" href="javascript:void(0)">支持(<span id="comment_good_span_${comment.id}">${comment.good_times?default(0)}</span>)</a>
						<a class="comment_remark_button" comment_id="${comment.id}" column="bad_times" href="javascript:void(0)">反对(<span id="comment_bad_span_${comment.id}">${comment.bad_times?default(0)}</span>)</a>
					</div>
                </div>
            </div>
            </#list>
        <#else>
            暂无评论
        </#if>
        </div>
		<script type="application/javascript">
			function getTinymceSize() {
				return {width:700,height:150};
			}
			function getTinymceSkin() {
				return 'comment';
			}
		</script>
        <!-- 提交评论 -->
        <div id="comment_container">
            <div id="comment_title">发表评论</div>
            <div class="commentbox_main">
            	<div id="reply_div" class="clear"></div>
				<input type="hidden" name="referenceCommentId" id="reference_comment_id_input"/>
				<input type="hidden" name="referenceCommenter" id="reference_commenter_input"/>
                <textarea name="content" id="comment_textarea" class="html_editor"></textarea>
            </div>
            <p id="commentbox_opt">
                <input id="submit_comment_button" class="comment_btn" value="提交评论" type="button">
            </p>
        </div>
    </div>
