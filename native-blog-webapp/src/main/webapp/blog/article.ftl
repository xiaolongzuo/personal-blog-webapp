<!DOCTYPE html>
<html>
<head>
<#assign metaTitle="${article.subject}" />
<#if article.type == "1">
    <#assign metaKeywords="异能程序员,程序员小说,小说" />
<#else>
    <#assign metaKeywords="左潇龙,技术文章,技术博客" />
</#if>
<#assign metaDescription="${article.summary}" />
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/common/article.css" rel="stylesheet"/>
<link href="${contextPath}/resources/css/common/code.css" rel="stylesheet"/>
</head>
<body>
<input id="articleId" type="hidden" name="articleId" value="${article.id}"/>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
		<#include "article_main.ftl">
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
        counter({"articleId":$("#articleId").val(),"type":1,"column":"access_times"});
        $("body").on("click",".content_reply_a",function(){
            scrollTo("#comment_div_"+$(this).attr("reference_comment_id"));
        });
        $(".reply_button").click(function(){
            reply($(this).attr("comment_id"));
        });
        $(".quite_button").click(function(){
            quote($(this).attr("comment_id"));
        });
        $(".comment_remark_button").click(function(){
            comment_remark({
                "type":1,
                "articleId":$("#articleId").val(),
                "commentId":$(this).attr("comment_id"),
                "column":$(this).attr("column")
            });
        });
        $("body").on("click","#cancel_reply_button",function(){
            cancelReply();
        });
        $("input[name=column]").click(function(){
            remark();
        });
        $("#submit_comment_button").click(function(){
            var content = tinymce.activeEditor.getContent();
            comment("/comment.do",$(this),{
                "articleId":$("#articleId").val(),
                "content":content,
                "referenceCommentId":$("#reference_comment_id_input").val(),
                "referenceCommenter":$("#reference_commenter_input").val()
            });
        });
    });
</script>
</body>
</html>
