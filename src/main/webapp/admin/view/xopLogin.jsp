<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>走秀网-后台管理系统</title>
<link href="<%=request.getContextPath()%>/admin/static/css/base.css" rel="stylesheet" type="text/css" />
</head>
<frameset rows="58,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="admin_top.action" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="180,*" frameborder="no" border="0" framespacing="0">
    <frame src="admin_left.action" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" title="leftFrame" />
        <frameset cols="10,*" frameborder="no" border="0" framespacing="0">
            <frame src="admin_center.action" name="centerFrame" scrolling="no" id="centerFrame" title="centerFrame" />
            <frameset rows="*,26" cols="*" frameborder="no" border="0" framespacing="0">
                <frame src="admin_main.action" name="mainFrame" id="mainFrame" title="mainFrame" />
                <frame src="admin_bottom.action" name="bottomFrame" scrolling="no" id="bottomFrame" title="bottomFrame" />
            </frameset>
        </frameset>
  </frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>