

function initCartList(data) {
	console.log(data);
	var list = data.details;
	var tbody = document.getElementById('tb');
	
	$(tbody).empty();
	for (var i = 0; i < list.length; i++) {
		// alert(JSON.stringify(data[i].createTime));

		var x = tbody.insertRow(i);
		x.insertCell(0).innerHTML = i + 1;
		var pName = x.insertCell(1);
		var pUnit = x.insertCell(2);
		var pNum = x.insertCell(3);
		var pPrice = x.insertCell(4);
		var pOpr = x.insertCell(5);
		
		pName.innerHTML = list[i].productName;
		var unitPrice = list[i].unitPrice;
		pUnit.innerHTML = "<h4><span class='text-danger'>" + unitPrice
							+ "</span><small>元/" + list[i].productUnit + "</small></h4>";
		var num = list[i].productNum;
		pNum.innerHTML = "<input type='number' value='" +num + "' id='num' />";
		pPrice.innerHTML = parseFloat(unitPrice) * parseInt(num) + "元";

		pOpr.innerHTML = "<button type='button' class='btn btn-flat btn-danger' onclick='deleteProduct("
				+ list[i].productId + ");'>移除</button>";
	}

//	var totalPage = data.totalPage;
//	$('#pagination').pagination({
//		items : data.totalPage,
//		itemOnPage : data.itemNum,
//		currentPage : data.currentPage,
//		cssStyle : '',
//		prevText : '上一页',
//		nextText : '下一页',
//		onInit : function() {
//			// fire first page loading
//		},
//		onPageClick : function(page, evt) {
//			getProducts(page, $('#search-input').val());
//			// $('#alt-style-pagination-content').text('Page ' + page);
//		}
//	});
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
			initCartList(data.data);
		}
	});

}
