 var weeboxs=$.weeboxs;
/**
 * 批量操作未催款订单
 * @param sign: 判断为那种操作 1:批量锁定 2:批量人工催款完成  3：批量撤销订单          
 * @param str: 操作的提示语
 */
function batchAction(sign,str) {
	var objarray = document.getElementsByName("orderIdList");
	var checkvalue = false;
	var selectstr="";
	for ( var i = 0; i < objarray.length; i++) {
		if (objarray[i].checked) {
			checkvalue = true;
			selectstr+=objarray[i].value+";";
		}
	}
	if (checkvalue == false) {
		alert("请选择列表记录!");
	} else {
		if (confirm(str)) {
	        if(sign==1){
	        	weeboxs.open("lockRmdOrders.action?orderStr="+selectstr+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
			}else if(sign==2){
				weeboxs.open("batchFinishRmdOrders.action?orderStr="+selectstr+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
			}else if(sign==3){
				weeboxs.open("cancelRmdOrders.action?orderStr="+selectstr+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
			}
		}
	}
}
/**
 * 批量导出未催款excel
 * @param str
 */
function rmdOrderExcelExport(str) {
		var data=$("#rmdform").serialize();
		weeboxs.open("goRmdExcel.action?serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
}
/**
 * 手工催款
 * @param str
 */
function rmdHandCheck(page,str1,str2) {
	var mobile_format = /^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/;
	
//	if (str2 == '' || str2 == null) {
//	  alert("手机号码不能为空");
//    }else if (str2.match(mobile_format) == null) {
//		alert("手机号码格式不正确!");
//	}else{
		var selectstr=str1;
		weeboxs.open("handRmdOrders.action?pageSign="+page+"&orderId="+selectstr+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
	//}	
}
/**
 * 批量手工催款
 * @param str
 */
function batchRmdHandCheck(str) {
	var selectstr = "";//记录被选中的值

	$("[name=orderIdList]:checkbox:checked").each(function(){
	     var str1=$(this).val();
		 var str2=$(this).next().val();
		 if(str2!=null && str2!='' ){
		   selectstr+=str1+";";
		 } 
	});
	   if(selectstr==null || selectstr=='' ){
	      alert("请选择含有手机号码的列表记录!");
	   }else {
			if (confirm(str)) {
					weeboxs.open("handRmdOrders.action?orderStr="+selectstr+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
			}
	   }
	}
/**
 * 手工撤销订单
 * @param sign
 * @param str
 */
function cancelRmdOrder(page,sign,str) {
	if (confirm(str)) {
    	weeboxs.open("cancelRmdOrders.action?pageSign="+page+"&orderId="+sign+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
	}
}
/**
 * 锁定单个订单
 * @param sign
 * @param str
 */
function lockRmdOrder(page,sign,str) {
	//if (confirm(str)) {
    	weeboxs.open("lockRmdOrders.action?pageSign="+page+"&orderId="+sign+"&locked="+str+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
	//}
}
/**
 * 订单信息排序
 * @param str
 */
function ordreSortBy(obj) {
	var rowSize=$("#rowSize").val();
	var sortStr=$("#sortElem").val();
	if(sortStr==null || sortStr=='' ){
	      alert("请选择升降的条件!");
	   }else {
		document.rmdform.submit();
	   }
}
/**
 * 换页
 */
$(document).ready(function(){
	$("#rowSize").change(function(){		
	  var pagesize=$("#rowSize").val();
	  document.rmdform.submit();
    });
});

//详细页

function showLayer(url){
	weeboxs.open(url+"&serialize="+$("#rmdform").serialize() +"&date=" + new Date(), {title:'', contentType:'ajax', width:520, height:280});
}

function delayCheck(){
	var delayTime = $("#delayTime").val().replace(/^\s+|\s+$/g, "");
	if ((delayTime == null) || (delayTime == "")) {
		alert("请选择延迟撤销时间!");
		return false;
	}else{
		return true;
	}
}

