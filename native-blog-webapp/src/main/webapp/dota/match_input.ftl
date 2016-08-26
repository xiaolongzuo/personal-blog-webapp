<!DOCTYPE html>
<html>
<head>
<#include "../common/meta.ftl">
<#include "../common/head.ftl">
<script>
  $(document).ready(function() {
    $(".heroInput").autocomplete({
      source: "${contextPath}/heroFinder.do"
    });
    $("#submitButton").click(function(){
    	$.ajax({
    		url:"${contextPath}/saveMatch.do",
    		type:"POST",
    		data:{"a":$("#a1").val() + "," + $("#a2").val() + "," + $("#a3").val() + "," + $("#a4").val() + "," + $("#a5").val(),
    			  "d":$("#d1").val() + "," + $("#d2").val() + "," + $("#d3").val() + "," + $("#d4").val() + "," + $("#d5").val(),
    			  "result":$(":radio[name=result]:checked").val(),
    			  "count":$("#count").val()
    		},
    		success:function(data){
    			if(data && data == 'success') {
					alert("感谢你对公会的贡献，你输入的数据将会为公会贡献一份力量。");
    				window.location.href="${contextPath}/dota/dota_index.ftl";
    			} else {
					alert(data);
				}
    		}
    	});
    });
  });
</script>
	<style type="text/css">
		.heroInput {
			height: 20px;
		}
		input {
			margin: 5px;
		}
	</style>
</head>
<body>
<#include "../common/header.ftl">
<article>
    <div class="left_box float_left">
		<#include "match_input_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</body>
</html>