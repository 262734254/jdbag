<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>修改订单备注</title>
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<link href="${path}/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="${path}/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<link href="${path}/resources/style/order_list.css" rel="stylesheet" type="text/css" />
<script src="${path}/template/javascript/jquery-1.9.1.min.js"></script>
<script src="${path}/template/javascript/bgiframe.js"></script>
<script src="${path}/template/uiplugin/weebox/src/bgiframe.js"></script>
<script>
$(document).ready(function(){
	window.name="orderRemark";
	<c:if test="${oper_rs}">
	alert("修改成功!");
	window.close();
	</c:if>
});
function tosbm(){
	if($.trim($("#remark").val())==""){
		return false;
	}
	return true;
}
</script>
</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">订单管理</h3></td>
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
									
									<dd>修改订单备注 </dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
<div class="adminContent" >		
<font style="font-size: 15px;font-weight: bolder;">订单备注：</font>
<hr/>
<form target="orderRemark" action="${path}/order!modifyOrderRemark.action" method="post" onsubmit="return tosbm();">
<table style="margin-top: 0px;margin-left: 50px;" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td style="text-align:right;vertical-align: top;">订单备注：</td>
		<td style="text-align: left;">
			<textarea id="remark" name="remark" rows="5" cols="50"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align: center;">
			<p>&nbsp;</p>
			<input value="确定" type="submit" style="width:60px;height: 25px;"/>
			<input value="取消" onclick="window.close();" type="button" style="width:60px;height: 25px;"/>
		</td>
	</tr>
</table>
</form>
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
