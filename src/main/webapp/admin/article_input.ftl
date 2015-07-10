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
        var updateCreateTime = $("input[name=updateCreateTime]").is(":checked");
        if (updateCreateTime) {
            updateCreateTime = 1;
        } else {
            updateCreateTime = 0;
        }
        var categories = '';
        $("input[name=categories]:checked").each(function() {
            categories = categories + $(this).val() + ",";
        });
        if (categories && $.trim(categories).length > 0) {
            categories = categories.substring(0, categories.length - 1);
        }
		$.ajax({
			url:"${contextPath}/admin/updateArticle.do",
			data:{"id":$("input[name=id]").val(),"content":tinymce.activeEditor.getContent()
                ,"subject":$("input[name=subject]").val(),"status":status,"type":$("select[name=type]").val()
                ,"tags":$("input[name=tags]").val(),"categories":categories,"updateCreateTime":updateCreateTime},
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
            <td>分类</td>
            <td>
                <#list categories as category>
                    <input style="margin: 5px 0px;" type="checkbox" value="${category.id}" name="categories"
                    <#if articleCategories??>
                        <#list articleCategories as articleCategory>
                            <#if articleCategory.id == category.id>
                           checked="checked"
                            </#if>
                        </#list>
                    </#if>
                            />${category.category_name}&nbsp;
                    <#if (category_index > 0) && ((category_index + 1) % 7 == 0)>
                        <br/>
                    </#if>
                </#list>
            </td>
        </tr>
        <tr>
            <td>标签</td>
            <td>
                <input type="text" name="tags"
                   <#if article??>
                        value="${article.tags?default('')}"
                   <#else>
                        value=""
                   </#if>
                />
            </td>
        </tr>
		<tr>
			<td>是否发表</td>
			<td>
				<input type="checkbox" name="status" <#if article??><#if article.status?default('0') == '1'>checked="checked"</#if></#if>/>
			</td>
		</tr>
        <tr>
            <td>是否更新创建时间</td>
            <td>
                <input type="checkbox" name="updateCreateTime" />
            </td>
        </tr>
        <tr>
            <td>类型</td>
            <td>
                <select name="type">
                    <option value="0" <#if article?? && article.type == "0">selected="selected"</#if>>文章</option>
                    <option value="1" <#if article?? && article.type == "1">selected="selected"</#if>>小说</option>
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