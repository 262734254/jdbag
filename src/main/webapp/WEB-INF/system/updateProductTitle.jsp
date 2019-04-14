<%@page import="java.net.URLDecoder"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>商品标题修改界面 </title> 
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
<td class="td02"><h3 class="topTitle fb f14">走秀网运营中心  > 商品标题修改界面</h3></td> 
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
	<dd>走秀网运营中心  > 商品标题修改界面 </dd>
</dl>
</div>


<form id="updateProductInfoForm" method="post" action="<%=request.getContextPath() %>/jdGoods/updateProductTitle.action">
  <table style="width:100%;">
	    <tr>
	      <td style="width: 300px;">
	                京东商品ID,每个商品ID独占一行:
	       <font style="color:red">最多支持200个</font>
	      </td>
	      <td colspan="4">
	        <textarea rows="10" id="jdwardIds" cols="50" name="jdwardIds"></textarea>
	      </td>
	    </tr>
      
	<tr >
		<td colspan="5">
		<!-- 选择 -1,  添加前缀 1 ,删除前缀 2 ，删除包含 3 -->
		   商品标题 <s:select id="titleStatus" 
		  theme="simple" name="titleStatus"  headerKey="-1" headerValue="选择" list="#{'1':'添加','2':'删除','3':'删除包含','4':'替换指定字符'}"
		   cssStyle="width:120px" value="titleStatus"></s:select>
		   前缀<input name="titlePrefix"  id="titlePrefix"/>
		 <span id="deletePre" style="display:none">同时删除之前前缀<input type="text" name="deletePreFix" /></span>
		  <span id="replace" style="display:none">替换后标题<input type="text" name="replaceTitle" /></span>
		 </td>
		
	</tr> 
</table>
<hr />

 <input id="updateProductInfo" type="button"  value="更新"/> 
</form>

</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
</body> 
</html> 
<script type="text/javascript">
$("#updateProductInfo").click(function(){

	
	if(confirm("您确定要更新商品么?")){
		 var titleStatus=$("#titleStatus").val();
		if(titleStatus==-1){
			alert("请选择操作类型");
			return ;
		}
		var titlePrefix=$("#titlePrefix").val();
		if(titlePrefix==null || $.trim(titlePrefix)==""){
			alert("请输入商品名称前缀");
			$("#titlePrefix").focus();
			return;
		}
		<%--
		var action = "<%=request.getContextPath() %>/jdGoods/updateProductTitle.action";
		$('#updateProductInfoForm').attr("action",action);
		$('#updateProductInfoForm').submit(); --%>
		$('#updateProductInfo').attr("disabled",true);
		
		var data=$('#updateProductInfoForm').serialize();
		$.ajax({type:'post',
			url:'<%=request.getContextPath() %>/jdGoods/updateProductTitle.action',
			data:data,
			dataType:'text',
			success:function(data){
				$('#updateProductInfo').attr("disabled",false);
				if(data==0){
					alert("走秀码数量超出200个");
				}else if(data==-1){
					alert("更新商品标题异常");
				}else if(data==1){
					alert("更新商品标题成功");
				}
			    
				
			}
		});
	}
	
});

$("#titleStatus").click(function(){
	
	var titleStatus=$("#titleStatus").val();
	if(titleStatus==1){
		$("#deletePre").show();		
		
	}
	else if(titleStatus==4){
		$("#replace").show();		
		
	}
	else{
		$("#deletePre").hide();
	}
	
})

</script>