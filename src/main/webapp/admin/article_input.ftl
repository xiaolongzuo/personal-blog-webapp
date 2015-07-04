<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<script type="text/javascript">
var settings = {width:900,height:400,content:''};
<#if article?? && article.escapeHtml??>
	settings.content = '${article.escapeHtml}';
</#if>
tinymceInit(settings);
$(document).ready(function(){
	$("#submitButton").click(function(){
		var status = $("input[name=status]").is(":checked");
		if (status) {
			status = 1;
		} else {
			status = 0;
		}
		$.ajax({
			url:"${contextPath}/admin/updateArticle.do",
			data:{"id":$("input[name=id]").val(),"content":tinymce.activeEditor.getContent()
                ,"subject":$("input[name=subject]").val(),"status":status,"type":$("select[name=type]").val()},
			type:"POST",
			success:function(data){
				if(data && data == 'success') {
					alert("保存成功");
					window.location.href="${contextPath}/admin/article_manager.ftl"
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
	<input type="hidden" name="id" <#if article??>value="${article.id?default('')}"</#if>/>
	<table>
		<tr>
			<td>标题</td>
			<td>
				<input style="width:500px;" type="text" class="text_input" name="subject" <#if article??>value="${article.subject?default('')}"</#if>/>
			</td>
		</tr>
		<tr>
			<td>内容</td>
			<td>
				<textarea class="html_editor" style="width:100%"></textarea>
			</td>
		</tr>
		<tr>
			<td>是否发表</td>
			<td>
				<input type="checkbox" name="status" <#if article??><#if article.status?default('0') == '1'>checked="checked"</#if></#if>/>
			</td>
		</tr>
        <tr>
            <td>类型</td>
            <td>
                <select name="type">
                    <option value="0">文章</option>
                    <option value="1">小说</option>
                </select>
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