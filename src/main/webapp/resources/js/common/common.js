function isEmptyHtml(content) {
    return !content || !$.trim(content) || content.match(/^<p>[&nbsp;| ]*<\/p>$/);
}

function counter(data) {
	$.ajax({
		url:contextPath + "/counter.do",
		type:"POST",
		data:data
	});
}

function isLogin() {
	var isLogin;
	$.ajax({
		url: contextPath + "/isLogin.do",
		type: "GET",
		async: false,
		success: function(result) {
			isLogin = result;
		}
	});
	return isLogin && isLogin == 'true';
}

function searchArticles(name, value) {
	var url = contextPath + '/blog/article_list.ftl?current=1';
	url = url + '&' + name + '=' + encodeURI(value);
	window.location.href=url;
}

/*
 * 滑动到指定元素
 */
function scrollTo(id){
    $('html, body').animate({scrollTop: $(id).offset().top},"fast");
}

/*
 * 取消回复
 */
function cancelReply(){
	$('#reply_div').html('');
	$("#reference_commenter_input").val('');
	$("#reference_comment_id_input").val('');
}

/*
 * 回复评论
 */
function reply(id) {
	var html = '<span>回复['+ $("#commenter_a_" + id).html() +']</span><a id="cancel_reply_button" href="javascript:void(0)">取消回复</a>';
	$("#reply_div").html(html);
	$("#reference_comment_id_input").val(id);
	$("#reference_commenter_input").val($("#commenter_a_" + id).html());
	scrollTo("#reply_div");
}

/*
 * 引用评论
 */
function quote(id) {
	reply(id);
	var content = generateQuote(id);
	tinymce.activeEditor.insertContent(content);
	scrollTo("#reply_div");
}

function generateQuote(id) {
	var content = '<blockquote>' + $("#comment_content_" + id).html() + '</blockquote><br/>';
	return content;
}

/*
 * 评价评论
 */
function comment_remark(data){
	var result = null;
	$.ajax({
    	url:contextPath + "/commentRemark.do",
    	async: false,
    	type:"POST",
        data:data,
        success:function(data){
        	result = data;
        	if(data && data == 'exists') {
        		alert("您已经评价过啦，亲！");
        	}
        }
    });
	if(!result || result != 'success') {
		return;
	}
	if(data.column == 'good_times') {
		var times = parseInt($("#comment_good_span_"+data.commentId).html()) + 1;
		$("#comment_good_span_"+data.commentId).html(times);
	}
	if(data.column == 'bad_times') {
		var times = parseInt($("#comment_bad_span_"+data.commentId).html()) + 1;
		$("#comment_bad_span_"+data.commentId).html(times);
	}
}

/*
 * 评价文章
 */
function remark(){
	var articleId = $("#articleId").val();
	var checked = $("input[name=column]:checked").val();
	var result = null;
	$.ajax({
    	url:contextPath + "/counter.do",
    	async: false,
    	type:"POST",
        data:{"type":1,"articleId":articleId,"column":checked},
        success:function(data){
        	result = data;
        	if(data && data == 'exists') {
        		alert("您已经评价过啦，亲！");
        	}
        }
    });
	if(!result || result != 'success') {
		return;
	}
	var max = 10;
	$("input[name=column]").each(function(){
		var times = parseInt($("#" + $(this).val() + "_span").html());
		if($(this).val() == checked ) {
			times = times + 1;
			$("#" + $(this).val() + "_span").html(times);
		}
		if (times > max) {
            max = times;
        }
	});
	$("input[name=column]").each(function(){
		var times = parseInt($("#" + $(this).val() + "_span").html());
		$("#" + $(this).val() + "_img").attr("height",times * 50 / max);
	});
}

/*
 * 评论
 */
function comment(url,data) {
	$.ajax({
		url:contextPath + url,
		type:"POST",
		data:data,
		success:function(data) {
            if (data && data == 'empty') {
                alert("评论不能为空呀，亲！")
            } else if (data && data.success) {
				var size = parseInt($("#comment_size").html()) + 1;
				var id = data.id;
				$("#comment_size").html(size);
				if(size == 1) {
					$("#comment_list").html('');
				}
				appendComment(id,data.content,size);
				tinymce.activeEditor.setContent('');
				cancelReply();
			} else {
				alert(data);
			}
		}
	});
}

/*
 * 追加评论
 */
function appendComment(id,content,size){
	var html = '<div id="comment_div_' + id + '" class="feedbackItem">';
	html = html + '<div class="feedbackListSubtitle">';
	html = html + '<a href="javascript:void(0)" class="layer">#' + size + '楼</a>  <span class="comment_date">刚刚</span> ';
	html = html + '</div>';
	html = html + '<div class="feedbackCon">';
	html = html + '<div class="blog_comment_body">' + content + '</div>';
	html = html + '</div>';
	html = html + '</div>';
	$("#comment_list").append(html);
}
