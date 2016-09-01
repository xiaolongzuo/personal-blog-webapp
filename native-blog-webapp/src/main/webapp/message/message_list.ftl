<!DOCTYPE html>
<html>
<head>
    <#include "../common/meta.ftl">
    <#include "../common/head.ftl">
    <link href="${contextPath}/resources/css/common/article.css" rel="stylesheet"/>
    <link href="${contextPath}/resources/css/common/code.css" rel="stylesheet"/>
</head>
<body>
<#include "../common/header.ftl">
<article>
    <div class="left_box float_left">
        <!-- 主题内容模块 -->
        <div class="index_about">
            <div id="comments_pager_top"></div>
            <!-- 评论列表 -->
            <div class="feedback_area_title">留言板（共<span id="comment_size">${messages?size}</span>条留言）</div>
            <div class="feedbackNoItems"></div>
            <div class="feedbackItem" id="comment_list">
            <#if messages?? && messages?size gt 0 >
                <#list messages as message>
                    <div id="comment_div_${message.id}" class="feedbackItem">
                        <div class="feedbackListSubtitle">
                            <div class="feedbackManage">
					<span class="comment_actions">
						<a comment_id="${message.id}" class="reply_button" href="javascript:void(0)">回复</a>
						<a comment_id="${message.id}" class="quite_button" href="javascript:void(0)">引用</a>
					</span>
                            </div>
                            <a href="javascript:void(0)" class="layer">#${messages?size - message_index}楼</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            时间：<span class="comment_date">${message.create_date}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            来源：<a id="commenter_a_${message.id}" href="javascript:void(0)">${message.commenter}</a>
                        </div>
                        <div class="feedbackCon">
                            <div id="comment_content_${message.id}" class="blog_comment_body">${message.message}</div>
                        </div>
                    </div>
                </#list>
            <#else>
                暂无留言
            </#if>
            </div>
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
    </div>
    <div class="right_box float_right">
    <#include "right.ftl">
    </div>
</article>
<#include "../common/footer.ftl">
<#include "../common/bottom.ftl">
<script type="text/javascript">
    tinymceInit({width:700,height:150,skin:'comment'});
    $(document).ready(function() {
        $("body").on("click",".content_reply_a",function(){
            scrollTo("#comment_div_"+$(this).attr("reference_comment_id"));
        });
        $(".reply_button").click(function(){
            reply($(this).attr("comment_id"));
        });
        $(".quite_button").click(function(){
            quote($(this).attr("comment_id"));
        });
        $("body").on("click","#cancel_reply_button",function(){
            cancelReply();
        });
        $("#submit_comment_button").click(function(){
            var content = tinymce.activeEditor.getContent();
            if(content.length > 1000) {
                alert("留言长度不能大于1000啊，亲！");
                return false;
            }
            comment("/message.do",{
                "content":content,
                "referenceCommentId":$("#reference_comment_id_input").val(),
                "referenceCommenter":$("#reference_commenter_input").val()
            });
        });
    });
</script>
</body>
</html>

