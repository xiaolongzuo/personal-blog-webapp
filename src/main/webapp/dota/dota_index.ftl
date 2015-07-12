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
		$("#searchButton").click(function(){
			$.ajax({
				url:"${contextPath}/search.do",
				type:"POST",
				data:{"h":$("#h1").val() + "," + $("#h2").val() + "," + $("#h3").val() + "," + $("#h4").val() + "," + $("#h5").val()},
				success:function(data){
					$("#showTable").html('<tbody id="tableBody"><tr> <th width="420">推荐阵容</th> <th width="90">胜率</th> <th width="90">对战次数</th> <th width="90">胜场次数</th></tr></tbody>');
					if(data && data.orderList && data.orderList.length > 0 && data.totalMap) {
						var orderList = data.orderList;
						var totalMap = data.totalMap;
						var tableData = '';
						for (var i = 0;i < orderList.length;i++) {
							var keys = orderList[i];
							var key = "[";
							for (var j = 0; j < keys.length; j++) {
								if (j == 0) {
									key = key + '"' + keys[j] + '"';
								} else {
									key = key + ',"' + keys[j] + '"';
								}
							}
							key = key + "]";
							if (i == 0 && totalMap[key][0] == 0) {
								alert("该阵容目前没有破解方式，请大家努力录入对战结果才会有哦！");
								return;
							}
							tableData += '<tr><td width="200">' + keys + '</td>';
							tableData += '<td>' + totalMap[key][0] + '%</td>';
							tableData += '<td>' + totalMap[key][1] + '场</td>';
							tableData += '<td>' + totalMap[key][2] + '场</td>';
							tableData += '</tr>';
						}
						$("#tableBody").append(tableData);
					} else if (data && !data.orderList) {
						alert(data);
					} else {
						alert("该阵容目前没有破解方式，请大家努力录入对战结果才会有哦！");
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
	td {
		text-align: center;
	}
	th {
		text-align: center;
	}
</style>
</head>
<body>
<#include "../common/header.ftl">
<article>
	<div class="left_box float_left">
		<#include "dota_index_main.ftl">
	</div>
	<div class="right_box float_right">
		<#include "right.ftl">
	</div>
</article>
<#include "../common/footer.ftl">
</body>
</html>