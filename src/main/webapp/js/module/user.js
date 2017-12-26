
function userLogin(username, password, userType) {

	var jsonParams = {
		"password" : password,
		"type" : userType,
		"username" : username
	};
	$.myAjax('../user/user_login', 'POST', JSON.stringify(jsonParams),
			function(data) {
		if (userType == 1){
//			alert("总部");
			window.location.href="./home.html"; 
		}else{
			alert("门店");
		}
	});

}