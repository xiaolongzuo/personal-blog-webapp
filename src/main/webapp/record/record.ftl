<!DOCTYPE html>
<html>
<head>
<#include "../common/head.ftl">
<link href="${contextPath}/resources/css/common/article.css" rel="stylesheet"/>
<link href="${contextPath}/resources/css/common/code.css" rel="stylesheet"/>
<script type="text/javascript">
	$(document).ready(function() {
        counter({"type":3,"recordId":$("#recordId").val()});
        $("#good_a").click(function () {
            $.ajax({
                url:"${contextPath}/recordRemark.do",
                type:"POST",
                data:{"recordId":$("#recordId").val()},
                success:function(data) {
                    if (data && data == 'success') {
                        var times = parseInt($("#good_times_span").html()) + 1;
                        $("#good_times_span").html(times);
                    } else if (data && data == 'exists') {
                        alert("您已经评价过了！");
                    } else {
                        alert("评价失败！");
                    }
                }
            });
        });
	});
</script>
</head>
<body>
<input id="recordId" type="hidden" name="recordId" value="${record.id}"/>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
		<#include "record_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>
