function operate(form,url){form.action=url;}
//验证部门名称长度
function checkDeptName() {
	var deptName = $("#deptName").val().replace(/^\s+|\s+$/g, "");
	var deptName_length = deptName.replace(/[^\x00-\xff]/g, 'xx').length;
	if ((deptName ==null) || (deptName == "")) {
		$("#deptName_info").text(" 部门名称不能为空！");return false;
	} else if ((deptName_length > 30) || (deptName_length < 1)) {
		$("#deptName_info").text(" 部门名称长度在1~30位！");return false;
	} else {
		$("#deptName_info").text("");return true;
	}
}
function submitDept() {if(checkDeptName()) {return true;} else {return false;}}
//验证岗位名称长度
function checkJobName() {
	var jobName=$("#jobName").val().replace(/^\s+|\s$/g,"");
	var jobName_length = jobName.replace(/[^\x00-\xff]/g, 'xx').length;
	if(jobName=="") {
		$("#jobName_info").text("岗位名称不能为空！");return false;
	} else if(jobName_length >30 || jobName_length <1) {
		$("#jobName_info").text("岗位名称长度在1~30位！");return false;
	} else {
		$("#jobName_info").text("");return true;
	}
}
function submitJob() {if(checkJobName()){return true;} else {return false;}}