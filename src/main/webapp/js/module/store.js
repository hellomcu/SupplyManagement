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
	$.myAjax('../admin/store', 'PUT', JSON.stringify(jsonParams),
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

	$.myAjax('../admin/store/stores?page=' + page + '&num=10', 'GET', null,
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
		z.innerHTML = '<a href="http://www.baidu.com">' + list[i].storeName
				+ '</a>';

		a.innerHTML = list[i].storePlace;
		b.innerHTML = list[i].contacts;

		c.innerHTML = "<button type='button' class='btn btn-danger' onclick=deleteStore("+ list[i].id + ")>删除</button>";
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
		$.myAjax('../admin/store?id=' + id, 'DELETE', null,
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