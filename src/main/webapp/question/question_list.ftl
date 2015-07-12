<!DOCTYPE html>
<html>
<head>
<#include "../common/meta.ftl">
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