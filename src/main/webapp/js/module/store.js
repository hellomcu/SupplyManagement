function addStore(storeName, storeAddress, callNumber, userName, passWord,
		beizhu) {

	var jsonParams = {
		"storeName" : storeName,
		"storePlace" : storeAddress,
		"contacts" : callNumber,
		"username" : userName,
		"password" : passWord,
		"description" : beizhu
	};

	$.ajax({
		url : 'admin/store',
		contentType : "application/json; charset=utf-8",
		data : JSON.stringify(jsonParams),
		type : 'put',
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

function getStore(num) {

	/*
	 * var jsonParams = { "page" : num, "num" : 10,
	 *  };
	 */

	$.ajax({
		url : 'admin/store/stores?page=' + num + '&num=10',
		// contentType : "application/json; charset=utf-8",
		// data : JSON.stringify(jsonParams),
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			// alert(JSON.stringify(data));

			// alert(JSON.stringify(data.data));

			initData(data.data);

		},
		error : function() {
			alert("异常");
		}
	});

}

function initData(data) {
	for (var i = 0; i < data.length; i++) {
alert(JSON.stringify(data[i].createTime));
	}
}