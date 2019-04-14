<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>订单分页信息</title> 
<link href="<%=request.getContextPath()%>/template/css/base.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/common.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/page_admin_main.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/template/javascript/calendar/My97DatePicker/WdatePicker.js" type="text/javascript"></script>	
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery-1.3.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weebox.css"	 />
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weebox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/bgiframe.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weescroller.js"></script>
<!-- 自动填充插件 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery.autocomplete.js"></script>
<style type="text/css">
 .isgray{
  text-decoration:underline;
  color:gray;
  }
</style>
<script type="text/javascript">
$(document).ready(function(){
	   var isDisabled=false;
	   var names = document.getElementsByName("orderIds");
		var len = names.length;
		
		if (len) {
			var i = 0;
			for (i = 0; i < len; i++) {
				//复选框没有可用的
				if (names[i].disabled) {
					isDisabled=names[i].disabled;
				}else{
					isDisabled=names[i].disabled;
					break;
				}
			}
		}
		if(isDisabled){
			$("#processStatusDisabled").attr('disabled','true');
			$(".isgray").addClass("isgray");
		}else{
			$(".isgray").removeClass("isgray");
		}
	

});
</script>
</head>
<body> 
<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
<tbody> 
<tr> 
<td class="td01"></td> 
<td class="td02"><h3 class="topTitle fb f14">京东订单分页列表</h3></td> 
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
	<dd>京东订单管理</dd>
</dl>
</div>

<form id="orderInfoForm" method="post" action="<%=request.getContextPath() %>/order/orderInfoList.action">
		<input type="hidden" name="currentPage" value="${currentPage }"/>
		<input type="hidden" name="pageSize" value="${pageSize}"/>
		<input type="hidden" name="jdOrderTrackForm.placeResult" value="${param['jdOrderTrackForm.placeResult']}"/>
		<table class="mytable">
			<tr>
				
				<td>京东订单号:<s:textfield theme="simple"  name="jdOrderTrackForm.jdOrderId"/></td>&nbsp;&nbsp;
				<td>固话:<s:textfield theme="simple"  name="jdOrderTrackForm.telePhone"/>  </td>&nbsp;&nbsp;
				<td>手机号:<s:textfield theme="simple"  name="jdOrderTrackForm.mobile"/></td>&nbsp;&nbsp;
				<td>收货人:<s:textfield theme="simple"  name="jdOrderTrackForm.fullName"/> </td>&nbsp;&nbsp;
				<td>状态:
			<s:select  theme="simple" name="jdOrderTrackForm.isProcess" id="isProcess" headerKey="-1" headerValue="处理状态" list="#{'0':'未处理','1':'已处理'}" cssStyle="width:100px" value="jdOrderTrackForm.isProcess"></s:select>
		       </td>
			</tr>
			
			<tr>
			   <td>收货地址:<s:textfield theme="simple"  name="jdOrderTrackForm.fullAddress"/> </td>&nbsp;&nbsp;
				<td colspan="2">创建时间:
					<s:textfield id="startTime" name="jdOrderTrackForm.startTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
					至
					<s:textfield id="endTime" name="jdOrderTrackForm.endTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
				</td>
				
				<td colspan="2">处理时间:
					<s:textfield id="startTime" name="jdOrderTrackForm.startTimeProcess" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
					至
					<s:textfield id="endTime" name="jdOrderTrackForm.endTimeProcess" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
				</td>
			</tr>
			<tr>
				<td><input id="query" type="button" value="查询"/>
				<!-- <input id="export" type="button" value="导出订单" style="position:relative;left:10px"/> -->
				</td>
			</tr>
		</table>
		<hr />
		<table class="listtable">
			<thead>
				<tr>
				  <th width="5%">选项</th>
					<th width="8%">京东订单ID</th>
					<th width="8%">走秀订单ID</th>
					<th width="10%">下单时间</th>
					<th width="8%">收货人</th>
					<th width="8%">手机</th>
					<th width="8%">固定电话</th>
					<th width="10%">收货人地址</th>
					<th width="6%">失败描述</th>
					<th width="10%">处理状态</th>
					<th width="10%">处理时间</th>
					
					<th width="9%">操作</th>
				</tr>
			</thead>

			<tbody>
				<s:iterator value="pageView.records" var="resultObject" >
					<tr class="myclass">
					    <td style="height:50px;text-align:center;" nowrap><!-- 选择 -->	
					    <s:property value="#goodsstate.count" />	
						<input type="checkbox" name="orderIds"  value="<s:property value='#resultObject.jdOrderId' />"
						
						 <s:if test='#resultObject.isProcess==1'>disabled="disabled"</s:if> />	
						  <!-- 已处理过了,把复选框灰掉 -->			
		       	     </td>
					
						<td class="tlr"><s:property value="#resultObject.jdOrderId" /></td>
						<td class="tlr"><s:property value="#resultObject.localOrderId" /></td>
						<td  style="text-align:center;"><s:property value="#resultObject.placeTime" /></td>
						<td class="tlr"><s:property value="#resultObject.fullName" /></td>
						<td class="tlr"><s:property value="#resultObject.mobile" /></td>
						<td class="tlr"><s:property value="#resultObject.telePhone" /></td>
						<td class="tlr"><s:property value="#resultObject.fullAddress" /></td>
						<td class="tlr"><s:property value="#resultObject.failDescri" /></td>
						<td class="tlr">
						   <s:if test="isProcess==0">未处理</s:if>
						   <s:else>已处理</s:else>
						</td>
						<td  style="text-align:center;"><s:property value="#resultObject.processTime" /></td>
						
						<td  style="text-align:center; ">
						  
						     <s:if test="isProcess==0"> <span> <a style="cursor: pointer;" onclick="setProcessStatus('false',<s:property value='#resultObject.jdOrderId' />)">处理</a> </span></s:if>
						     <s:else> <span style="text-decoration: underline;color: gray;">已处理</span></s:else>
						    
						   &nbsp;
						    <span ><a style="cursor: pointer;" onclick="orderSKUInfo('<%=request.getContextPath() %>/order/getOrderSkuInfo.action?orderId=<s:property value='#resultObject.jdOrderId' />')">查看</a></span>
						   
						</td>
						
					</tr>
				</s:iterator>
				
			</tbody>
			 
			<tbody>
			
			<%@ include file="/admin/common/page.jsp"%>
			
			</tbody>
		</table>
		
		<a href="#" class="isgray"
		 <s:if test="pageView.records.size()>0">onclick="checkAll('orderIds')" </s:if>
		<s:else> style="text-decoration: underline;color: gray;"</s:else> 
		>全选  </a>
		<a href="#" class="isgray" <s:if test="pageView.records.size()>0">onclick="reserveCheck('orderIds')"</s:if> <s:else> style="text-decoration: underline;color: gray;"</s:else>  >反选 </a>
		<input id="processStatusDisabled"  <s:if test="pageView.records.size()<1">disabled="disabled"</s:if>  type="button" onclick="setProcessStatus('true','')" value="批量处理"/>
	</form>
</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">
	
	$("#query").click(function(){
		var action = "<%=request.getContextPath() %>/order/orderInfoList.action";
		$('#orderInfoForm').attr("action",action);
		var form = document.forms[0];
		form.currentPage.value=1;
		$('#orderInfoForm').submit();
	});
	
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.action = "<%=request.getContextPath()%>/order/orderInfoList.action";
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
	///order/exportOrderInfo.action?jdOrderTrackForm.placeResult=2
	$("#export").click(function(){
		var action = "<%=request.getContextPath()%>/fqlGoods/exportOrderInfo.action";
		$('#orderInfoForm').attr("action",action);
		$('#orderInfoForm').submit();
	});
	
	// 全选
	function checkAll(name) {
		var names = document.getElementsByName(name);
		var len = names.length;
		if (len > 0) {
			var i = 0;
			for (i = 0; i < len; i++) {
				if (names[i].disabled != true)
					names[i].checked = true;
			}
		}
	}
	
	// 反选 
	function reserveCheck(name) {
		var names = document.getElementsByName(name);
		var len = names.length;
		if (len > 0) {
			var i = 0;
			for (i = 0; i < len; i++) {
				if (names[i].checked) {
					if (names[i].disabled != true)
						names[i].checked = false;
				} else {
					if (names[i].disabled != true)
						names[i].checked = true;
				}

			}
		}
	}
	
	//查看订单SKU详情信息
	   function orderSKUInfo(url){
            
			weeboxDialog = $.weeboxs.open(url, {
				
				title : '订单SKU详情信息',
				contentType : 'iframe',
				width : 800,
				height : 500,
				async : false,
				modal: true,
				showButton: false
			});
			
		}

   //处理设置状态 flag=true为批量设置
   function setProcessStatus(flag,JdorderId){
	   var isSelected=false;
	   var orderId=null;
	   if(flag!=null ){
		   //为批量设置,需要判断复选框是否又被选中了
		   if("true"==flag){
			   var names = document.getElementsByName("orderIds");
				var len = names.length;
				if (len) {
					var i = 0;
					for (i = 0; i < len; i++) {
						//复选框有被选中
						if (names[i].checked) {
							isSelected=true;
						}
					}
				}else{
					//一个的情况
					isSelected=names.checked
				}
		   }else{
			   //直接点击处理链接
			   isSelected=true;
			   orderId=JdorderId;
			  
		   }
	   }
	   
	   if(isSelected){
		  
		  var isConfirm= confirm("您确定要执行此操作么?");
		   if(isConfirm){
			   var form = document.forms[0];
			   
			  var isProcess=$('#isProcess').val();
			  if(isProcess!=-1){
				  form.currentPage.value=1;
			  } 
			  var requestPath= "<%=request.getContextPath()%>/order/updateProcessState.action?processStatu=1"
		       if(orderId!=null && ""!=orderId){
		    	   requestPath=requestPath+"&orderId="+orderId;
		       }
				form.action =requestPath ;
				form.submit();
		   }
		   
	   }else{
		   alert("请选中操作项");
	   }
	   
   }
   
   
	
</script>
</body>
</html>