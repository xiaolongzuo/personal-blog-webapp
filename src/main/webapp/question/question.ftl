<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/common/article.css" rel="stylesheet"/>
<link href="${contextPath}/resources/css/common/code.css" rel="stylesheet"/>
<script type="text/javascript">
	$(document).ready(function() {
        counter({"type":2,"questionId":$("#questionId").val()});
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
                "type":2,
                "questionId":$("#questionId").val(),
                "commentId":$(this).attr("comment_id"),
                "column":$(this).attr("column")
            });
        });
        $("body").on("click","#cancel_reply_button",function(){
            cancelReply();
        });
        $("#submit_comment_button").click(function(){
            var content = tinymce.activeEditor.getContent();
            if(!content || !$.trim(content)) {
                alert("评论不能为空啊，亲！");
                return false;
            }
            comment("/answer.do",{
                "questionId":$("#questionId").val(),
                "content":content,
                "referenceCommentId":$("#reference_comment_id_input").val(),
                "referenceCommenter":$("#reference_commenter_input").val()
            });
        });
	});
</script>
</head>
<body>
<input id="questionId" type="hidden" name="questionId" value="${question.id}"/>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
		<#include "question_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>
