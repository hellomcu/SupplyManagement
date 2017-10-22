var jsonParams = {
	"password" : "admin123456",
	"type" : 1,
	"username" : "admin"
};
function userLogin() {
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