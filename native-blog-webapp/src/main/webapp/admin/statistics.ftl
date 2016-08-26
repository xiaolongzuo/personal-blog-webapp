<!DOCTYPE html>
<html>
<head>
<#include "../common/meta.ftl">
<#include "../common/head.ftl">
</head>
<body>
<#include "../common/header.ftl">
<article>
<h3>统计信息</h3>
<table cellpadding="0" border="1" align="center" style="margin:10px 0px;">
	<tbody>
		<tr>
			<th>网站总访问量</th>
			<th>网站本日访问量</th>
            <th>网站总访问IP数</th>
            <th>网站本日访问IP数</th>
			<th>文章总访问量</th>
			<th>评论总数</th>
			<th>本日新增评论数</th>
			<th>用户总数</th>
			<th>本日新增用户数</th>
		</tr>
		<tr>
			<td align="center">${siteTotalAccessTimes}</td>
			<td align="center">${siteTodayAccessTimes}</td>
            <td align="center">${siteTotalVisitorIpNumber}</td>
            <td align="center">${siteTodayVisitorIpNumber}</td>
			<td align="center">${articleTotalAccessTimes}</td>
			<td align="center">${commentTotalNumber}</td>
			<td align="center">${commentTodayNumber}</td>
			<td align="center">${userTotalNumber}</td>
			<td align="center">${userTodayNumber}</td>
		</tr>
	</tbody>
</table>
</article>
<#include "../common/footer.ftl">
</body>
</html>