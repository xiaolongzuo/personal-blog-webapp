<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/article.css" rel="stylesheet"/>
<style type="text/css">
.left_box p {text-indent: 2em;line-height:30px;}
</style>
</head>
<body>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
		<#include "about_me_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>