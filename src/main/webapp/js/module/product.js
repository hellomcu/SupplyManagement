function addProduct(categoryId, remark, productName, productNum, productPrice,
		productUnit, productPlace) {

	var jsonParams = {
		"categoryId" : categoryId,
		"description" : "remark",
		"productDate" : null,
		"productName" : productName,
		"productNum" : productNum,
		"productPlace" : productPlace,
		"productPrice" : productPrice,
		"productUnit" : productUnit,
		"qualityGuaranteePeriod" : null
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
	console.log(list);
	$(tbody).empty();
	for (var i = 0; i < list.length; i++) {
		// alert(JSON.stringify(data[i].createTime));

		var x = tbody.insertRow(i);
		x.insertCell(0).innerHTML = i + 1;
		var y = x.insertCell(1);
		var z = x.insertCell(2);

		var a = x.insertCell(3);
		// var b = x.insertCell(3);
		var c = x.insertCell(4);
		y.innerHTML = list[i].productName;
		z.innerHTML = list[i].productPlace;

		a.innerHTML = "<h4><span class='text-danger'>" + list[i].productPrice
				+ "</span><small>元/" + list[i].productUnit + "</small></h4>";
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

function toCart(product) {
	$('#my-modal').modal('show');
	$('#my-modal').on(
			'shown.bs.modal',
			function() {
				$("#product-name").val(product.productName);
				$("#product-price").val(
						product.productPrice + "元/" + product.productUnit);
				$("#product-place").val(product.productPlace);
				$('#btn-add-cart').unbind("click");
				$('#btn-add-cart').bind("click", function() {
					var amount = $("#amount").val();
					if (!isPositive(amount)) {
						alert("配货数量必须大于0");
						return;
					}
					amount = parseFloat(amount);

					addCart(product.id, amount, function() {
						$('#my-modal').modal('hide');
						window.location.href = "./products.html";

					});
				});
			});
	$('#my-modal').on('hidden.bs.modal', function() {
		$('#add-cart-form')[0].reset();
	});
}

function addCart(id, amount, success) {
	var params = {
		"cartRemark" : null,
		"details" : [ {
			productId : id,
			productNum : amount
		} ]
	};
	$.myAjax('./admin/cart/add_cart', 'POST', JSON.stringify(params), function(
			data) {
		if (data.code != 1) {
			alert(data.message);
		} else {
			alert("添加成功");
			if (!!success) {
				success();
			}
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


function getMyCart() {

	$.myAjax('./admin/cart/my_cart', 'GET', null, function(data) {

		if (data.code != 1) {
			alert(data.message);
		} else {
			console.log(data);
		}
	});

}
