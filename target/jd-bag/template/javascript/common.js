/*
 * 公共JS方法
 */

/*
 * 批量输入以换行组装成字符串
 */

	

function batchInputGenerator(inputId, hiddenId, limit, msg) {
	limit = limit == '' ? 100 : limit;
	msg = msg == '' ? '商品SN' : msg;
	var inputValue = $.trim($("#" + inputId).val());
	if ($.trim(inputValue) != '') {
		var reg = new RegExp("[\s　]*[\r\n]+[\s　]*", "g");
		inputValue = $.trim(inputValue.replace(reg, ";"));
 		inputValue = inputValue.replace(/[ ]/g,"");
   		$("#" + hiddenId).attr("value", inputValue);
	}
	var inputLength = inputValue.split(";");
	if (inputLength.length > limit) {
		alert("你输入的" + msg + "个数不能超过" + limit + "个!");
		return false;
	}
	return true;

}


