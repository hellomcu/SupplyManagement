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
function  addMenDian() {
	window.location.href="./addMenDian.jsp"; 
}
function addMenDian() {
	var storeName = document.getElementById("storeName").value;
	var storeAddress = document.getElementById("storeAddress").value;
	var callNumber = document.getElementById("callNumber").value;
	var userName = document.getElementById("userName").value;
	var passWord = document.getElementById("passWord").value;
	
	var beizhu = document.getElementById("beizhu").value;
	
	if (storeName == "" || storeAddress == ""  || callNumber == "" || userName == "" || passWord == ""){
		alert("输入框不能为空");
	}else{
		addStore(storeName,storeAddress,callNumber,userName,passWord,beizhu);
	}
	
	
	
}
</script>
</head>
<body>
<input type="text" class="text05" value="" placeholder="门店名称" id="storeName"/></br></br></br>
<input type="text" class="text05" value="" placeholder="门店地址" id="storeAddress"/></br></br></br>
<input type="text" class="text05" value="" placeholder="门店联系电话"id="callNumber"/></br></br></br>
<input type="text" class="text05" value="" placeholder="门店帐号"id="userName"/></br></br></br>
<input type="text" class="text05" value="" placeholder="门店联帐号密码"id="passWord"/></br></br></br>
<textarea rows="5" cols="" style="width: 100%;font-size: 20px;font-family: 微软雅黑;padding: 10px;" placeholder="备注" id="beizhu"></textarea></br></br>

<center><input type="button" class="button05" value="确定添加" onclick="addMenDian();"/></center>
</body>

<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/module/store.js"></script>
</html>