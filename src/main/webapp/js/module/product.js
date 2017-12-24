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
	$.myAjax('../admin/product', 'PUT', JSON.stringify(jsonParams),
			function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					alert("添加成功！");
					window.location.href = "./products.html";
				}
			});

}
function getProducts(page) {

	$.myAjax('../admin/product/products?page=' + page + '&num=10', 'GET', null,
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
	
	var list = data.list;
	var tbody = document.getElementById('tb');
	
	$(tbody).empty();
	for (var i = 0; i < list.length; i++) {
		// alert(JSON.stringify(data[i].createTime));

		var x = tbody.insertRow(i);
		var y = x.insertCell(0);
		var z = x.insertCell(1);

		var a = x.insertCell(2);
		// var b = x.insertCell(3);
		var c = x.insertCell(3);
		y.innerHTML = list[i].productName;
		z.innerHTML = list[i].productPlace;

		a.innerHTML = "<h4><span class='text-danger'>" + list[i].productPrice + "</span><small>元/" + list[i].productUnit + "</small></h4>";
		// b.innerHTML = data[i].productUnit;

		// c.innerHTML = "<input type='button' value='立即购买'
		// onclick='createOrder(" + JSON.stringify(data[i]) + ");' />";
		c.innerHTML = "<button type='button' class='btn btn-danger' onclick='javascript:alert(\"暂不提供删除功能\");'>删除</button>";
	}
	
	var totalPage = data.totalPage;
	$('#pagination').pagination({
        items: data.totalPage,
        itemOnPage: data.itemNum,
        currentPage: data.currentPage,
        cssStyle: '',
        prevText: '上一页',
        nextText: '下一页',
        onInit: function () {
            // fire first page loading
        },
        onPageClick: function (page, evt) {
        	getProducts(page);
//            $('#alt-style-pagination-content').text('Page ' + page);
        }
    });
}
