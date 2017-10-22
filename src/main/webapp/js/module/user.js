
function userLogin(username, password, type) {

	var jsonParams = {
		"password" : password,
		"type" : type,
		"username" : username
	};

	$.ajax({
		url : BASE_URL + 'user/user_login',
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(jsonParams),
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			alert(JSON.stringify(data));

		},
		error : function() {
			alert("异常");
		}
	});
}