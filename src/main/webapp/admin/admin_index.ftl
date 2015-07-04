<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
</head>
<body>
<#include "../common/header.ftl">
<article>
<h3>后台管理</h3>
<table cellpadding="0" border="1" align="center" style="margin:10px 0px;">
	<tbody>
		<tr>
			<th><a href="${contextPath}/admin/statistics.ftl">统计信息</a></th>
			<th><a href="${contextPath}/admin/new_comment.ftl">最新评论</a></th>
			<th><a href="${contextPath}/admin/article_manager.ftl">文章管理</a></th>
            <th><a href="${contextPath}/admin/record_manager.ftl">记录管理</a></th>
		</tr>
	</tbody>
</table>
</article>
<#include "../common/footer.ftl">
</body>
</html>