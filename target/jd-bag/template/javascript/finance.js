function replaceNotNumber(){
	if('' != $("#pageAmount").val().replace(/\d{1,}\.{0,1}\d{0,}/,'')) {
        $("#pageAmount").val($("#pageAmount").val().match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :$("#pageAmount").val().match(/\d{1,}\.{0,1}\d{0,}/));
    }		
}
//������Ƶ��������Ƿ����
if($("#sortStr").val() == "") {
	$("#orderByasc").attr("disabled","true");
	$("#orderBydesc").attr("disabled","true");
} else {
	$("#orderByasc").attr("disabled","");
	$("#orderBydesc").attr("disabled","");
}		
//ҳ�������ɺ�������ѡ���Ƿ���õ�ʱ��ؼ�
if($("#timeFlag").val() == ""){
	$("#beginTimeString").attr("disabled","true");
	$("#endTimeString").attr("disabled","true");
	$("#beginTimeString").val("");
	$("#endTimeString").val("");
}else {
	$("#beginTimeString").attr("disabled","");
	$("#endTimeString").attr("disabled","");
}
//���ѡ��ؼ�
if($("#amountType").val() == ""){
		$("#boundFlag").attr("disabled","true");
		$("#pageAmount").attr("disabled","true");
		$("#boundFlag").val("");
		$("#pageAmount").val("");				
}else {
		$("#boundFlag").attr("disabled","");
		$("#pageAmount").attr("disabled","");
}
//��ϵ��ʽ����
if($("#telId").val() == "") {
	$("#telNum").attr("disabled","true");
	$("#telNum").val("");
}else {
	$("#telNum").attr("disabled","");
}
//�������ؼ�
if($("#boundFlag").val() == "") {
	$("#pageAmount").attr("disabled","true");
}else {
	$("#pageAmount").attr("disabled","");
}

//ʱ��ؼ��Ƿ����
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
//ʱ��ؼ�ѡ���Ƿ����
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
//ʱ�������Ƿ����
$("#boundFlag").change(function(){
	if($("#boundFlag").val() == "") {
		$("#pageAmount").attr("disabled","true");
		$("#pageAmount").val();
	}else {
		$("#pageAmount").attr("disabled","");
	}
})
//����ѡ��ĸı�������
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
	//��ϵ��ʽ����
	if($("#telId").val() == "") {
		$("#telNum").val("");
		$("#telNum").attr("disabled","true");
	}else {
		$("#telNum").attr("disabled","");
	}
})