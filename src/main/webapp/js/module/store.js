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
	$.myAjax('admin/store', 'PUT', JSON.stringify(jsonParams),
			function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					alert("添加成功！");
					window.location.href = "./mendianManage.html";
				}
			});

}

function getStore(num) {

	$.myAjax('admin/store/stores?page=' + num + '&num=10', 'GET', null,
			function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					initData(data.data);
				}
			});
}

function initData(data) {
	for (var i = 0; i < data.length; i++) {
		// alert(JSON.stringify(data[i].createTime));

		var x = document.getElementById('tb').insertRow(i);
		var y = x.insertCell(0);
		var z = x.insertCell(1);

		var a = x.insertCell(2);
		var b = x.insertCell(3);
		var c = x.insertCell(4);
		y.innerHTML = i + 1;
		z.innerHTML = '<a href="http://www.baidu.com">' + data[i].storeName
				+ '</a>';

		a.innerHTML = data[i].storePlace;
		b.innerHTML = data[i].contacts;

		c.innerHTML = "<button type='button' class='btn btn-danger' onclick=deleteStore("+ data[i].id + ")>删除</button>";
	}
}
function deleteStore(id) {
	var r = confirm("要删除它吗？");
	if (r == true) {
		$.myAjax('admin/store?id=' + id, 'DELETE', null,
				function(data) {
					if (data.code != 1) {
						alert(data.message);
					} else {
						window.location.href = "./mendianManage.html";
					}
				});

	} else {

	}
}