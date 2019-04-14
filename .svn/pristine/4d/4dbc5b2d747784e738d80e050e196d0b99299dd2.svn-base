<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>京东-走秀类目映射 </title>
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<link href="${path}/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="${path}/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<script src="${path}/template/javascript/jquery-1.9.1.min.js"></script>
<script src="${path}/template/javascript/bgiframe.js"></script>
<script src="${path}/template/uiplugin/weebox/src/bgiframe.js"></script>
<style>
.sp{
	width:200px;
	height:200px;
	background-color:#ffffff;
	border: 1px solid #000000;
	overflow-y: scroll;
	overflow-x: hidden;
	display: inline-block;
}
li{
	cursor: hand;
}
</style>
<script type="text/javascript"> 
$(document).ready(function() {
	
});
function xiu_liclk(obj){
	$(obj).parent().children().css("background-color","#ffffff");
	$(obj).parent().children().css("color","#666");
	$(obj).css("background-color","#666");
	$(obj).css("color","#ffffff");
	toNextXiuCls(obj);
}
function toNextXiuCls(obj){
	var curCode = "";
	var parentCode = "";
	if($(obj).parent().attr("id")=="xiu_cid_1"){
		curCode = "family";
		parentCode = "super";
		$("#xiu_cid_2").empty();
		$("#xiu_cid_3").empty();
	}else if($(obj).parent().attr("id")=="xiu_cid_2"){
		curCode = "class";
		parentCode = "family";
		$("#xiu_cid_3").empty();
	}else if($(obj).parent().attr("id")=="xiu_cid_3"){
		toChosXIU(obj);
		return;
	}
	$.ajax({type:'post',
		url:'${path}/cate!searchXiuCategory.action',
		data:'curCode='+curCode+'&parentCode='+parentCode+'&paramCode='+$(obj).attr("id"),
		dataType:'text',
		success:function(msg){
			if($(obj).parent().attr("id")=="xiu_cid_1"){
				$("#xiu_cid_2").append(msg);
			}
			if($(obj).parent().attr("id")=="xiu_cid_2"){
				$("#xiu_cid_3").append(msg);
			}
		}
	});
}
var id;
var name;
function toChosXIU(obj){
	id=$(obj).attr("id");
	name=$(obj).attr("title");
}
function tosbm(){
	var obj = new Object();
	obj.id = id;
	obj.name=name;
	window.returnValue=obj;
	window.close();
}
</script>
</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">类目管理</h3></td>
				<td class="td03"></td>
			</tr>
		</tbody>
	</table>
	<table class="adminMain_main" style="width: 100%;">
		<tbody>
			<tr>
				<td class="td01"></td>				
				<td class="adminMain_wrap" >
					<div class="wrapMain">
							<div class="adminUp_wrap">
								<dl class="adminPath clearfix">
									<dt>您现在的位置：</dt>
									<dd>京东-走秀类目映射 </dd>
									<dd>走秀类目 </dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
<div class="adminContent" style="font-size: 15px;font-weight: bolder;">		
<!--主体内容开始-->
<form id="form" method="post" action="${path}/cate!refCategory.action">
<p>走秀类目：</p>
<span>	
   <ul class="sp" id="xiu_cid_1">
		<c:forEach items="${xiuClzList}" var="item">
		<li onclick="xiu_liclk(this)" title="${item.name}" id="${item.code}">${item.name}<span style="float:right;">></span></li>
		</c:forEach>
	</ul>
	&nbsp;&nbsp;
	<ul class="sp" id="xiu_cid_2">
	</ul>
	&nbsp;&nbsp;
	<ul class="sp" id="xiu_cid_3">
	</ul>
</span>
<center>
<p>&nbsp;</p>
<p>
	<input id="btn" type="button" onclick="tosbm();" value=" 确 定 " style="width:100px;height:30px;"/>
</p>
</center>
</form>
<p>&nbsp;</p>
<p>&nbsp;</p>
<!--主体内容结束-->
</div>	
						
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
