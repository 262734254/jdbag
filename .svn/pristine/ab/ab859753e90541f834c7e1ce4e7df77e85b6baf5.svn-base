//全选
function checkAll(name) {
	var names = document.getElementsByName(name);
	var len = names.length;
	if (len > 0) {
		var i = 0;
		for (i = 0; i < len; i++){
			if(names[i].disabled!=true)
				names[i].checked = true;
		}
	}
}

//全不选
function checkAllNo(name) {
	var names = document.getElementsByName(name);
	var len = names.length;
	if (len > 0) {
		var i = 0;
		for (i = 0; i < len; i++){
			if(names[i].disabled!=true)
				names[i].checked = false;
		}
	}
}

//反选
function reserveCheck(name) {
	var names = document.getElementsByName(name);
	var len = names.length;
	if (len > 0) {
		var i = 0;
		for (i = 0; i < len; i++) {
			if (names[i].checked){
				if(names[i].disabled!=true)
					names[i].checked = false;
			}
			else{
				if(names[i].disabled!=true)
					names[i].checked = true;
			}

		}
	}
}

//批量删除
function batchDelete(name,url) {

	var objarray = document.getElementsByName(name);
	var checkvalue = false;

	for ( var i = 0; i < objarray.length; i++) {

		if (objarray[i].checked) {
			checkvalue = true;
		}
	}

	if (checkvalue == false) {
		alert("请选择删除的项目");
	} else {

		if (confirm("确认删除吗？")) {
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	}
}

function replaceNotNumberByDecimal(id){
	if('' != $("#"+id).val().replace(/\d{1,}\.{0,1}\d{0,}/,'')) {
        $("#"+id).val($("#"+id).val().match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :$("#"+id).val().match(/\d{1,}\.{0,1}\d{0,}/));
    }		
}
//验证手机与电话号码
function replaceNotNumberByPhone(id){
	if('' != $("#"+id).val().replace(/\d{1,}\-{0,1}\d{0,}/,'')) {
        $("#"+id).val($("#"+id).val().match(/\d{1,}\-{0,1}\d{0,}/) == null ? '' :$("#"+id).val().match(/\d{1,}\-{0,1}\d{0,}/));
    }		
}

function replaceNotNumber(id){
	if('' != $("#"+id).val().replace(/\d{1,}/,'')) {
        $("#"+id).val($("#"+id).val().match(/\d{1,}/) == null ? '' :$("#"+id).val().match(/\d{1,}/));
    }		
}