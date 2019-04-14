<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/admin/common/includeInit.jsp"%>
<title>操作结果</title>
<link href="<%=request.getContextPath()%>/admin/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/admin/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/admin/static/css/page_admin_main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/admin/view/properties/updatepro.js"></script>
</head>
<form name="myForm1" id="myForm1">
	<body>
<table class="adminMain_top">
<tbody>
<tr>
<td class="td01"></td>
<td class="td02"><h3 class="topTitle fb f14">操作结果</h3></td>
<td class="td03"></td>
</tr>
</tbody>
</table>
<table class="adminMain_main">
<tbody>
<tr>
<td class="td01"></td>
<td class="adminMain_wrap">
<div class="wrapMain">
<!--主体内容开始-->
<form name="supplierList555" id="bd" method="post"  action="searchProMapping.action">
<div class="adminUp_wrap">
<dl class="adminPath clearfix">
<!-- 	<dt>您现在的位置：</dt>
	<dd>商品信息管理</dd>
	<dd>运营属性映射表</dd>-->
</dl> 
<fieldset class="clearfix adminSearch">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				  <tr>
					  <td><img src="<%=request.getContextPath()%>/template/images/global_success.png"/>&nbsp;<strong><s:property value="xopResult.resultMessage" /></strong></td>
				 </tr>
				 <tr>
					  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<s:property value="xopResult.backUrl" />">返回</a></td>
				</tr>
			</table>
</fieldset>
</div>
</form>
<div class="adminContent clearfix">
</div>
</div>
</td>
<td class="td03"></td>
</tr>
</tbody>
</table>

</body>
	</body>
</form>
</html>

