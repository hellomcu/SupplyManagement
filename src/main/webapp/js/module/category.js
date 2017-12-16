function getCategories(num) {

	/*
	 * var jsonParams = { "page" : num, "num" : 10, };
	 */

	$.myAjax('admin/category/categories?page=' + num + '&num=100000', 'GET', null,
			function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					initData(data.data);
				}
			});

}
//"data": [
//    {
//      "id": 2,
//      "categoryName": "农副产品",
//      "children": [
//        {
//          "id": 6,
//          "categoryName": "蛋类",
//          "children": null
//        },
//        {
//          "id": 9,
//          "categoryName": "鸡肉",
//          "children": null
//        }
//      ]
//    },
function initData(data) {

	var childArray = {};
	for (var i = 0; i < data.length; i++) {
		var parent = data[i];
		var parentId = parent.id;
		$('#category-parent').append("<option value='" + parentId + "'>" + parent.categoryName + "</option>");
		var children = parent.children;
		for (var j=0; j<children.length; j++) {
			var children = parent.children;
			childArray[parentId] = children;
		}
	
	}
	console.log(childArray);
	
	var children = data[0].children;
	for (var j=0; j<children.length; j++) {
		var child = children[j];
		$('#category-child').append("<option value='" + child.id + "'>" + child.categoryName + "</option>");
	}
	
	$("#category-parent").change(function () {  
		$('#category-child').empty();
		var parentId = $(this).children('option:selected').val();  
		var children = childArray[parentId]; 
		
		for (var j=0; j<children.length; j++) {
			var child = children[j];
			$('#category-child').append("<option value='" + child.id + "'>" + child.categoryName + "</option>");
		}
    }); 
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
