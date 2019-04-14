<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>日志分页列表</title> 
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
<td class="td02"><h3 class="topTitle fb f14">日志分页列表</h3></td> 
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
	<dd>日志分页列表</dd>
</dl>
</div>


<form id="jdLogListForm" action="<%=request.getContextPath() %>/jdGoods/logListRecord.action" method="post">

<input type="hidden" name="currentPage" value="${currentPage }"/>
<input type="hidden" name="pageSize" value="${pageSize}"/>


<table  class="mytable"  >
	<tr>
		<td style="width: 500px;">创建时间
		<s:textfield id="startTime" name="queryForm.startTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
		到 
		<s:textfield id="endTime" name="queryForm.endTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
		</td>
		<td>
		&nbsp;&nbsp;&nbsp;日志类型&nbsp;
		<s:select id="resultFlag"  theme="simple" name="queryForm.logType" headerKey="0" headerValue="全部" list="#{'1':'sku不存在','2':'库存','3':'价格'}" cssStyle="width:100px" value="queryForm.logType"></s:select>
		</td>
		<td>&nbsp;&nbsp;&nbsp;状态&nbsp;
			<s:select id="resultStatus"  theme="simple" name="queryForm.status" headerKey="" headerValue="全部" list="#{'1':'库存同步成功','2':'库存同步失败','3':'价格同步成功','4':'价格同步失败','5':'不能同步价格'}" cssStyle="width:100px" value="queryForm.status"></s:select>
		</td>
		<!-- 
		<td>更新时间
		<s:textfield id="updateStartTime" name="queryForm.updateStartTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
		到 
		<s:textfield id="updateEndTime" name="queryForm.updateStartTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
		</td>
		 -->
	</tr>
	<tr>
		<td>京东商品ID&nbsp;<s:textfield id="skuCode" theme="simple"  name="queryForm.jdWareId"></s:textfield></td>
		<td>商品SKU码&nbsp;<s:textfield id="skuCode" theme="simple"  name="queryForm.skuCode"></s:textfield></td>
	</tr>
	<tr >
		<td ><input id="query"  type="button" value="查询"/><input id="export" type="button" value="导出日志" style="position:relative;left:10px"/></td>
	</tr>
	
</table>


<hr />
<table class="listtable">
	<thead>
		<tr>
			<th width="8%">日志类型</th>
			<th width="30%">描述</th>
			<th width="15%">京东商品ID</th>
			<th width="12%">商品SKU码</th>
			<th width="15%">创建时间</th>
			<!-- <th width="15%">更新时间</th> -->
			<th width="15%">状态</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="pageView.records" var="resultObject" >
			<tr>
				
				<td  class="tlr" style="text-align: center">
				<s:if test="logtype==1">
					sku码不存在
				</s:if>
				<s:elseif test="logtype==2">
					库存
				</s:elseif>
				<s:elseif test="logtype==3">
					价格
				</s:elseif>
				</td>
				<td class="tlr"><s:property value="#resultObject.describe" /></td>
				<td   style="text-align:center;"><s:property value="#resultObject.wareid" /></td>
				<td class="tlr"><s:property value="#resultObject.xiusn" /></td>
				<td class="tlr"><s:property value="#resultObject.createdate" /></td>
				<!-- <td class="tlr"><s:property value="#resultObject.updatedate" /></td> -->
				<td class="tlr">
					<s:if test="status==0">
						商品sku码不存在
					</s:if>
					<s:elseif test="status==1">
						库存同步成功
					</s:elseif>
					<s:elseif test="status==2">
						库存同步失败
					</s:elseif>
					<s:elseif test="status==3">
						价格同步到京东
					</s:elseif>
					<s:elseif test="status==4">
						价格同步失败
					</s:elseif>
					<s:else>
						不能同步价格
					</s:else>
				</td>
			</tr>
		</s:iterator>
	</tbody>
	
	<tbody>
	<%@include file="/admin/common/page.jsp"%>
	</tbody>
</table>
</form>

</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript"><!--

$(function() {
	//$('#jdLogListForm').submit(function() {
		/* var start = $('input[name=queryForm.begDate]').val();
		var end = $('input[name=queryForm.endDate]').val();
		
		if ((start != '' && end != '') && start > end) {
			alert('[上传提交时间] 开始时间不能大于结束时间');
			 $('input[name=queryForm.startCreateTime]').focus();
			return false;
		}
		
		return true; */
		//alert("表单提交");
		//return true;
	//});
	$("#query").click(function(){
		var action = "<%=request.getContextPath()%>/jdGoods/logListRecord.action";
		$('#jdLogListForm').attr("action",action);
		$('#jdLogListForm').submit();
	});
	
});

//到指定的分页页面
function topage(page){
	var form = document.forms[0];
	form.action = "<%=request.getContextPath()%>/jdGoods/logListRecord.action";
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

/*导出日志列表*/
$("#export").click(function(){
	var action="<%=request.getContextPath()%>/jdGoods/exportLog.action";
	$('#jdLogListForm').attr("action",action);
	$('#jdLogListForm').submit();
});

/*显示商品sku码*/
$("#skuCode").click(function(){
	$.ajax({
		type:"GET",
		url:"<%=request.getContextPath()%>/jdGoodsajax/logSkuList.action",
		dataType:"json",
		success:function(data,textStatus){
			if(data != null && data.logSkuList != null){
				var input = $("#skuCode");
				input.autocomplete(data.logSkuList,{
					minChars:0,
					width: 200,
					matchContains: true,
					autoFill: false,
					formatItem: function(row, i, max) {
                        return "<table><tr><td>"+row.xiusn+"</td></tr></table>";
                    },
                    formatMatch:function(row, i, max) {
                        return row.xiusn;
                    },
                    formatResult: function(row) {
                        return row.xiusn;
                    }
                    }).result(function(e,data,value,esc){
                    	input.val(data.xiusn);
                    });
			}
		}
	});
});

/*选择下拉列表时自动查询*/
$("#resultFlag").change(function(){
	if(!this.selected){
		var action = "<%=request.getContextPath()%>/jdGoods/logListRecord.action";
		$('#jdLogListForm').attr("action",action);
		$('#jdLogListForm').submit();
	}
});

/*查询绑定回车事件*/
$("#startTime,#endTime,#skuCode,#resultFlag").keydown(function(event){
	 if (event.keyCode == 13) {  	
		 var action = "<%=request.getContextPath()%>/jdGoods/logListRecord.action";
			$('#jdLogListForm').attr("action",action);
			$('#jdLogListForm').submit();
	 }
});
</script>

</body> 
</html> 