function getOrders(num) {

	/*
	 * var jsonParams = { "page" : num, "num" : 10, };
	 */

	$.myAjax('admin/order/orders?page=' + num + '&num=100000', 'GET', null,
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

		var newRow = document.getElementById('tb').insertRow(i);
		var no = newRow.insertCell(0);
		var store = newRow.insertCell(1);
		var contact = newRow.insertCell(2);
		var total = newRow.insertCell(3);
		var cate = newRow.insertCell(4);
		var statusCol = newRow.insertCell(5);
		var oper = newRow.insertCell(6);

		no.innerHTML = i + 1;
		var order = data[i];
		store.innerHTML = order.storeName;
		contact.innerHTML = order.contacts;
		total.innerHTML = order.totalPrice;
		cate.innerHTML = order.productNum;

		var status = order.orderStatus;
		var statusStr = '未知';
		var btnStr = '';
		var btnClass = 'btn-info';
		
		if (status === 1) {
			statusStr = '已下单';
			btnStr = '出货';
			btnClass = 'btn-danger';
		} else if (status === 2) {
			statusStr = '出货中';
			btnStr = '发货';
			btnClass = 'btn-primary';
		} else if (status === 3) {
			statusStr = '配送中';
			btnStr = '到达';
			btnClass = 'btn-warning';
		} else if (status === 4) {
			statusStr = '已到达';
			btnStr = '收货';
			btnClass = 'btn-default';
		} else if (status === 5) {
			statusStr = '已收货';
			btnStr = '完成';
			btnClass = 'btn-success';
		}
		statusCol.innerHTML = statusStr;
		var params = 'order=' + encodeURI(encodeURI(JSON.stringify(order)));

		oper.innerHTML = "<button type='button' class='btn " + btnClass + "' onclick='javascript:window.location.href=\"my_order_detail.html?"
				+ params + "\"'>" + btnStr + "</button>";
	}
}

function getMyOrderDetail(order, products) {
	for (var i = 0; i < products.length; i++) {
		// alert(JSON.stringify(data[i].createTime));

		var newRow = document.getElementById('tb').insertRow(i);
		var no = newRow.insertCell(0);
		var prod = newRow.insertCell(1);
		var unit = newRow.insertCell(2);
		var num = newRow.insertCell(3);
		var total = newRow.insertCell(4);

		no.innerHTML = i + 1;
		prod.innerHTML = products[i].productName;
		var unitPrice = products[i].unitPrice;
		unit.innerHTML = unitPrice;
		var productNum = products[i].productNum;
		num.innerHTML = productNum;
		total.innerHTML = unitPrice * productNum;
	}

}
