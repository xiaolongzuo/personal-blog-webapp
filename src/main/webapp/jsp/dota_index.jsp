<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>晓风残月</title>
<meta name="keywords" content="个人博客,Zeus">
<meta name="description" content="左潇龙,Zeus,个人博客">
<link href="../resources/css/base.css" rel="stylesheet">
<link href="../resources/css/index.css" rel="stylesheet">
	<link href="../resources/css/jquery-ui.min.css" rel="stylesheet">
	<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
	<script type="text/javascript" src="../resources/js/jquery-ui.min.js"></script>
	<script>
		$(document).ready(function() {
			$(".heroInput").autocomplete({
				source: "/heroFinder.do"
			});
			$.ajax({
				url:"/matchCount.do",
				type:"POST",
				success:function(data) {
					if(data && !isNaN(data)) {
						$("#matchSize").html(data);
					} else {
						$("#infoSpan").html("真是抱歉，获取系统信息失败了，你可以尝试刷新页面或者联系男神来帮你解决！");
					}
				},
				error:function() {
					$("#infoSpan").html("真是抱歉，获取系统信息失败了，你可以尝试刷新页面或者联系男神来帮你解决！");
				}
			});
			$("#searchButton").click(function(){
				$.ajax({
					url:"/search.do",
					type:"POST",
					data:{"h":$("#h1").val() + "," + $("#h2").val() + "," + $("#h3").val() + "," + $("#h4").val() + "," + $("#h5").val()},
					success:function(data){
						$("#showTable").html('<tbody id="tableBody"><tr> <th width="200">阵容</th> <th>总胜率</th> <th>进攻胜率</th> <th>防守胜率</th>' +
						' <th>总场数</th> <th>总胜利数</th> <th>总失败数</th> <th>进攻场数</th> <th>进攻胜利数</th> <th>进攻失败数</th> <th>防守场数</th>' +
						' <th>防守胜利数</th> <th>防守失败数</th> </tr></tbody>');
						if(data && data.orderList && data.orderList.length > 0 && data.totalMap && data.attackMap && data.defendMap) {
							var orderList = data.orderList;
							var totalMap = data.totalMap;
							var attackMap = data.attackMap;
							var defendMap = data.defendMap;
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
								if (attackMap[key]) {
									tableData += '<td>' + attackMap[key][0] + '%</td>';
								} else {
									tableData += '<td>0%</td>';
								}
								if (defendMap[key]) {
									tableData += '<td>' + defendMap[key][0] + '%</td>';
								} else {
									tableData += '<td>0%</td>';
								}
								tableData += '<td>' + totalMap[key][1] + '</td>';
								tableData += '<td>' + totalMap[key][2] + '</td>';
								tableData += '<td>' + totalMap[key][3] + '</td>';
								if (attackMap[key]) {
									tableData += '<td>' + attackMap[key][1] + '</td>';
									tableData += '<td>' + attackMap[key][2] + '</td>';
									tableData += '<td>' + attackMap[key][3] + '</td>';
								} else {
									tableData += '<td>0</td>';
									tableData += '<td>0</td>';
									tableData += '<td>0</td>';
								}
								if (defendMap[key]) {
									tableData += '<td>' + defendMap[key][1] + '</td>';
									tableData += '<td>' + defendMap[key][2] + '</td>';
									tableData += '<td>' + defendMap[key][3] + '</td>';
								} else {
									tableData += '<td>0</td>';
									tableData += '<td>0</td>';
									tableData += '<td>0</td>';
								}
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
<div style="margin: 20px">
	<span id="infoSpan" style="color: red;">当前数据库当中共保存了<span id="matchSize" style="color: red;"></span>场对战结果，加油！</span><br/>
</div>
<div style="margin: 20px">
	<span>为晓风贡献力量，记录自己的对战结果，请点击<a href="/jsp/match_input.jsp" style="color: blue;text-decoration: underline;">&nbsp;这里&nbsp;</a>（PS:为保证结果的正确性，请只录入JJC的对战结果，不要录入巅峰的）</span><br/>
</div>
<div style="margin: 20px">
	待破解的阵容：
		<input id="h1" class="heroInput" type="text"/>
		<input id="h2" class="heroInput" type="text"/>
		<input id="h3" class="heroInput" type="text"/>
		<input id="h4" class="heroInput" type="text"/>
		<input id="h5" class="heroInput" type="text"/>
		<input style="padding: 0px;width: 200px; line-height: 20px;" id="searchButton" type="button" value="我要破解"/>
</div>
<table id="showTable" cellpadding="0" border="0" align="center" width="1200">
	<tbody id="tableBody">
		<tr>
			<th width="200">推荐阵容</th>
			<th>总胜率</th>
			<th>进攻胜率</th>
			<th>防守胜率</th>
			<th>总场数</th>
			<th>总胜利数</th>
			<th>总失败数</th>
			<th>进攻场数</th>
			<th>进攻胜利数</th>
			<th>进攻失败数</th>
			<th>防守场数</th>
			<th>防守胜利数</th>
			<th>防守失败数</th>
		</tr>
	</tbody>
</table>
</body>
</html>