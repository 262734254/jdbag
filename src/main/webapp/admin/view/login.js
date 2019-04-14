function checkLoginName() {
	var realname = $("#loginName").val().replace(/^\s+|\s+$/g, "").trim();
	var realname_length = realname.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((realname == null) || (realname == "")) {
		$("#loginName_info").text(" 登录名不能为空");
		return false;
	} else if ((realname_length > 20) || (realname_length < 4)) {
		$("#loginName_info").text("  登录姓名长度为4－20个字符");
		return false;
	} else {
		$("#loginName_info").text("");
		return true;
	}
}
function checkPassword() {

	var password = $("#password").val().replace(/^\s+|\s+$/g, "");
	var password_length = password.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((password == null) || (password == "")) {
		$("#password_info").text("  密码不能为空");
		return false;
	} else if ((password_length < 6) || (password_length > 20)) {
		$("#password_info").text("  密码长度在6-20个字符之间");
		return false;
	} else {
		$("#password_info").text("");
		return true;
	}
}
/**
 * 登录验证
 * @returns {Boolean}
 */
function validate_login() {
	var ck1 = checkLoginName();
	var ck2 = checkPassword();
	if (ck1 && ck2) {
		return true;
	} else {
		return false;
	}
}
function onsubmit()
{
	document.getElementById("user").submit();	
}




