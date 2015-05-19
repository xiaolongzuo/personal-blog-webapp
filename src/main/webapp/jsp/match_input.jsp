<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
    $("#submitButton").click(function(){
    	$.ajax({
    		url:"/saveMatch.do",
    		type:"POST",
    		data:{"a":$("#a1").val() + "," + $("#a2").val() + "," + $("#a3").val() + "," + $("#a4").val() + "," + $("#a5").val(),
    			  "d":$("#d1").val() + "," + $("#d2").val() + "," + $("#d3").val() + "," + $("#d4").val() + "," + $("#d5").val(),
    			  "result":$(":radio[name=result]:checked").val()	  
    		},
    		success:function(data){
    			if(data && data == 'success') {
					alert("感谢你对公会的贡献，你输入的数据将被其它人使用。");
    				window.location.href="/jsp/dota_index.jsp";
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
<div style="margin: 20px">
	<form action="/saveMatch.do" method="post">
	<br/>进攻方阵容:  <input id="a1" class="heroInput" type="text" />
					<input id="a2" class="heroInput" type="text" />
					<input id="a3" class="heroInput" type="text" />
					<input id="a4" class="heroInput" type="text" />
					<input id="a5" class="heroInput" type="text" />
	<br/>防守方阵容:  <input id="d1" class="heroInput" type="text" />
					<input id="d2" class="heroInput" type="text" />
					<input id="d3" class="heroInput" type="text" />
					<input id="d4" class="heroInput" type="text" />
					<input id="d5" class="heroInput" type="text" />
	<br/>胜负: <input type="radio" name="result" checked="checked" value="1"/>进攻胜利   &nbsp;&nbsp;<input type="radio" name="result" value="0"/>进攻失败   &nbsp;&nbsp;
	<br/><input style="padding: 0px;width: 200px; line-height: 20px;" id="submitButton" type="button" value="我要保存"/>
	</form>
</div>
</body>
</html>