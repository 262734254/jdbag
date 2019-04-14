<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>走秀网-登录后台管理</title>
<link href="<%=request.getContextPath()%>/admin/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/admin/static/css/page_login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="template/view/login.js"></script>
</head>
<body>
<a href="checkLogin.action" target="_top" id="logout">退出登录</a>
<script type="text/javascript">
document.getElementById("logout").click();
</script>
</body>
</html>
