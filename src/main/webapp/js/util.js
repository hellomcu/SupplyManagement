
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
				if (typeof callback != 'undefined')
					callback.call(this, data);
			},
			error: function () {
				alert("异常");
			}
		});
	}
});