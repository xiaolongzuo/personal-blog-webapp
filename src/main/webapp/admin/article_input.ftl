<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<script type="text/javascript" src="${contextPath}/resources/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#submitButton").click(function(){
		$.ajax({
			url:"${contextPath}/admin/updateArticle.do",
			data:{
			"content":tinymce.activeEditor.getContent(),"subject":"测试文章","status":1,
			"icon":"http://localhost:8080/resources/img/article_09.jpg"},
			type:"POST",
			success:function(data){
			}
		});
	});
});
</script>
</head>
<body>
<#include "../common/header.ftl">
<article>
    <textarea class="html_editor" style="width:100%"></textarea>
    <input class="button" style="margin:10px 0px;" id="submitButton" type="button" value="保存"/>
</article>
<#include "../common/footer.ftl">
</body>
</html>