 var weeboxs=$.weeboxs;

/**
 * 提现审核通过
 * @param sign
 * @param str
 */
function passCheck(sign,str) {
	if (confirm(str)) {
    	weeboxs.open("billPassCheck.action?drawId="+sign+"&serialize="+$("#drawform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
	}
}
/**
 * 审核不通过
 * @param str1
 * @param str2
 */
function checkFail(str1,str2){
	//weeboxs.open(url+"&serialize="+$("#drawform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
	window.location.href="toBillCheckDraw.action?drawId="+str1+"&drawApplyDO.contactInfo="+str2;
}
/**
 * 已返款
 * @param str1
 * @param str2
 */
function drawFinish(str1,str2){
	window.location.href="toDrawFinish.action?drawId="+str1+"&drawApplyDO.contactInfo="+str2;
}
/**
 * 返款成功
 * @param sign
 * @param str
 */
function drawSuccess(sign,str) {
	if (confirm(str)) {
    	weeboxs.open("drawApplySuccess.action?drawId="+sign+"&serialize="+$("#drawform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
	}
}
/**
 * 返款失败
 * @param sign
 * @param str
 */
function drawFail(sign,str) {
	if (confirm(str)) {
    	weeboxs.open("drawApplyFail.action?drawId="+sign+"&serialize="+$("#drawform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
	}
}
/**
 * 提现客服备注
 * @returns {Boolean}
 */
function drawMemoSub() {
	var drawMemo = $("#drawMemo").val().replace(/^\s+|\s+$/g, "");
	var drawMemo_length = drawMemo.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((drawMemo == null) || (drawMemo == "")) {
		alert("提现备注不能为空");
		return false;
	}else if (drawMemo_length > 200) {
		alert("提现备注不能超过200个字符");
		return false;
	} else {
		return true;
	}
}
/**
 * 
 * @returns {Boolean}
 */
function smsMessageSub() {
	var drawMemo = $("#smsMemo").val().replace(/^\s+|\s+$/g, "");
	var drawMemo_length = drawMemo.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((drawMemo == null) || (drawMemo == "")) {
		alert("备注不能为空");
		return false;
	}else if (drawMemo_length > 100) {
		alert("备注不能超过100个字符");
		return false;
	} else {
		return true;
	}
}
//  ----------添加提现验证--------------------------------------------------------

function checkApplyAmount() {
	//var pricetype = /^[1-9]\d{0,5}$/;
	var pricetype = /^([0-9]\d{0,5})((\.[\d]{1,2})|(.{0,0}))$/;
	var realname = $("#applyAmount").val().replace(/^\s+|\s+$/g, "");
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#applyAmount_info").text(" 提现金额不能为空");
		return false;
	}else if (realname.match(pricetype)==null) {
		$("#applyAmount_info").text("提现金额必需大于0小于1,000,000数字,小数点后最多保留2位");
		return false;
	}else if(realname==0){
		$("#applyAmount_info").text(" 提现金额不能为0");
	} else {
		$("#applyAmount_info").text("");
		return true;
	}
}

function checkSignName() {
	var realname = $("#signName").val().replace(/^\s+|\s+$/g, "");
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#signName_info").text("开户人不能为空");
		return false;
	} else if ((realname_length > 50) || (realname_length < 2)) {
		$("#signName_info").text("开户人在2-50个字符之间");
		return false;
	} else {
		$("#signName_info").text("");
		return true;
	}
}

function checkBankName() {
	var realname = $("#bankName").val().replace(/^\s+|\s+$/g, "");
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#bankName_info").text("开户银行不能为空");
		return false;
	} else if ((realname_length > 100) || (realname_length < 2)) {
		$("#bankName_info").text("开户银行在2-100个字符之间");
		return false;
	} else {
		$("#bankName_info").text("");
		return true;
	}
}
function checkBankAccount() {
	var realname = $("#bankAccount").val().replace(/^\s+|\s+$/g, "");
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#bankAccount_info").text("银行账号不能为空");
		return false;
	} else if (realname_length > 50) {
		$("#bankAccount_info").text("银行账号小于50个字符");
		return false;
	} else {
		$("#bankAccount_info").text("");
		return true;
	}
}

function checkContactInfo() {
	var contactInfo = $("#contactInfo").val().replace(/^\s+|\s+$/g, "");
	var realname_length = contactInfo.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((contactInfo == null) || (contactInfo == "")) {
		$("#contactInfo_info").text("申请人联系方式不能为空");
		return false;
	} else if (realname_length > 20) {
		$("#contactInfo_info").text("申请人联系方式小于20个字符");
		return false;
	} else {
		$("#contactInfo_info").text("");
		return true;
	}
}

function checkDrawReason() {
	var realname = $("#drawReason").val().replace(/^\s+|\s+$/g, "");
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#drawReason_info").text("提现原因不能为空");
		return false;
	} else if (realname_length >200) {
		$("#drawReason_info").text("提现原因在200字符之内");
		return false;
	} else {
		$("#drawReason_info").text("");
		return true;
	}
}

function checkUserName() {
	var realname = $("#userName").val().replace(/^\s+|\s+$/g, "");
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#userName_info").text("用户名称不能为空");
		return false;
	} else if (realname_length > 30) {
		$("#userName_info").text("用户名称小于30个字符");
		return false;
	} else {
		$("#userName_info").text("");
		return true;
	}
}
var pricetype = /^([1-9]\d*|[0])((\.[\d]{1,2})|(.{0,0}))$/;
function checkAccountTotal() {
	var realname = $("#accountTotal").val().replace(/^\s+|\s+$/g, "");
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#accountTotal_info").text("可退金额不能为空");
		return false;
	} else if (realname.match(pricetype)==null) {
		$("#accountTotal_info").text("可退金额存在错误");
		return false;
	} else {
		$("#accountTotal_info").text("");
		return true;
	}
}

/**
 * 添加提现验证
 * @returns {Boolean}
 */
function drawApplyCheck() {

	var ck1 = checkApplyAmount();
	var ck2 = checkSignName();
	var ck3 = checkBankName();
	var ck4 = checkBankAccount();
	var ck5 = checkContactInfo();
	var ck6 = checkDrawReason();
	var ck7 = checkUserName();
	var ck8 = checkAccountTotal();
	var accountTotal = $("#accountTotal").val().replace(/^\s+|\s+$/g, "");
	var applyAmount = $("#applyAmount").val().replace(/^\s+|\s+$/g, "");
	if(parseInt(applyAmount) > parseInt(accountTotal)){
		$("#pay_info").text("可退金额不能大于可提现总金额");
		return false;
	}
	if (ck1 && ck2 && ck3 && ck4 && ck5 && ck6 && ck7 && ck8) {
		return true;
	} else {
		return false;
	}
}