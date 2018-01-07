function addStore(storeName, storeAddress, callNumber, userName, passWord,
		beizhu) {

	var jsonParams = {
		"storeName" : storeName,
		"storePlace" : storeAddress,
		"contacts" : callNumber,
		"username" : userName,
		"password" : passWord,
		"description" : beizhu
	};
	$.myAjax('./admin/store', 'PUT', JSON.stringify(jsonParams),
			function(data) {
				if (data.code != 1) {
					alert(data.message);
				} else {
					alert("添加成功！");
					window.location.href = "./stores.html";
				}
			});

}

function getStore(page) {

	$.myAjax('./admin/store/stores?page=' + page + '&num=10', 'GET', null,
			function(data) {
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
		var b = x.insertCell(3);
		var c = x.insertCell(4);
		y.innerHTML = i + 1;
		z.innerHTML = '<a href="#">' + list[i].storeName
				+ '</a>';

		a.innerHTML = list[i].storePlace;
		b.innerHTML = list[i].contacts;

		c.innerHTML = "<button type='button' class='btn btn-flat btn-warning' onclick=toRecharge("+ list[i] + ")>充值</button>"
			+ "&nbsp;&nbsp;<button type='button' class='btn btn-flat btn-danger' onclick=deleteStore("+ list[i].id + ")>删除</button>";
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
        	getStore(page);
//            $('#alt-style-pagination-content').text('Page ' + page);
        }
    });
}
function deleteStore(id) {
	var r = confirm("要删除它吗？");
	if (r == true) {
		$.myAjax('./admin/store?id=' + id, 'DELETE', null,
				function(data) {
					if (data.code != 1) {
						alert(data.message);
					} else {
						window.location.href = "./stores.html";
					}
				});

	} else {

	}
}

function toRecharge(store) {
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
					$("#category-tree").treeview2({
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