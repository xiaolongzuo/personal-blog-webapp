<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/common/article.css" rel="stylesheet"/>
<link href="${contextPath}/resources/css/common/code.css" rel="stylesheet"/>
<script type="text/javascript">
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
            if(isEmptyHtml(content)) {
                alert("评论不能为空啊，亲！");
                return false;
            }
			comment("/comment.do",{
				"articleId":$("#articleId").val(),
				"content":content,
				"referenceCommentId":$("#reference_comment_id_input").val(),
				"referenceCommenter":$("#reference_commenter_input").val()
			});
		});
	});
</script>
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
</body>
</html>
