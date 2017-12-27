
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

$.extend($, {
	/*
	 * ajax调用封装，返回json url 服务路径 data一般为js对象 callback 回调函数
	 */
	myAjax : function(url, method, body, callback) {
		$.ajax({
			url : url,
			contentType: "application/json",
			data : body,
			dataType : 'json',
			method : method,
			cache : false,
			beforeSend : function() {
				$("body").mLoading("show");
			},
			complete : function() {
				$("body").mLoading("hide");
			},
			success : function(data) {
				if (data.code === 100) {
					window.location.href = 'login.html';
				}
				if (typeof callback != 'undefined')
					callback.call(this, data);
			},
			error: function () {
				alert("异常");
			}
		});
	}
});

function loadPage(page, callback) {
	$.ajax({ 
		   type: "GET", 
		   url: page, 
		   cache:false, 
		   async:false, 
		   dataType: "html", 
		   success: function(html){ 
			   if (callback != null) {
				   callback(html);
			   }
			   
		   }
	});
}

function loadHeader() {
	loadPage('header.html', function(html) {
		$('#header').html(html);
	});
}

function loadFooter() {
	$('#footer-container').load('footer.html');
}

function loadNav() {
	loadPage('nav.html', function(html) {
		$('#nav').html(html);
	});
}

function navActive(obj) {
	obj.addClass('active');
}