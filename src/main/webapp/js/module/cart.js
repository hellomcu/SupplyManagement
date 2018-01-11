

function initCartList(data) {
	var list = data.details;
	var tbody = document.getElementById('tb');
	var totalPrice = 0.0;
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
		
		var productId = list[i].productId;
		
		pName.innerHTML = list[i].productName;
		var unitPrice = list[i].unitPrice;
		pUnit.innerHTML = unitPrice + "元/" + list[i].productUnit;
		var num = list[i].productNum;
		pNum.innerHTML = "<input type='number' value='" +num + "' id='" + i + "' class='col-xs-12'/>";
		var xiaoji = parseFloat(unitPrice) * parseInt(num);
		pPrice.innerHTML = "<span class='text-danger' id='price-" + i + "'>" + xiaoji + "</span>元";
		totalPrice += xiaoji;
		pOpr.innerHTML = "<button type='button' class='btn btn-flat btn-danger' onclick='deleteProductFromCart("
				+ list[i].productId + ");'>移除</button>";
		
		$("#" + i).on('input', function() {

			if (this.value == '' || !isPositive(this.value)) {
				this.value = '0';
			}
			var _this = this;
			var subPrice = 0.0;
			updateCart(list[this.id].productId, this.value, 
					function(data) {
						var newNum = data.productNum;
						this.value = newNum; 
						var newPrice = list[_this.id].unitPrice * parseInt(newNum);
//						console.log(newPrice + "," + ;
						subPrice = newPrice - parseFloat($('#price-' + _this.id).html());
						totalPrice += subPrice;
						$('#total-price').html("总价: " + (totalPrice) + " 元");
						if (newNum === 0) {
							var index = _this.parentNode.parentNode.rowIndex;
							tbody.deleteRow(index - 1);
							return;
						}
					
						$('#price-' + _this.id).html(newPrice);
					},
					function() {
						window.location.href = "./my_cart.html";
					});
		});
	}
	
	$('#total-price').html("总价: " + totalPrice + " 元");
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

function deleteProductFromCart(id) {
	var r = confirm("要把它从购物车移除吗？");
	if (r == true) {
		$.myAjax('./admin/cart/cart_product?productId=' + id, 'DELETE', null, function(data) {
			if (data.code != 1) {
				alert(data.message);
			} else {
				window.location.href = "./my_cart.html";
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


function updateCart(productId, productNum, success, err) {
	var params = {
			productId	:	productId,
			productNum	:	productNum
	};
	$.myAjax('./admin/cart/update_cart', 'POST', JSON.stringify(params), function(data) {

		if (data.code != 1) {
			if(err != null) {
				err();
			}
			alert(data.message);
		} else {
			if(success != null) {
				success(data.data);
			}
		}
	});

}