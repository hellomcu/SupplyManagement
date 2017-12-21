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
	// $.myAjax('admin/category/categories?page=' + num + '&num=100000', 'GET',
	// null, function(data) {
	// if (data.code != 1) {
	// alert(data.message);
	// } else {
	// initCategory(data.data);
	// }
	// });
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
		$('#category-tree').append(
				"<li class='closed'><span class='folder' id='" + parentId+ "'>" + parent.categoryName + "</span><ul></ul></li>");
		var parentNode = $('#' + parentId).next();
		parentNode.append("<li><span class='file'><a href='javascript:showAddCategoryDialog("
				+ parentId + ");'>+添加</a></span></li>");
		
		var children = parent.children;
		for (var j = 0; j < children.length; j++) {
			var child = children[j];
			parentNode.append(
					"<li><span class='file' id='" + child.id + "'>"
							+ child.categoryName + "</span></li>");
		}
	}

	$("#category-tree").treeview({
//		toggle : function() {
//			console.log("%s was toggled.", $(this).find(">span").text());
//		}
	});
}

function showAddCategoryDialog(parentId) {
	console.log("parentId:" + parentId);
	$('#my-modal').modal('show');
	$('#my-modal').on('shown.bs.modal', function() {
		$('#btn-add-category').unbind("click");
		$('#btn-add-category').bind("click", function() {
			var name = $("#category-name").val();
			if (name === '') {
				alert("名称不能为空");
				return;
			}
			requestAddCategory(parentId, name, function(data){
				$('#my-modal').modal('hide');
				alert("添加成功");
				if (parentId === 0) {
					//添加父分类
					$('#category-tree').prepend("<li><span class='folder' id='" + data.id + "'>" + name + "</span><ul><li><span class='file'><a href='javascript:showAddCategoryDialog(" + data.id + ");'>+添加</a></span></li></ul></li>");
					$("#category-tree").treeview({
					});
				} else {
					//添加子分类
					$('#' + parentId).next().children("li:first-child").after("<li><span class='file' id='" + data.id + "'>"+ name + "</span></li>"); 

				}
				
			});
		});
	});
	$('#my-modal').on('hidden.bs.modal', function() {
		// $('#btn-add-category').bind("click", null);
		// $('#btn-add-category').unbind("click");
		$('#category-form')[0].reset();
	});
}

function requestAddCategory(parentId, categoryName, callback) {

	var body = {
		"categoryName" : categoryName,
		"parentId" : parentId
	};

	$.myAjax('admin/category', 'PUT', JSON.stringify(body), function(data) {
		if (data.code != 1) {
			alert(data.message);
		} else {
			callback(data.data);
		}
	});
}
