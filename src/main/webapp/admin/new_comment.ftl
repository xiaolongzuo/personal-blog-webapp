<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
</head>
<body>
<#include "../common/header.ftl">
<article>
<h3>最近回复</h3>
<table cellpadding="0" border="1" align="center" style="margin:10px 0px;">
	<tbody>
		<tr>
			<th style="width:200px;">评论时间</th>
			<th style="width:200px;">评论人</th>
			<th style="width:100px;">地址</th>
			<th style="width:500px;">评论内容</th>
		<tr>
		<#if newComments?? && newComments?size gt 0>
        	<#list newComments as comment>
			<tr>
				<td align="center" style="padding:10px;">${comment.create_date}</td>
				<td align="center" style="padding:10px;">${comment.commenter}</td>
				<td align="center" style="padding:10px;"><a href="${comment.articleUrl}" target="_blank">查看</a></td>
				<td align="center" style="padding:10px;">
					<div style="width:500px;">
					${comment.content}
					</div>
				</td>
			</tr>
        	</#list>
        </#if>
	</tbody>
</table>
</article>
<#include "../common/footer.ftl">
</body>
</html>