
function userLogin(username, password, userType) {

	var jsonParams = {
		"password" : password,
		"type" : userType,
		"username" : username
	};
	$.myAjax('./user/user_login', 'POST', JSON.stringify(jsonParams),
			function(data) {
		if (data.code === 1) {
			if (userType === 1) {
				window.location.href = "./home.html";
			} else {
				alert("用户身份错误");
			}
		} else {
			alert(data.message);
		}
	
	});

}

function userLogout() {

	$.myAjax('./user/user_logout', 'DELETE', null, function(
			data) {

		if (data.code === 1) {
			window.location.href = "./login.html";
	
		} else {
			alert(data.message);
		}
	});

}