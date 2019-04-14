<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="hd">
	<div class="logo"><a href="#"><img src="<%=request.getContextPath()%>/template/images/logo.png" border="0" /></a></div>
			<div class="userinfo">
			 您好，<font style="weight:bold;color:blue"><s:property value="#session.USER_NAME" /></font>&nbsp;欢迎您!&nbsp;&nbsp;
			<a href="checkLogin.action">退出</a>&nbsp;&nbsp;</div>
					<div class="nav">
						<ul>
						<s:iterator value="#session.labelList">
							<li><a href="<%=request.getContextPath()%>/<s:property value="pageUrl" />"><s:property value="pageName" /></a></li>
						</s:iterator>
						<!--<li><a href="toAddSupply.action">供应商添加</a></li>
						<li><a href="querySupplierList.action">供应商列表</a></li>
						<li><a href="oplog.action">供应商操作日志</a></li> 
						</ul>
					--></div>
</div>