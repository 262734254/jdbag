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
									
									<dd>订单详情 </dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
<div class="adminContent" >		
<font style="font-size: 15px;font-weight: bolder;">订单信息：</font>
<hr/>
<table style="margin-top: 0px;margin-left: 50px;" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td style="text-align:right;">订单号：</td><td style="text-align: left;">&nbsp;${orderInfo.orderId}</td>
	</tr>
	<tr>
		<td style="text-align:right;">订单ID：</td><td>&nbsp;${orderInfo.venderId}</td>
	</tr>
	<tr>
		<td style="text-align:right;">支付方式：</td><td>&nbsp;${orderInfo.payType}</td>
	</tr>
	<tr>
		<td style="text-align:right;">订单总金额：</td><td>${orderInfo.orderTotalPrice}</td>
	</tr>
	<tr>
		<td style="text-align:right;">用户应付金额：</td><td>${orderInfo.orderPayment}</td>
	</tr>
	<tr>
		<td style="text-align:right;">订单货款金额：</td><td>${orderInfo.orderSellerPrice}</td>
	</tr>
	<tr>
		<td style="text-align:right;">商品的运费：</td><td>${orderInfo.freightPrice}</td>
	</tr>
	<tr>
		<td style="text-align:right;">商家优惠金额：</td><td>${orderInfo.sellerDiscount}</td>
	</tr>
	<tr>
		<td style="text-align:right;">订单状态（英文）：</td><td>${orderInfo.orderState}</td>
	</tr>
	<tr>
		<td style="text-align:right;">订单状态说明（中文）：</td><td>${orderInfo.orderStateRemark}</td>
	</tr>
	<tr>
		<td style="text-align:right;">送货（日期）类型：</td><td>${orderInfo.deliveryType}</td>
	</tr>
	<tr>
		<td style="text-align:right;">发票信息：</td><td>${orderInfo.invoiceInfo}</td>
	</tr>
	<tr>
		<td style="text-align:right;">买家下单时订单备注：</td><td>${orderInfo.orderRemark}</td>
	</tr>
	<tr>
		<td style="text-align:right;">下单时间：</td><td>${orderInfo.orderStartTime}</td>
	</tr>
	<tr>
		<td style="text-align:right;">结单时间：</td><td>${orderInfo.orderEndTime}</td>
	</tr>
</table>
<p>&nbsp;</p>
<font style="font-size: 15px;font-weight: bolder;">订单商品列表：</font>
<hr/>
<table style="margin-top: 0px;margin-left: 50px;" cellpadding="0" cellspacing="0" border="0">
<c:forEach var="item" items="${orderInfo.itemInfoList}">
	<tr>
		<td style="text-align:right;">京东内部SKU的ID：</td><td style="text-align: left;">&nbsp;${item.skuId}</td>
	</tr>
	<tr>
		<td style="text-align:right;">SKU外部ID：</td><td style="text-align: left;">&nbsp;${item.outerSkuId}</td>
	</tr>
	<tr>
		<td style="text-align:right;">商品的名称+SKU规格：</td><td style="text-align: left;">&nbsp;${item.skuName}</td>
	</tr>
	<tr>
		<td style="text-align:right;">SKU的京东价：</td><td style="text-align: left;">&nbsp;${item.jdPrice}</td>
	</tr>
	<tr>
		<td style="text-align:right;">赠送积分：</td><td style="text-align: left;">&nbsp;${item.giftPoint}</td>
	</tr>
	<tr>
		<td style="text-align:right;">京东内部商品ID：</td><td style="text-align: left;">&nbsp;${item.wareId}</td>
	</tr>
	<tr>
		<td style="text-align:right;">数量：</td><td style="text-align: left;">&nbsp;${item.itemTotal}</td>
	</tr>
	<tr>
		<td style="text-align:right;">商品货号：</td><td style="text-align: left;">&nbsp;${item.productNo}</td>
	</tr>
	 <tr>
          <td colspan="2" style="border-bottom: 1px dashed #334455;height: 1px;">
                  			&nbsp;
          </td>
     </tr>
</c:forEach>
</table>
<p>&nbsp;</p>
<font style="font-size: 15px;font-weight: bolder;">收货人基本信息：</font>
<hr/>
<c:set var="user" value="${orderInfo.consigneeInfo}"></c:set>
<table style="margin-top: 0px;margin-left: 50px;" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td style="text-align:right;">姓名：</td><td style="text-align: left;">&nbsp;${user.fullname}</td>
	</tr>
	<tr>
		<td style="text-align:right;">地址：</td><td style="text-align: left;">&nbsp;${user.fullAddress}</td>
	</tr>
	<tr>
		<td style="text-align:right;">固定电话：</td><td style="text-align: left;">&nbsp;${user.telephone}</td>
	</tr>
	<tr>
		<td style="text-align:right;">手机：</td><td style="text-align: left;">&nbsp;${user.mobile}</td>
	</tr>
	<tr>
		<td style="text-align:right;">省：</td><td style="text-align: left;">&nbsp;${user.province}</td>
	</tr>
	<tr>
		<td style="text-align:right;">市：</td><td style="text-align: left;">&nbsp;${user.city}</td>
	</tr>
	<tr>
		<td style="text-align:right;">县：</td><td style="text-align: left;">&nbsp;${user.county}</td>
	</tr>
</table>
<p>&nbsp;</p>
<font style="font-size: 15px;font-weight: bolder;">订单商家优惠列表：</font>
<hr/>
<table style="margin-top: 0px;margin-left: 50px;" cellpadding="0" cellspacing="0" border="0">
<c:if test="${(!empty orderInfo.couponDetailList) && (fn:length(orderInfo.couponDetailList)>0)}">
<c:forEach var="item" items="${orderInfo.couponDetailList}">
	<tr>
		<td style="text-align:right;">优惠金额：</td><td style="text-align: left;">&nbsp;${item.couponPrice}</td>
	</tr>
	<tr>
		<td style="text-align:right;">优惠类型：</td><td style="text-align: left;">&nbsp;${item.couponType}</td>
	</tr>
	<tr>
		<td style="text-align:right;">订单编号：</td><td style="text-align: left;">&nbsp;${item.orderId}</td>
	</tr>
	<tr>
		<td style="text-align:right;">京东sku编号：</td><td style="text-align: left;">&nbsp;${item.skuId}</td>
	</tr>
	<tr>
          <td colspan="2" style="border-bottom: 1px dashed #334455;height: 1px;">
                  			&nbsp;
          </td>
     </tr>
</c:forEach>
</c:if>
<c:if test="${(empty orderInfo.couponDetailList) || (fn:length(orderInfo.couponDetailList)==0)}">
	<tr>
		<td colspan="2" style="text-align:center;">
			无
		</td>
	</tr>
</c:if>
</table>
<center>
	<input value="返回" onclick="window.history.go(-1);" type="button" style="width:60px;height: 25px;"/>
</center>
	<!--主体内容结束-->
</div>	
						
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
