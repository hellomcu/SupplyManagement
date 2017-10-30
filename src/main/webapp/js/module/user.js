
function userLogin(username, password, userType) {

	var jsonParams = {
		"password" : password,
		"type" : userType,
		"username" : username
	};

	$.ajax({
		url :  'user/user_login',
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(jsonParams),
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			//alert(JSON.stringify(data));
			
			if (userType == 1){
//				alert("总部");
				window.location.href="./main.jsp"; 
			}else{
				alert("门店");
			}

		},
		error : function() {
			alert("异常");
		}
	});
}