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
<td class="td02"><h3 class="topTitle fb f14">走秀网运营中心  > 通知邮件配置</h3></td> 
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
	<dd>走秀网运营中心  > 通知邮件配置 </dd>
</dl>
</div>


<form id="updateEmailInfoForm" method="post" action="<%=request.getContextPath() %>/jdProduct/updateOrderEmail。action">
  <table style="width:100%;">
    <tr>
         <td style="width: 300px;">
                                      下单异常通知邮箱：（多个邮箱用分号分隔。如：admin@xiu.com;xop@xiu.com ）
	      </td>
	</tr>
	    <tr>
	    
	      <td colspan="4">
	        <textarea rows="5" id="emailAddress" cols="80" name="emailAddress">${emailAddress}</textarea>
	      </td>
	    </tr>

</table>
<hr />

 <input id="upadteEmailAddress" type="button" value="提交"/> 
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
$("#upadteEmailAddress").click(function(){
	var emailAddress=$('#emailAddress').val();
	if(emailAddress==null || $.trim(emailAddress)==""){
		alert("请输入邮箱地址");
		return;
	}
	
	if(confirm("确定要修改邮箱吗?")){
		var action = "<%=request.getContextPath() %>/jdProduct/updateOrderEmail.action";
		$('#updateEmailInfoForm').attr("action",action);
		$('#updateEmailInfoForm').submit();
	}
	
});


</script>