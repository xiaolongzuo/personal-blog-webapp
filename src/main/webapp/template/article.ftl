<!DOCTYPE html>
<html>
<head>
<#include "head.ftl">
<link href="resources/css/article.css" rel="stylesheet"/>
<link href="resources/css/code.css" rel="stylesheet"/>
<script type="application/javascript" src="resources/js/counter.js"></script>
<script type="application/javascript" src="resources/js/comment.js"></script>
</head>
<body>
<input id="articleId" type="hidden" name="articleId" value="${article.id}"/>
<#include "header.ftl">
<article>
    <#include "article_main.ftl">
    <#include "right.ftl">
</article>
<#include "footer.ftl">
</body>
</html>
