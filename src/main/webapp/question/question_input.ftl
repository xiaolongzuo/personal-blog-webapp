<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<script type="text/javascript" src="${contextPath}/resources/js/tinymce/tinymce.min.js"></script>
</head>
<body>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
        <#include "question_input_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>