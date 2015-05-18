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
<script type="text/javascript" src="../resources/js/jquery.min.js"></script>
</head>
<body>
<div style="margin: 20px">
	<a href="/jsp/match_input.jsp" style="color: blue">我要为晓风贡献力量，记录我的对战结果</a><br/>
</div>
<div style="margin: 20px">
	<form action="/search" method="post">
	<br/>英雄1:<input type="text" name="hero"/>
	<br/>英雄2:<input type="text" name="hero"/>
	<br/>英雄3:<input type="text" name="hero"/>
	<br/>英雄4:<input type="text" name="hero"/>
	<br/>英雄5:<input type="text" name="hero"/>
	<br/><input style="padding: 15px;width: 200px;height: 20px;" id="searchButton" type="submit" value="我要查询JJC阵容破法"/>
	</form>
</div>
</body>
</html>