<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>数据查询页面</title> 
<link href="<%=request.getContextPath()%>/template/css/base.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/common.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/page_admin_main.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/template/javascript/calendar/My97DatePicker/WdatePicker.js" type="text/javascript"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/goodsList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery-1.3.2.min.js"></script>
<!-- 自动填充插件 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery.autocomplete.js"></script>


</head>
<body> 
<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
<tbody> 
<tr> 
<td class="td01"></td> 
<td class="td02"><h3 class="topTitle fb f14">数据查询页面</h3></td> 
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
	<dd>走秀网运营中心</dd>
	<dd>数据查询页面</dd>
</dl>
</div>


<form id="queryDataForm" action="<%=request.getContextPath() %>/jdGoods/getData.action" method="post">
<s:hidden name="isQueryPage" value="true"></s:hidden>
<table  class="mytable"  >
	<tr>
	
	
	<td style="width: 500px;" colspan="2">
		<span style="float: left;padding-top: 40px;"><font style="color: red">请输入查询SQL:</font></span>
				    <textarea rows="4" cols="100" id="sql" name="sql" ><s:property value="sql"/></textarea>
		 </td>
	</tr>

	<tr >
		<td ><input id="getQueryData"  type="button" value="查询"/>
		</td>
	</tr>
	
</table>
</hr>
<table class="listtable">
	<thead>
	  <s:if test="queryData.data.size()>0">
		<tr>
			<s:iterator value="queryData.columnTitles" var="columnName" >
			<th width="10%"><s:property value="#columnName" /></th>
			</s:iterator>
		</tr>
	</s:if>
	</thead>
	<tbody>
	<!-- List<List<Object>> data -->
	 <s:if test="isQueryPage eq 'true' ">
	    <s:if test="queryData.data.size()>0">
		<s:iterator value="queryData.data" var="resultObject" >
			<tr>
			 <s:iterator value="resultObject" var="rowData" >
				<td class="tlr"><s:property value="#rowData" /></td>
				
			</s:iterator>
			</tr>
		</s:iterator>
	  </s:if>
	  <s:else>
		  <s:if test="count==null || count == 0 ">
		   <tr>
		     <font style="color: red">对不起没有找到你想要的数据</font>
		  </tr>
		  </s:if>
	 </s:else>
	 
	 </s:if>
	 
	</tbody>
</table>
</form>

</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">

$("#getQueryData").click(function(){
	var sql=$('#sql').val();
	if(sql==null || $.trim(sql)==''){
		alert("请输入查询语句");
		return ;
	}
	var action = "<%=request.getContextPath()%>/jdGoods/getData.action";
	$('#queryDataForm').attr("action",action);
	$('#queryDataForm').submit();
});
</script>

</body> 
</html> 