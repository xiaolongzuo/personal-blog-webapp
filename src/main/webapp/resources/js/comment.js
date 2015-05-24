$(document).ready(function() {
	$("#btn_comment_submit").click(function(){
		var comment = $("#tbCommentBody").val();
		if(!comment || !$.trim(comment)) {
			alert("评论不能为空啊，亲！");
			return false;
		}
		$.ajax({
			url:contextPath + "/comment.do",
			type:"POST",
			data:{"articleId":$("#articleId").val(),"content":comment}
		});
		var size = parseInt($("#comment_size").html()) + 1;
		$("#comment_size").html(size);
		$("#comment_list").append('<div class="feedbackListSubtitle">');
		$("#comment_list").append('<a href="#" class="layer">#' + size + '楼</a>  <span class="comment_date">刚刚</span> ');
		$("#comment_list").append('</div><div class="feedbackCon">');
		$("#comment_list").append('<div class="blog_comment_body">' + $("#tbCommentBody").val() + '</div></div>');
		$("#tbCommentBody").val('');
	});
});