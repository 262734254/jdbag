<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/admin/common/includeInit.jsp"%>
<title>走秀网-登录后台管理</title>
<link href="<%=request.getContextPath()%>/admin/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/admin/static/css/page_login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/admin/view/login.js"></script>
</head>
<body>
<form action="doLogin.action" method="post" name="user" id="user" onsubmit="return validate_login();" theme="simple">
<table width="1003" align="center" cellpadding="0" cellspacing="0" class="table">
  <tr>
    <td height="613" valign="top">

<table width="300px" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="340" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td width="100" height="36">&nbsp;</td>
    <td valign="top"><input type="text" value="" name="userName" id="loginName" onblur="checkLoginName();" class="input w130" 
		style="margin-right: 120px;"/></td>
  </tr>
  <tr>
    <td height="35">&nbsp;</td>
    <td valign="top"><input type="password" value="" name="userPwd" id="password" onblur="checkPassword();" class="input w130"
    	style="margin-right: 120px;"/></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td valign="top">
    
<!--   
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="66"><input type="text" class="input w50" /></td>
    <td width="78"><a href="#"><img src="<%=request.getContextPath()%>/admin/static/pic/vCode.gif" alt="" width="71" height="29" align="absmiddle" /></a></td>
    <td>&nbsp;</td>
  </tr>
</table>
-->
    </td>
  </tr>
  <tr>
    <td colspan="2">
    
<table width="250" border="0" cellpadding="0" cellspacing="0" class="mt15" style="margin-right: 350px">
  <tr>
    <td width="35">&nbsp;</td>
    <td width="135"><!-- <input type="checkbox" /><label for="" class="ml5">记住密码？</label> --></td>
    <td height="30"><input type="image" src="<%=request.getContextPath()%>/admin/static/img/btn_login.gif" onclick="javascript:onsubmit();" /></td>
  </tr>
  <tr>
    <td colspan="3">
    	<div style="color:red"><br><span id="loginName_info" ></span><br><span
			id="password_info"></span>
		</div>
		<div style="color:red"><br>
		<span ><s:property value="result.ResultCode"/></span>
		</div>
    </td>
  </tr>
</table>

    </td>
    </tr>
</table>

    </td>
  </tr>
</table>
</form>

</body>
</html>
