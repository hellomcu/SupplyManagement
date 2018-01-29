function addProduct(categoryId, remark, productName, productNum, productPrice,
		productUnit, productPlace, salePrice) {

	var jsonParams = {
		"categoryId" : categoryId,
		"description" : remark,
		"productDate" : null,
		"productName" : productName,
		"productNum" : productNum,
		"productPlace" : null,
		"productPrice" : productPrice,
		"productUnit" : productUnit,
		"qualityGuaranteePeriod" : null,
		"salePrice" : salePrice
	};
	$.myAjax('./admin/product', 'PUT', JSON.stringify(jsonParams), function(
			data) {
		if (data.code != 1) {
			alert(data.message);
		} else {
			alert("添加成功！");
			window.location.href = "./products.html";
		}
	});

}

function getProducts(page, productName) {

	$.myAjax('./admin/product/products?page=' + page + '&num=10&productName='
			+ productName, 'GET', null, function(data) {
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
	
		var x = tbody.insertRow(i);
		x.insertCell(0).innerHTML = i + 1;
		var y = x.insertCell(1);
		//var z = x.insertCell(2);

		var a = x.insertCell(2);
		var salePrice = x.insertCell(3);
		var stock = x.insertCell(4);
		var c = x.insertCell(5);
		y.innerHTML = "<a href='product_detail.html?product="
				+ encodeURI(encodeURI(JSON.stringify(list[i]))) + "'>"
				+ list[i].productName + "</a>";
		console.log(JSON.stringify(list[i]));
		//z.innerHTML = list[i].productPlace;

		a.innerHTML = "<h4><span class='text-danger'>" + list[i].productPrice
				+ "</span><small>元/" + list[i].productUnit + "</small></h4>";
		salePrice.innerHTML = "<h4><span class='text-danger'>" + list[i].salePrice
		+ "</span><small>元/" + list[i].productUnit + "</small></h4>";
		stock.innerHTML = list[i].productNum;
		// b.innerHTML = data[i].productUnit;

		// c.innerHTML = "<input type='button' value='立即购买'
		// onclick='createOrder(" + JSON.stringify(data[i]) + ");' />";
		c.innerHTML = "<button type='button' class='btn btn-flat btn-success' onclick='toCart("
				+ JSON.stringify(list[i])
				+ ");'>加入购物车</button>"
				+ "&nbsp;&nbsp;<button type='button' class='btn btn-flat btn-danger' onclick='deleteProduct("
				+ list[i].id + ");'>删除</button>";
	}

	var totalPage = data.totalPage;
	$('#pagination').pagination({
		items : data.totalPage,
		itemOnPage : data.itemNum,
		currentPage : data.currentPage,
		cssStyle : '',
		prevText : '上一页',
		nextText : '下一页',
		onInit : function() {
			// fire first page loading
		},
		onPageClick : function(page, evt) {
			getProducts(page, $('#search-input').val());
			// $('#alt-style-pagination-content').text('Page ' + page);
		}
	});
}

function deleteProduct(id) {
	var r = confirm("要删除它吗？");
	if (r == true) {
		$.myAjax('./admin/product?id=' + id, 'DELETE', null, function(data) {
			if (data.code != 1) {
				alert(data.message);
			} else {
				window.location.href = "./products.html";
			}
		});

	} else {

	}
}

function initProductDetail(product) {
	$('#product-id').val(product.id);
	//console.log($.parseHTML(product.productName));
	$('#productName').val(product.productName);
	$('#productNum').val(product.productNum);
	$('#unitPrice').val(product.productPrice);
	$('#productUnit').val(product.productUnit);
	$('#sale-price').val(product.salePrice);
	//$('#productPlace').val(product.productPlace);
	$('#remark').html(product.description);
}

function updateProduct(id, categoryId, remark, productName, productNum,
		productPrice, productUnit, productPlace, salePrice) {

	var jsonParams = {
		"id" : id,
		"description" : remark,
		"productDate" : null,
		"productName" : productName,
		"productNum" : productNum,
		"productPlace" : null,
		"productPrice" : productPrice,
		"productUnit" : productUnit,
		"qualityGuaranteePeriod" : null,
		"salePrice" : salePrice
	};
	$.myAjax('./admin/product', 'POST', JSON.stringify(jsonParams), function(
			data) {
		if (data.code != 1) {
			alert(data.message);
		} else {
			alert("更新成功！");
			// window.location.href = "./products.html";
		}
	});

}