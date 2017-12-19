function requestCategory(num, callback) {
	$.myAjax('admin/category/categories?page=' + num + '&num=100000', 'GET',
			null, function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					callback(data.data);
				}
			});
}



function getCategories(num) {

	requestCategory(num, function(data) {
		initCategory(data);
	});
//	$.myAjax('admin/category/categories?page=' + num + '&num=100000', 'GET',
//			null, function(data) {
//				if (data.code != 1) {
//					alert(data.message);
//				} else {
//					initCategory(data.data);
//				}
//			});
}
// "data": [
// {
// "id": 2,
// "categoryName": "农副产品",
// "children": [
// {
// "id": 6,
// "categoryName": "蛋类",
// "children": null
// },
// {
// "id": 9,
// "categoryName": "鸡肉",
// "children": null
// }
// ]
// },
function initCategory(data) {

	var childArray = {};
	for (var i = 0; i < data.length; i++) {
		var parent = data[i];
		var parentId = parent.id;
		$('#category-parent').append(
				"<option value='" + parentId + "'>" + parent.categoryName
						+ "</option>");
		var children = parent.children;
		for (var j = 0; j < children.length; j++) {
			var children = parent.children;
			childArray[parentId] = children;
		}

	}

	var children = data[0].children;
	for (var j = 0; j < children.length; j++) {
		var child = children[j];
		$('#category-child').append(
				"<option value='" + child.id + "'>" + child.categoryName
						+ "</option>");
	}

	$("#category-parent").change(
			function() {
				$('#category-child').empty();
				var parentId = $(this).children('option:selected').val();
				var children = childArray[parentId];

				for (var j = 0; j < children.length; j++) {
					var child = children[j];
					$('#category-child').append(
							"<option value='" + child.id + "'>"
									+ child.categoryName + "</option>");
				}
			});

}

function categoryManage(num) {
	requestCategory(num, function(data) {
		showCategories(data);
	});
}

function showCategories(data) {
	
	for (var i = 0; i < data.length; i++) {
		var parent = data[i];
		var parentId = parent.id;
		$('#category-tree').append("<li class='closed'><span class='folder' id='" + parentId + "'>" + parent.categoryName + "</span><ul></ul>");
		
		$('#' + parentId).next().append("<li><span class='file'><a href='javascript:showAddCategoryDialog(" + parentId + ");'>+添加</a></span></li>");   
		
		var children = parent.children;
		for (var j = 0; j < children.length; j++) {
			var child = children[j];
			$('#' + parentId).next().append("<li><span class='file' id='" + child.id + "'>" + child.categoryName + "</span></li>");   
		}
	}
	
	$("#category-tree").treeview({
		toggle : function() {
			console.log("%s was toggled.", $(this).find(">span").text());
		}
	});
}

function showAddCategoryDialog(parentId){
	$('#my-modal').modal('show');
}
