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

</head>
<body> 
<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
<tbody> 
<tr> 
<td class="td01"></td> 
<td class="td02"><h3 class="topTitle fb f14">京东-走秀类目映射</h3></td> 
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
	<dd>京东-走秀类目映射 </dd>
</dl>
</div>


<form id="form" method="post" action="${path}/cate!queryXiuJdCategory.action">

<input type="hidden" id="currentPage" name="currentPage" value="${currentPage}"/>
<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>


<table class="mytable"  >
	  <tr>

		<td colspan="3">京东分类:
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
		<%-- 京东类目ID：<input name="xiuJdCategory.jdCid" value="<s:property value="xiuJdCategory.jdCid"/>"/> --%>
		
		<td>
		京东类目名称：<input name="xiuJdCategory.jdName" value="<s:property value="xiuJdCategory.jdName"/>"/>
		</td>
		<td>
		走秀类目ID：<input name="xiuJdCategory.xiuCid" value="<s:property value="xiuJdCategory.xiuCid"/>"/>
		</td>
		<td>
		走秀类目名称：<input name="xiuJdCategory.xiuName" value="<s:property value="xiuJdCategory.xiuName"/>"/>
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
			<th>京东类目ID</th>
			<th>京东类目名称</th>
			<th>京东类目位置</th>
			<th>走秀类目ID</th>
			<th>走秀类目名称</th>
			<th width="14%">操作</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="pageView.records" var="resultObject" >
			<tr id="tr_<s:property value="#resultObject.id" />">
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.jdCid" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.jdName" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.jdFullName" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuCid" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuName" />
				</td>
				<td  class="tlr" style="text-align: center">
					<a id="export" href="#">导出</a>
					&nbsp;
					<a href="javascript:toEdit(<s:property value="#resultObject.id" />);">修改</a>
					&nbsp;
					<a href="javascript:todel(<s:property value="#resultObject.id" />);">删除</a>
				</td>
			</tr>
		</s:iterator>
	</tbody>
	
	<tbody>
	<%@ include file="/admin/common/page.jsp"%>
	</tbody>
</table>
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
	if(window.confirm("你确定要删除映射的类目吗?")){
		/* $.ajax({type:'post',
			url:'${path}/cate!delXiuJdCategory.action',
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
		window.location.href="<%=request.getContextPath()%>/cate!delXiuJdCategory.action?id="+n;
		<%-- var action = "<%=request.getContextPath()%>/cate!delXiuJdCategory.action?id="+n;
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
});
function toEdit(id){
	var rs = window.showModalDialog('${path}/cate!toEditXiuJdCategory.action?id='+id+'&cur_time='+new Date(),'','dialogWidth:800px;dialogHeight:500px;status:0;');
	if("succ"==rs){
		window.location.href="${path}/cate!queryXiuJdCategory.action";
	}
}

function exportExcel(jdCid){
	window.location.href="${path}/cate!exportTemplate.action?cid="+jdCid;
}
$("#export").click(function(){
	var jdCid = $(this).parent().parent().children().first().html();
	window.location.href="${path}/cate!exportTemplate.action?cid="+jdCid;
});
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