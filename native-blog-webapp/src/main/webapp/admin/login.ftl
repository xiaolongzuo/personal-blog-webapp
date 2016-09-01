<!DOCTYPE html>
<html>
<head>
<#include "../common/meta.ftl">
<#include "../common/head.ftl">
</head>
<body>
<#include "../common/header.ftl">
<article>
<form action="${contextPath}/admin/login.do" method="POST">
<div style="width:100%;height:500px;line-height:20px;" align="center">
	<table id="showTable" cellpadding="0" border="0" align="center">
		<tr>
			<td style="width:50px;padding:10px;">密码：</td>
			<td style="width:200px;padding:10px;"><input type="password" name="password"/></td>
		<tr>
		<tr>
			<td style="width:50px;padding:10px;">&nbsp;</td>
			<td style="width:200px;padding:10px;">
			<input type="submit" value="登录" style="line-height:30px;height:30px;width:80px;"/>
			</td>
		</tr>
	</table>
</div>
</form>
</article>
<#include "../common/footer.ftl">
<#include "../common/bottom.ftl">
</body>
</html>