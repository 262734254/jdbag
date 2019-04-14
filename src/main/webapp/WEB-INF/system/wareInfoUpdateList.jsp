<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>商品更新信息</title> 
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
				<td class="td02"><h3 class="topTitle fb f14">商品更新信息</h3></td> 
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
								<dd>商品更新信息</dd>
							</dl>
						</div>
	<form id="wareInfoForm" method="post" action="<%=request.getContextPath() %>/jdGoods/wareInfoUpdateList.action">
		<input type="hidden" name="currentPage" value="${currentPage }"/>
		<input type="hidden" name="pageSize" value="${pageSize}"/>
		
		
		<s:if test="status!=0">
		<input id="flag" type="hidden" name="flag" value="flag"/>
		<table class="mytable">
			<tr>
				<%-- <td>导入excel的批次号&nbsp;<s:textfield id="num" theme="simple"  name="num"></s:textfield></td> --%>
				<td style="width: 250px">京东商品ID&nbsp;<s:textfield id="jdWareId" theme="simple" name="jdProductInfoForm.jdWareId" /></td>
				</td>
				<td style="width: 250px">商品标题前缀&nbsp;<input id="prJdTitle"  value=""/></td>
			</tr>
			<tr>
				<td style="width: 150px"><input id="query" type="button" value="查询"/></td>
				<td><input id="update" type="button" value="更新"/></td>
			</tr>
		</table>
		<hr />
		</s:if>
		<s:else>
			<input type="hidden" name="num" value="${num}"/>
		</s:else>
		<table class="listtable">
			<thead>
				<tr>
					<th width="5%">京东分类</th>
					<th width="10%">京东商品ID</th>
					<th width="10%">京东标题</th>
					<th width="10%">商品走秀码</th>
					<th width="5%">商品京东价</th>
					<th width="5%">商品市场价</th>
					<th width="5%">库存</th>
					<th width="10%">创建时间</th>
				</tr>
			</thead>
			
			<tbody>
				<s:iterator value="pageView.records" var="resultObject" >
					<tr class="myclass">
						<td  class="tlr" style="text-align: center">
							<s:property value="#resultObject.cid" />
						</td>
						<td class="tlr"><s:property value="#resultObject.jdWareId" /></td>
						<td class="tlr"><s:property value="#resultObject.title" /></td>
						<td class="tlr"><s:property value="#resultObject.xiucode" /></td>
						<td class="tlr"><s:property value="#resultObject.jdprice" /></td>
						<td class="tlr"><s:property value="#resultObject.marketprice" /></td>
						<td class="tlr"><s:property value="#resultObject.stocknum" /></td>
						<td class="tlr"><s:property value="#resultObject.createdate" /></td>
					</tr>
				</s:iterator>
			</tbody>
			
			<tbody>
			<%@ include file="/admin/common/page.jsp"%>
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
	
	$("#query").click(function(){
		var action = "<%=request.getContextPath() %>/jdGoods/wareInfoList.action";
		$('#wareInfoForm').attr("action",action);
		$('#wareInfoForm').submit();
	});
	
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.action = "<%=request.getContextPath()%>/jdGoods/wareInfoList.action";
		form.currentPage.value=page;
		form.submit();
	}

	$("#goButton").click(function(){

		var page = $("#goPageno").val();
		if (! /[0-9]+/.test(page)) {
			alert("请输入数字");
			$("#goPageno").val('');
			return;
		}
		
		page = parseInt(page);
		var totalPage = ${pageView.totalpage };
		
		if (page > totalPage) {
			page = totalPage;
		}
		
		topage(page);
	});
	
	$("#update").click(function(){
		var prJdTitle = $("#prJdTitle").val();
		if(prJdTitle == ""){
			alert("商品名称前缀为空,你确定要执行此操作吗?");
		}
		window.location.href= "<%=request.getContextPath()%>/jdGoods/updateWareInfo.action?prJdTitle="+prJdTitle;
	});
</script>
</body>
</html>