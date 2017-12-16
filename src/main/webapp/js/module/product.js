function addProduct(categoryId, remark, productName, productNum, productPrice,
		productUnit, productPlace) {

	var jsonParams = {
			  "categoryId": categoryId,
			  "description": "remark",
			  "productDate": null,
			  "productName": productName,
			  "productNum": productNum,
			  "productPlace": productPlace,
			  "productPrice": productPrice,
			  "productUnit": productUnit,
			  "qualityGuaranteePeriod": null
			};
	$.myAjax('admin/product', 'PUT', JSON.stringify(jsonParams),
			function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					alert("添加成功！");
					window.location.href = "product_manage.html";
				}
			});

}
function getProducts(num) {

	$.myAjax('admin/product/products?page=' + num + '&num=10', 'GET', null,
			function(data) {
				// alert(JSON.stringify(data.data));

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
		// var b = x.insertCell(3);
		var c = x.insertCell(3);
		y.innerHTML = data[i].productName;
		z.innerHTML = data[i].productPlace;

		a.innerHTML = "<h4><span class='text-danger'>" + data[i].productPrice + "</span><small>元/" + data[i].productUnit + "</small></h4>";
		// b.innerHTML = data[i].productUnit;

		// c.innerHTML = "<input type='button' value='立即购买'
		// onclick='createOrder(" + JSON.stringify(data[i]) + ");' />";
		c.innerHTML = "<button type='button' class='btn btn-danger' onclick='javascript:alert(\"暂不提供删除功能\");'>删除</button>";
	}
}
