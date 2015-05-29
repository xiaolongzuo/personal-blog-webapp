<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/article.css" rel="stylesheet"/>
<link href="${contextPath}/resources/css/code.css" rel="stylesheet"/>
<script type="application/javascript" src="${contextPath}/resources/js/counter.js"></script>
<script type="application/javascript" src="${contextPath}/resources/js/comment.js"></script>
</head>
<body>
<input id="articleId" type="hidden" name="articleId" value="${article.id}"/>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
		<#include "article_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>
