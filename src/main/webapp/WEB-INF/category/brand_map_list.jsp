<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>京东-走秀类目映射 </title> 
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<link href="${path}/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="${path}/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<script src="${path}/template/javascript/jquery-1.9.1.min.js"></script>
<script src="${path}/template/javascript/bgiframe.js"></script>
<script src="${path}/template/uiplugin/weebox/src/bgiframe.js"></script>
<style type="text/css">
 .isgray{
  text-decoration:underline;
  color:gray;
  }
  
  #headSearchTable  tr td{
  padding-left: 7px;
  }
</style>
</head>
<body> 
<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
<tbody> 
<tr> 
<td class="td01"></td> 
<td class="td02"><h3 class="topTitle fb f14">京东-走秀品牌映射</h3></td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>  
<table border="0" cellspacing="0" cellpadding="0" class="adminMain_main"> 
<tbody> 
<tr> 
<td class="td01"></td> 
<td class="adminMain_wrap"> 
<div class="wrapMain" style="_height:1000px;min-height:1000px;vertical-align: middle;"> 
<!-- 在这里填充 -->
<div class="adminUp_wrap" style="height:24px;margin-bottom:10px;">
<dl class="adminPath clearfix">
	<dt>您现在的位置：</dt>
	<dd>京东-走秀品牌映射 </dd>
</dl>
</div>


<form id="form" method="post" action="${path}/cate!queryXiuJdBrand.action">

<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>


<table>
	
		<tr>
				<td style="width: 500px">京东分类:
					<s:select list="jdCategories"  id="firstCategoriesSelect"  theme="simple" name="firstCategoryId" 
					 onchange="getNextCategory(this.value,'2')" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
					
			
				    <s:select list="jdCategories2"  id="sendCategorySelect"  theme="simple" name="sendCategoryId"  value="sendCategoryId" 
				    onchange="getNextCategory(this.value,'3')" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
						
				
					
					<s:select list="jdCategories3"  id="threeCategorySelect"  theme="simple"  name="threeCategoryId" value="threeCategoryId" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
					</td>
				
		</tr>
		
		<tr>
		<td>
		京东分类名称：<input name="xiuJdBrand.jdCategoryName" value="<s:property value="xiuJdBrand.jdCategoryName"/>"/>
		</td>
		
		<%-- <td>
		京东属性值ID：<input name="xiuJdBrand.jdVid" value="<s:property value="xiuJdBrand.jdVid"/>"/>
		</td> --%>
		<td>
		京东属性值名称：<input name="xiuJdBrand.jdVname" value="<s:property value="xiuJdBrand.jdVname"/>"/>
		</td>
		<td>
		走秀品牌ID：<input name="xiuJdBrand.xiuBrandCode" value="<s:property value="xiuJdBrand.xiuBrandCode"/>"/>
		</td>
		<td>
		走秀品牌名称：<input name="xiuJdBrand.xiuBrandName" value="<s:property value="xiuJdBrand.xiuBrandName"/>"/>
		</td>
	</tr>
	<tr >
		<td>
		<input onclick="toquery();" type="button" value=" 查 询 "/>
		</td>
	</tr>
</table>
<hr />
<table class="listtable">
	<thead>
		<tr>
		   <th width="5%">选项</th>
			<th>京东类目ID</th>
			<th>京东分类名称</th>
			<th>京东属性值名称</th>
			<th>走秀品牌ID</th>
			<th>走秀品牌名称</th>
			<th width="12%">操作</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="pageView.records" var="resultObject" >
			<tr id="tr_<s:property value="#resultObject.id" />">
			     <td style="height:50px;text-align:center;" nowrap><!-- 选择 -->	
				 <input type="checkbox" name="ids"  value="<s:property value='#resultObject.id' />" />	
		       </td>
			
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.jdCid" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.jdCategoryName" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.jdVname" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuBrandCode" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuBrandName" />
				</td>
				<td  class="tlr" style="text-align: center">
					<a href="javascript:todel(<s:property value="#resultObject.id" />);">删除</a>
				</td>
			</tr>
		</s:iterator>
	</tbody>
	
	<tbody>
	<%@ include file="/admin/common/page.jsp"%>
	</tbody>
</table>

<a href="#" class="isgray"
		 <s:if test="pageView.records.size()>0">onclick="checkAll('ids')" </s:if>
		<s:else> style="text-decoration: underline;color: gray;"</s:else> 
		>全选  </a>
		<a href="#" class="isgray" <s:if test="pageView.records.size()>0">onclick="reserveCheck('ids')"</s:if> <s:else> style="text-decoration: underline;color: gray;"</s:else>  >反选 </a>
		<input  id="batchDelete"  <s:if test="pageView.records.size()<1">disabled="disabled"</s:if>  type="button" onclick="batchDeleteMapBrand()" value="批量删除"/>
</form>

</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
</body> 
</html> 
<script>
function todel(n){
	if(window.confirm("你确定要删除映射的品牌吗?")){
		/* $.ajax({type:'post',
			url:'${path}/cate!deleteXiuJdBrand.action',
			data:'id='+n,
			dataType:'text',
			success:function(msg){
				if(msg=="succ"){
					$("#tr_"+n).remove();
				}else{
					alert("删除失败!请稍后重试...");
				}
			}
		}); */
		window.location.href="<%=request.getContextPath()%>/cate!deleteXiuJdBrand.action?id="+n;
		<%-- var action = "<%=request.getContextPath()%>/cate!deleteXiuJdBrand.action?id="+n;
		$('#form').attr("action",action);
		$('#form').submit(); --%>
	}
}
function topage(page){
	$("#currentPage").val(page);
	$("#form").submit();
}
function toquery(){
	var selectCategory=true;
	var firstCategoryVal=$('#firstCategoriesSelect').val();
	 if(firstCategoryVal!=null && firstCategoryVal!=""){
		 
		 var sendCategoryVal=$('#sendCategorySelect').val();
		 
		 if(sendCategoryVal==null || sendCategoryVal==""){
			 
			 selectCategory=false;
		 }
		 
        var threeCategoryVal=$('#threeCategorySelect').val();
		 
		 if(threeCategoryVal==null || threeCategoryVal==""){
			 
			 selectCategory=false;
		 }

	 }
	 if(!selectCategory){
		 alert("请选择下分类");
		 return ;
	 }
	 
	$("#currentPage").val(1);
	$("#form").submit();
}
$(document).ready(function(){
	$("#goButton").click(function(){
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
	});
	
	
	 var isDisabled=false;
	   var names = document.getElementsByName("ids");
		var len = names.length;
		
		if (len) {
			var i = 0;
			for (i = 0; i < len; i++) {
				//复选框没有可用的
				if (names[i].disabled) {
					isDisabled=names[i].disabled;
				}else{
					isDisabled=names[i].disabled;
					break;
				}
			}
		}
		if(isDisabled){
			$("#batchDelete").attr('disabled','true');
			$(".isgray").addClass("isgray");
		}else{
			$(".isgray").removeClass("isgray");
		}
});

//全选
function checkAll(name) {
	var names = document.getElementsByName(name);
	var len = names.length;
	if (len > 0) {
		var i = 0;
		for (i = 0; i < len; i++) {
			if (names[i].disabled != true)
				names[i].checked = true;
		}
	}
}

// 反选 
function reserveCheck(name) {
	var names = document.getElementsByName(name);
	var len = names.length;
	if (len > 0) {
		var i = 0;
		for (i = 0; i < len; i++) {
			if (names[i].checked) {
				if (names[i].disabled != true)
					names[i].checked = false;
			} else {
				if (names[i].disabled != true)
					names[i].checked = true;
			}

		}
	}
}

//批量删除映射的品牌
function batchDeleteMapBrand(){
	   var isSelected=false;

		   //为批量删除,需要判断复选框是否有被选中了
			   var names = document.getElementsByName("ids");
				var len = names.length;
				if (len) {
					var i = 0;
					for (i = 0; i < len; i++) {
						//复选框有被选中
						if (names[i].checked) {
							isSelected=true;
						}
					}
				}else{
					//一个的情况
					isSelected=names.checked
				}
		   
	   
	   
	   if(isSelected){
		  
		  var isConfirm= confirm("您确定要执行此操作么?");
		   if(isConfirm){
			   
			  var requestPath= "<%=request.getContextPath()%>/cate!batchDeleteBrand.action"
		     
				form.action =requestPath ;
				form.submit();
		   }
		   
	   }else{
		   alert("请选中操作项");
	   }
}

//根据京东父分类查询下级分类
function getNextCategory(categoryId,num){
	var sendCategorySelect=$("#sendCategorySelect");
	var threeCategorySelect=$("#threeCategorySelect");
	if(categoryId==null || categoryId==""){
		sendCategorySelect.hide();
		threeCategorySelect.hide();
	}
	
	if(num==2){
		sendCategorySelect.empty();
		threeCategorySelect.empty();
	}
	
	if(num==3){
		threeCategorySelect.empty();
		
	}
	$.ajax({type:'post',
		url:'<%=request.getContextPath()%>/jdGoods/getJdCategory.action',
		data:'categoryId='+categoryId,
		dataType:'text',
		success:function(data){
			var jsonObj = eval(''+data+'');
			if(jsonObj!=null && jsonObj!=""){
			      
				if(num==2){
				$("<option>").val("").text("==请选择分类==").appendTo("#sendCategorySelect");
				sendCategorySelect.show();
				threeCategorySelect.hide();
				}
				if(num==3){
					$("<option>").val("").text("==请选择分类==").appendTo("#threeCategorySelect");
					threeCategorySelect.show();
					
				}
				
			}
			 for(var i=0;i<jsonObj.length;i++){
				var name=jsonObj[i].name;
				var id=jsonObj[i].id;
				var option=$("<option>");
				if(num==2){
					$(option).val(id).text(name).appendTo("#sendCategorySelect");
				}
				if(num==3){
					$(option).val(id).text(name).appendTo("#threeCategorySelect");
				}
			} 
		}
	});
	
}
</script>