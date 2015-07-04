<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<script type="text/javascript">
var settings = {width:900,height:400,content:''};
<#if record?? && record.escapeHtml??>
	settings.content = '${record.escapeHtml}';
</#if>
tinymceInit(settings);
$(document).ready(function(){
	$("#submitButton").click(function(){
		$.ajax({
			url:"${contextPath}/admin/updateRecord.do",
			data:{"id":$("input[name=id]").val(),"content":tinymce.activeEditor.getContent(),"title":$("input[name=title]").val()},
			type:"POST",
			success:function(data){
				if(data && data == 'success') {
					alert("保存成功");
					window.location.href="${contextPath}/admin/record_manager.ftl"
				} else {
					alert("保存失败");
				}
			}
		});
	});
});
</script>
</head>
<body>
<#include "../common/header.ftl">
<article>
	<input type="hidden" name="id" <#if record??>value="${record.id?default('')}"</#if>/>
	<table>
		<tr>
			<td>标题</td>
			<td>
				<input style="width:500px;" type="text" class="text_input" name="title" <#if record??>value="${record.title?default('')}"</#if>/>
			</td>
		</tr>
		<tr>
			<td>内容</td>
			<td>
				<textarea class="html_editor" style="width:100%"></textarea>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<input class="button" style="margin:10px 0px;" id="submitButton" type="button" value="保存"/>
			</td>
		</tr>
	<table>
</article>
<#include "../common/footer.ftl">
</body>
</html>