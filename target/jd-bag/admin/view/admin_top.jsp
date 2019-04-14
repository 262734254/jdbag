<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/admin/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/admin/static/css/page_admin.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="adminTop">
<div class="adminTop_left"><img src="<%=request.getContextPath()%>/admin/static/img/admin_logo.gif" alt="" /></div>
<div class="adminTop_right">
<p class="rTop">
    <span>欢迎您：<strong class="fma f14 fb"><s:property value="#session.USER_NAME" /></strong></span>
    <span><a href="checkLogin.action" target="_top">退出登录</a></span>
</p>
<!--<p class="lh30"><a href="12.html" target="mainFrame">公共样式库</a></p>-->
<!--adminTop_right--></div>
<!--adminTop--></div>
</body>
</html>
