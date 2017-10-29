<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!--
	Author: W3layouts
	Author URL: http://w3layouts.com
	License: Creative Commons Attribution 3.0 Unported
	License URL: http://creativecommons.org/licenses/by/3.0/
-->



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/mendian.css" type="text/css" />
<style type="text/css">

</style>

<script type="text/javascript">
function mendian() {
	window.location.href="./mendianManage.jsp"; 
}
function goods() {
	window.location.href="./goodsManage.jsp"; 
}
function goodsClassification() {
	window.location.href="./goodsClassificationManage.html"; 
}

</script>
</head>
<body>

	<input type="button" value="门店管理	►"
		style="width: 100%; height: auto; padding: 20px;" class="button05" onclick="mendian();">
	<br />
	<br />
	<input type="button" value="货品管理	►"
		style="width: 100%; height: auto; padding: 20px;" class="button05" onclick="goods();">
		<br />
	<br />
	<input type="button" value="货品分类管理	►"
		style="width: 100%; height: auto; padding: 20px;" class="button05" onclick="goodsClassification();">

</body>
</html>