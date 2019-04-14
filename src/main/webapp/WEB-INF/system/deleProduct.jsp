<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>批量删除商品界面 </title> 
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
<td class="td02"><h3 class="topTitle fb f14">走秀网运营中心  > 批量删除商品界面</h3></td> 
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
	<dd>走秀网运营中心  > 批量删除商品界面 </dd>
</dl>
</div>


<form id="deleteProductInfoForm" method="post" action="<%=request.getContextPath() %>/jdGoods/deleProduct.action">
  <table style="width:100%;">
	    <tr>
	      <td style="width: 300px;">
	                  请输入商品走秀码,每个走秀码独占一行:
	       <font style="color:red">最多支持200个</font>
	      </td>
	      <td colspan="4">
	        <textarea rows="10" id="xiuCodes" cols="50" name="xiuCodes"></textarea>
	      </td>
	    </tr>

	<tr >
		<td colspan="5">
		 是否同时删除京东上的商品:<s:select id="synStatus" 
		  theme="simple" name="synStatus" headerKey="0" headerValue="否" list="#{'1':'是'}"
		   cssStyle="width:100px" value="synStatus"></s:select>
		 </td>
		
	</tr> 
</table>
<hr />

 <input id="deleteProductInfo" type="button" value="删除"/> 
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
$("#deleteProductInfo").click(function(){
	var xiuCodes=$('#xiuCodes').val();
	if(xiuCodes==null || $.trim(xiuCodes)==""){
		alert("请输入商品走秀码");
		return;
	}
	
	if(confirm("您确定要删除商品么?")){
		var synStatus=$("#synStatus").val();
		if(synStatus==1){
			if(confirm("确定连同京东上的商品都删除么?")){
				var action = "<%=request.getContextPath() %>/jdGoods/deleProduct.action";
				$('#deleteProductInfoForm').attr("action",action);
				$('#deleteProductInfoForm').submit();
			}
		}else{
			var action = "<%=request.getContextPath() %>/jdGoods/deleProduct.action";
			$('#deleteProductInfoForm').attr("action",action);
			$('#deleteProductInfoForm').submit();
		}
		
	}
	
});


</script>