function getCategories(num) {

	/*
	 * var jsonParams = { "page" : num, "num" : 10, };
	 */

	$.myAjax('admin/category/categories?page=' + num + '&num=100000', 'GET',
			null, function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					initCategory(data.data);
				}
			});
	console.log('vaaaaav');
	var nodes = [
		 {
		 text: "Parent 1",
		 nodes: [
		  {
		  text: "Child 1",
		  nodes: [
		   {
		   text: "Grandchild 1"
		   },
		   {
		   text: "Grandchild 2"
		   }
		  ]
		  },
		  {
		  text: "Child 2"
		  }
		 ]
		 },
		 {
		 text: "Parent 2"
		 },
		 {
		 text: "Parent 3"
		 },
		 {
		 text: "Parent 4"
		 },
		 {
		 text: "Parent 5"
		 }
		];
	var tree = {
		text : "Node 1",
		icon : "glyphicon glyphicon-stop",
		selectedIcon : "glyphicon glyphicon-stop",
		color : "#000000",
		backColor : "#FFFFFF",
		selectable : false,
		multiSelect: false,    //多选
		showCheckbox: false,
		state : {
			checked : false,
			disabled : false,
			expanded : false,
			selected : false
		},
		data : nodes
	};
	console.log('vv');
	$('#category-tree').treeview(tree);
	console.log('cc');
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
