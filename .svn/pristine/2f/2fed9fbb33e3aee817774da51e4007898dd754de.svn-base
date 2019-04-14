function replaceNotNumber(){
	if('' != $("#pageAmount").val().replace(/\d{1,}\.{0,1}\d{0,}/,'')) {
        $("#pageAmount").val($("#pageAmount").val().match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :$("#pageAmount").val().match(/\d{1,}\.{0,1}\d{0,}/));
    }		
}
//排序控制的升倒序是否可用
if($("#sortStr").val() == "") {
	$("#orderByasc").attr("disabled","true");
	$("#orderBydesc").attr("disabled","true");
} else {
	$("#orderByasc").attr("disabled","");
	$("#orderBydesc").attr("disabled","");
}		
//页面加载完成后根据情况选择是否可用的时间控件
if($("#timeFlag").val() == ""){
	$("#beginTimeString").attr("disabled","true");
	$("#endTimeString").attr("disabled","true");
	$("#beginTimeString").val("");
	$("#endTimeString").val("");
}else {
	$("#beginTimeString").attr("disabled","");
	$("#endTimeString").attr("disabled","");
}
//金额选择控件
if($("#amountType").val() == ""){
		$("#boundFlag").attr("disabled","true");
		$("#pageAmount").attr("disabled","true");
		$("#boundFlag").val("");
		$("#pageAmount").val("");				
}else {
		$("#boundFlag").attr("disabled","");
		$("#pageAmount").attr("disabled","");
}
//联系方式控制
if($("#telId").val() == "") {
	$("#telNum").attr("disabled","true");
	$("#telNum").val("");
}else {
	$("#telNum").attr("disabled","");
}
//金额输入控件
if($("#boundFlag").val() == "") {
	$("#pageAmount").attr("disabled","true");
}else {
	$("#pageAmount").attr("disabled","");
}

//时间控件是否可用
$("#timeFlag").change(function(){
	if($("#timeFlag").val() == ""){
		$("#beginTimeString").attr("disabled","true");
		$("#endTimeString").attr("disabled","true");
		$("#beginTimeString").val("");
		$("#endTimeString").val("");
	}else {
		$("#beginTimeString").attr("disabled","");
		$("#endTimeString").attr("disabled","");
		}
})
//时间控件选择是否可用
$("#amountType").change(function(){
	if($("#amountType").val() == ""){
			$("#boundFlag").attr("disabled","true");
			$("#pageAmount").attr("disabled","true");
			$("#boundFlag").val("");
			$("#pageAmount").val("");				
	}else {
		$("#boundFlag").attr("disabled","");
	}
})
//时间输入是否可用
$("#boundFlag").change(function(){
	if($("#boundFlag").val() == "") {
		$("#pageAmount").attr("disabled","true");
		$("#pageAmount").val();
	}else {
		$("#pageAmount").attr("disabled","");
	}
})
//根据选择的改变来升降
$("#sortStr").change(function(){
	if($("#sortStr").val() == "") {
		$("#orderByasc").attr("disabled","true");
		$("#orderBydesc").attr("disabled","true");
	} else {
		$("#orderByasc").attr("disabled","");
		$("#orderBydesc").attr("disabled","");
	}
})
$("#telId").change(function(){
	//联系方式控制
	if($("#telId").val() == "") {
		$("#telNum").val("");
		$("#telNum").attr("disabled","true");
	}else {
		$("#telNum").attr("disabled","");
	}
})