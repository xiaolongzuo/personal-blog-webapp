<!DOCTYPE html>
<html>
<head>
<#assign metaTitle="Zeus个人博客" />
<#assign metaKeywords="左潇龙,个人博客,Zeus" />
<#assign metaDescription="左潇龙的个人博客，记录了工作与生活当中的点点滴滴" />
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/common/list.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
        <#include "question_list_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>