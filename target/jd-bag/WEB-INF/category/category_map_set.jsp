<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/struts-dojo-tags" prefix="dj"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>京东-走秀类目映射 </title>
<dj:head />
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
	width:220px;
	height:220px;
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
function liclk(obj){
	$(obj).parent().children().css("background-color","#ffffff");
	$(obj).parent().children().css("color","#666");
	$(obj).css("background-color","#666");
	$(obj).css("color","#ffffff");
	toNextCls(obj);
}
var jdname1;
var jdname2;
function toNextCls(obj){
	if($(obj).parent().attr("id")=="jd_cid_1"){
		jdname1=$(obj).attr("title");
		$("#jd_cid_2").empty();
		$("#jd_cid_3").empty();
	}else if($(obj).parent().attr("id")=="jd_cid_2"){
		jdname2=$(obj).attr("title");
		$("#jd_cid_3").empty();
	}else if($(obj).parent().attr("id")=="jd_cid_3"){
		toChosJD(obj);
		return;
	}
	
	$.ajax({type:'post',
		url:'${path}/cate!searchCategorysByFid.action',
		data:'fid='+$(obj).attr("id"),
		dataType:'text',
		success:function(msg){
			if($(obj).parent().attr("id")=="jd_cid_1"){
				$("#jd_cid_2").append(msg);
			}
			if($(obj).parent().attr("id")=="jd_cid_2"){
				$("#jd_cid_3").append(msg);
			}
		}
	});
}
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
var index=0;
var sn = false;
function toChosJD(obj){
	if(sn){
		return;
	}
	index++;
	var msg = "";
	msg+="<tr id='tr_"+index+"'>"
		+"<td align='right'>"+$(obj).attr("title")+"</td>"
		+"<td align='center'>&nbsp;<font style='color: green;'><--JD&nbsp;|<a href='javascript:todel("+index+");'>删除</a>|&nbsp;XIU--></font>&nbsp;</td>"
		+"<td align='left' id='td_"+index+"'>"
		+"?"
		+"</td>"
		+"</tr>";
	$("#tbl").append(msg);
	$("form").append("<input name='refdata' type='hidden' id='hid_"+index+"' value='"+$(obj).attr("id")+"#"+$(obj).attr("title")+"#"+jdname1+"-"+jdname2+"-"+$(obj).attr("title")+"#'/>");
	sn = true;
}
function toChosXIU(obj){
	if(!sn){
		return;
	}
	$("#td_"+index).html($(obj).attr("title"));
	$("#hid_"+index).val($("#hid_"+index).val()+$(obj).attr("id")+"#"+$(obj).attr("title"));
	sn = false;
}

function xiubrand_liclk(obj){
	var brandCode = $($(obj).children().get(0)).text();
	var brandName = $($(obj).children().get(1)).text();
	//alert(brandCode);
	//alert(brandName);
	$(obj).parent().children().css("background-color","#ffffff");
	$(obj).parent().children().css("color","#666");
	$(obj).css("background-color","#666");
	$(obj).css("color","#ffffff");
	toChosbrandXIU(obj,brandCode,brandName);
}

function toChosbrandXIU(obj,brandCode,brandName){
	if(sn){
		return;
	}
	$("#td_"+index).html($("#td_"+index).html()+brandName);
	$("#hid_"+index).val($("#hid_"+index).val()+"(brand"+$.trim(brandCode)+"=="+$.trim(brandName)+")");
}

function todel(n){
	$("#tr_"+n).remove();
	$("#hid_"+n).remove();
}
function tosbm(){
	if(window.confirm("确定提交")){
		$("#btn").attr("disabled",true);
		$("#form").attr("action","${path}/cate!refCategory.action");
		$("#form").submit();
	}
}

function tosearch_x(){
	$("#currentPage").val(1);
	$("#form").attr("action","${path}/cate!queryXiuBrand.action");
}
function topage(page){
	$("#currentPage").val(page);
	dojo.event.topic.publish("to_query_xiu");
}
function topage2(){
	var page = $("#goPageno").val();
	if (! /[0-9]+/.test(page)) {
		alert("请输入数字");
		$("#goPageno").val('');
		return;
	}
	
	page = parseInt(page);
	var totalPage = ${pageView.totalpage};
	
	if (page > totalPage) {
		page = totalPage;
	}
	topage(page);
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
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
<div class="adminContent" style="font-size: 15px;font-weight: bolder;">		
<!--主体内容开始-->
<form id="form" method="post" action="${path}/cate!queryXiuBrand.action">
<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
<p>京东类目：</p>
<span>
	<ul class="sp" id="jd_cid_1">
		<c:forEach items="${jdClzList}" var="item">
			<li onclick="liclk(this)" title="${item.name}" id="${item.id}">${item.name}<span style='float:right;'>&gt;</span></li>
		</c:forEach>
	</ul>
	&nbsp;&nbsp;
	<ul class="sp" id="jd_cid_2">
	</ul>
	&nbsp;&nbsp;
	<ul class="sp" id="jd_cid_3">
	</ul>
</span>	
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

<p>走秀-品牌：</p>
<span>	
   <table>
   	<tr>
   		<td>
   			品牌ID:<input id="xiuBrandCode" name="xiuJdBrand.xiuBrandCode"/>
   			品牌名称:<input id="xiuBrandName" name="xiuJdBrand.xiuBrandName"/>
   			&nbsp;
   			<dj:submit listenTopics="to_query_xiu" onclick="tosearch_x();" targets="xiu_brand_span" value=" 查 询 " formId="form"></dj:submit>
   		</td>
   	</tr>
   </table>
   <br/>
   <div id="xiu_brand_span">
   <table class="listtable">
		<thead>
			<tr>
				<th>走秀品牌ID</th>
				<th>走秀品牌名称</th>
			</tr>
		</thead>
		<tbody id="tg_tbd">
		<s:iterator value="pageView.records" var="resultObject" >
		             
			<tr style="cursor:hand;" onclick='xiubrand_liclk(this);'
			 id='tr_<s:property value="#resultObject.id" />' >
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuBrandCode" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuBrandName" />
				</td>
			</tr>
		</s:iterator>
		</tbody>
	
		<tbody>
		<%@ include file="/WEB-INF/category/page.jsp"%>
		</tbody>
	</table>
	</div>
</span>

<p>已选择映射：</p>
<table align="center" id="tbl">
</table>
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
