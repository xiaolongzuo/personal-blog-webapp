<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/common/article.css" rel="stylesheet"/>
<link href="${contextPath}/resources/css/common/code.css" rel="stylesheet"/>
<script type="text/javascript">
	counter({"type":2,"questionId":$("#questionId").val()});
</script>
</head>
<body>
<input id="questionId" type="hidden" name="questionId" value="${question.id}"/>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
		<#include "question_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>
