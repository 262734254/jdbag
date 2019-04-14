<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>订单查询</title>
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<link href="${path}/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="${path}/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<link href="${path}/resources/style/order_list.css" rel="stylesheet" type="text/css" />
<script src="${path}/template/javascript/jquery-1.9.1.min.js"></script>
<script src="${path}/template/javascript/bgiframe.js"></script>
<script src="${path}/template/uiplugin/weebox/src/bgiframe.js"></script>
<script src="${path}/template/javascript/calendar/My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript"> 
$(document).ready(function() {
		$("#_order${_sn}").addClass("thistab");
		<c:if test="${page eq '1'}">
			document.getElementById('first_page').disabled="disabled";
			document.getElementById('first_page').onclick="";
			document.getElementById('last_page').disabled="disabled";
			document.getElementById('last_page').onclick="";
		</c:if>
		<c:if test="${page eq totalPage || totalPage eq '0'}">
			document.getElementById('next_page').disabled="disabled";
			document.getElementById('next_page').onclick="";
			document.getElementById('end_page').disabled="disabled";
			document.getElementById('end_page').onclick="";
		</c:if>
});

function toPage(num){
	$("#page").val(num);
	if(num=='last'){
		$("#page").val(<s:property value="page"/>-1);
	}else if(num=='next'){
		$("#page").val(<s:property value="page"/>+1);
	}
	
	$("#form").submit();
}
function checkPageSize(obj){
	var v = $(obj).val();
	$(obj).val(v.replace(/[^\d]/g,''));
	if($(obj).val() > 100){
		$(obj).val(100);
	}
}
function toModifyRemark(orderId){
	window.showModalDialog('${path}/order!forward.action?path=orderRemark&orderId='+orderId+'&cur_time='+new Date(),'','dialogWidth:600px;dialogHeight:500px;status:0;');
}
function toOutstorage(orderId){
	window.showModalDialog('${path}/order!forward.action?path=outstorage&orderId='+orderId+'&cur_time='+new Date(),'','dialogWidth:600px;dialogHeight:500px;status:0;');
}
</script>
<style type="text/css">
td {
	text-align: center;
}

th {
	text-align: center;
	font-weight: bolder;
	font-size: 15px;
}

.apage {
	height:22px;
	width:60px;
	text-align: center;
	text-decoration: none;
	display: block;
	border:1px solid #999999;
	background-color: #ffffff;
	color:#000000;
	vertical-align:middle;
	line-height: 22px;
}
</style>

</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">订单管理</h3></td>
				<td class="td03"></td>
			</tr>
		</tbody>
	</table>
	<table class="adminMain_main" style="width: 100%;">
		<tbody>
			<tr>
				<td class="td01"></td>				
				<td class="adminMain_wrap" >
					<div class="wrapMain">
							<div class="adminUp_wrap">
								<dl class="adminPath clearfix">
									<dt>您现在的位置：</dt>
									
									<dd>订单检索 </dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
<div class="adminContent" >		
<form id="form" method="post" action="${path}/order!searchOrder.action">
<table style="margin-top: 20px;" border="0">
        		<tr>
        			<td style="text-align: left;">
        				时间从：<input style="width:200px;height:18px;" name="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        				&nbsp;至&nbsp;
        				<input style="width:200px;height:18px;" name="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>&nbsp;
        				&nbsp;<font color="red">(开始时间 和 结束时间 不得相差超过1个月)</font>
        				&nbsp;&nbsp;每页<input onkeyup="checkPageSize(this);" name="pageSize" style="width:30px;height:18px;" value="<s:property value="pageSize"/>"/>/条
        				&nbsp;<font color="red">(最大100)</font>
        			</td>
        		</tr>
        		<tr>
        			<td style="text-align: left;">
        				&nbsp;<br/>
        				订单号：<input name="orderId" style="width:200px;height:18px;"/>
        				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<input value="查询" type="submit" style="width:60px;height: 25px;"/>
        				<input type="hidden" name="orderState" value="<s:property value="orderState"/>"/>
        				<input type="hidden" id="page" name="page" value="1"/>
        			</td>
        		</tr>
        	</table>
        	<p>&nbsp;</p>
<div id="tabbox">
	<ul class="tabs" id="tabs">
         <li style="cursor:pointer" id="_order-1"><a state="-1"  href="${path}/order!searchOrder.action?orderState=WAIT_SELLER_STOCK_OUT,WAIT_GOODS_RECEIVE_CONFIRM,FINISHED_L,TRADE_CANCELED">全部订单</a></li>
         <li style="cursor:pointer" id="_order1"><a state="1" href="${path}/order!searchOrder.action?orderState=WAIT_SELLER_STOCK_OUT">等待出库</a></li>
         <li style="cursor:pointer" id="_order4"><a state="4" href="${path}/order!searchOrder.action?orderState=WAIT_GOODS_RECEIVE_CONFIRM">已出库</a></li>
         <li style="cursor:pointer" id="_order7"><a state="7" href="${path}/order!searchOrder.action?orderState=FINISHED_L">买家已收货</a></li>
         <li style="cursor:pointer" id="_order6"><a state="6" href="${path}/order!searchOrder.action?orderState=TRADE_CANCELED">已取消</a></li>
    </ul>
    <ul class="tab_conbox" id="tab_conbox">
        <li class="tab_con">
        	<table style="margin-top: 0px;" width="98%" cellpadding="0" cellspacing="0" border="0">
        		 <thead>
                    <tr>
                        <th style="height: 50px;">订单编号</th>
                        <th>商品信息</th>
                        <th>支付方式</th>
                        <th>下单时间</th>
                        <th>金额</th>
                        <th>优惠金额</th>
                        <th>当前状态</th>
                        <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                   <tr>
                  		<td colspan="8" style="border-top: 1px solid #334455;height: 1px;">
                  			&nbsp;
                  		</td>
                  	</tr>
                  	<c:forEach var="orderInfo" items="${orderResult.orderInfoList}">
                  	 <tr>
                  		<td>
                  			<a href="${path}/order!toGetOrderInfo.action?orderId=${orderInfo.orderId}">${orderInfo.orderId}</a>
                  		</td>
                  		<td>
                  			<c:forEach var="itemInfo" items="${orderInfo.itemInfoList}">
                  				<font color="#0000FF"> ${itemInfo.skuName}</font><br><font color="#FF6600">(商品数量：${itemInfo.itemTotal})</font> <br>
                  			</c:forEach>
                  		</td>
                  		<td>
                  			${orderInfo.payType}
                  		</td>
                  		<td>
                  			${orderInfo.orderStartTime}
                  		</td>
                  		<td>
                  			订单金额：${orderInfo.orderTotalPrice}<br>结算金额：${orderInfo.orderPayment}
                  		</td>
                  		<td>
                  			${orderInfo.sellerDiscount}
                  		</td>
                  		<td>
                  			${orderInfo.orderStateRemark}
                  		</td>
                  		<td style="text-align: left;">
                  			<a style="font-size: 12px;" target="_blank" href="${path}/order!print.action?orderId=${orderInfo.orderId}">打印面单</a>
                  			&nbsp;
                  			<a style="font-size: 12px;" href="#" onclick="toModifyRemark(${orderInfo.orderId});">修改备注</a>
                  			<br/>
                  			<c:choose>
                  				<c:when test="${orderInfo.orderState eq 'WAIT_SELLER_STOCK_OUT'}">
                  					<%-- 
                  					<a style="font-size: 12px;" href="#" onclick="toOutstorage(${orderInfo.orderId});">订单出库</a>
                  					 --%>
                  				</c:when>
                  				<c:otherwise>
                  					<a style="font-size: 12px;" href="#" onclick="alert('?');">修改运单号</a>
                  				</c:otherwise>
                  			</c:choose>
                  		</td>
                  	 </tr>
                  	 <tr>
                  		<td colspan="8" style="border-bottom: 1px dashed #334455;height: 1px;">
                  			&nbsp;
                  		</td>
                  	</tr>
                  	</c:forEach>
                  	<c:if test="${empty orderResult.orderInfoList}">
                  		 <tr>
                  		<td colspan="8" style="text-align: center;">
                  			没有符合的记录
                  		</td>
                  	</tr>
                  	</c:if>
                  </tbody>
        	</table>
        	<table width="98%" border="0">
        		<tr>
        			<td width="57%">&nbsp;</td>
        			<td>&nbsp;<a id="first_page" href="#" onclick="toPage(1);" class="apage">首页</a>&nbsp;</td>
        			<td>&nbsp;<a id="last_page" href="#" onclick="toPage('last');" class="apage">上一页</a>&nbsp;</td>
        			<td>&nbsp;&nbsp;<s:property value="page"/>&nbsp;&nbsp;</td>
        			<td>&nbsp;<a id="next_page" href="#" onclick="toPage('next');" class="apage">下一页</a>&nbsp;</td>
        			<td>&nbsp;<a id="end_page" href="#" onclick="toPage(${totalPage});" class="apage">末页</a>&nbsp;</td>
        			<td>&nbsp;共${totalCount}条&nbsp;<s:property value="pageSize"/>条/页&nbsp;共${totalPage}页</td>
        		</tr>
        	</table>
        </li>
    </ul>
</div>    
	<!--主体内容结束-->
</form>
</div>	
						
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
