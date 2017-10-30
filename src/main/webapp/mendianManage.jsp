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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>门店管理</title>
<link rel="stylesheet" href="css/mendian.css" type="text/css" />

<script type="text/javascript">
	function addMenDian() {
		window.location.href = "./addMenDian.jsp";
	}

	function getAllStore(num) {
		getStore(num);
	}
</script>
</head>
<body onload="getAllStore(1);">
	<input type="button" class="button05" value="添加门店"
		onclick="addMenDian();" />

	<br />
	<br />

	<br />
	<div class="table-b">
		<table id="myTable"  
cellspacing="30">
			<tr>
				<th>编号</th>
				<th>名称</th>
				<th>地址</th>
				<th>电话</th>
				<th>操作</th>
			</tr>

		</table>
	</div>
</body>
</html>

<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/module/store.js"></script>