<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>满减促销管理</title> 
<link href="<%=request.getContextPath()%>/template/css/base.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/common.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/page_admin_main.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/template/css/ebay_sales.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/template/javascript/calendar/My97DatePicker/WdatePicker.js" type="text/javascript"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/goodsList.js"></script>
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
</head>
<body>
	<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
		<tbody> 
			<tr> 
				<td class="td01"></td> 
				<td class="td02"><h3 class="topTitle fb f14">满减促销</h3></td> 
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
								<dd>满减促销</dd>
							</dl>
						</div>
	<form id="promoInfoForm" method="post"  enctype="multipart/form-data">
		<input type="hidden" name="currentPage" value="${currentPage }"/>
		<input type="hidden" name="pageSize" value="${pageSize}"/>
		<%-- <input type="hidden" id="stockOrder" name="stockOrder" value="<s:property value="stockOrder"/>"/>
	    <input type="hidden" id="jdstockNumOrder" name="jdstockNumOrder" value="<s:property value='jdstockNumOrder'/>"/>
	    <input type="hidden" name="jdwardIds" id="jdwardIds" value=""/> --%>
		<s:if test="1==1">
		<input id="flag" type="hidden" name="flag" value="flag"/>
		<table class="mytable">
		    <tr>
				<td   colspan='4' style="width: 250px">
					<input id="query" style="float:right"  type="button" value="添加满减促销" onclick="openAddPromoUI()"/>
				</td>
			</tr>
			<tr>
					
				
				<td  style="width: 250px">活动名称：&nbsp;
					<s:textfield theme="simple" name="promoForm.name" />
				</td>
				<td  colspan='3' style="width: 1000px" >活动时间&nbsp;&nbsp;
					<s:textfield id="startTime" name="promoForm.beginTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					至
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:textfield id="endTime" name="promoForm.endTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
				</td>
				
			</tr>
			
			
			<tr>
			   
			  <td ><br /><span  style='margin-bottom:20px'>京东商品编码：</span>
			 
			   <textarea rows="4" cols="15" id="wareIds" name="wareIds" ><s:property value="wareIds"/></textarea>  
			  </td>
			   <td ><br /><span >京东SKU:
			    <textarea rows="4" cols="15" id="jdSkus" name="jdSkus" ><s:property value="jdSkus"/></textarea>
			  </td>
			   <td ><br /><span >走秀货号：</span>
			    <textarea rows="4" cols="15" id="xiuCodes" name="xiuCodes" ><s:property value="xiuCodes"/></textarea>
			  </td>
			   <td  ><br /><span >走秀SKU：</span>
			    <textarea rows="4" cols="15" id="xiuSkus" name="xiuSkus" ><s:property value="xiuSkus"/></textarea>
			  </td>
			 
			</tr>
			<tr>
				<td  colspan='4' ><input id="query" style="width: 80px; float:right" type="button" onclick='queryPromoList()' value="查询"/></td>
				
			</tr>
	
		<tr>
		 <td colspan="17"> </td>
		
		</tr>
		
		<td colspan="4">
		
		</td>
		</tr>
		</table>
		 </form>
		<hr />
		</s:if>
		
		<table class="listtable" id="idTable">

			<thead>
				<tr>
				  
					<th width="5%">促销编码</th>
					<th width="5%">促销名称</th>
					<th width="8%">促销优惠</th>
					<th width="5%">会员等级</th>
					<th width="8%">促销时间</th>

					<th width="5%">参与方式</th>
					<th width="5%">订单规则类型</th>
					<th width="10%">创建时间</th>
					<th width="4%" id="idSize" >
					 操作
					</th>
					
				</tr>
			</thead>
			
			<tbody>
				<s:iterator value="pageView.records" var="resultObject" >
					<tr class="myclass">
						<td class="tlr"><s:property value="#resultObject.promoId" /></td>
						<td class="tlr"><s:property value="#resultObject.name" /></td>
						<td class="tlr"><s:property value="#resultObject.name" /></td>
						<td class="tlr">
						
						  <s:if test="#resultObject.member==50">
									注册会员
						   </s:if>
						   <s:elseif test="#resultObject.member==56">
								       铜牌
						   </s:elseif>
						   <s:elseif test="#resultObject.member==61">
								      银牌
						   </s:elseif>
						   <s:elseif test="#resultObject.member==62">
								       金牌
						   </s:elseif>
						   <s:elseif test="#resultObject.member==105">
								      钻石
						   </s:elseif>
						    <s:elseif test="#resultObject.member==110">
								      VIP
						   </s:elseif>
						</td>
						<td class="tlr">
						     <p><s:property value="#resultObject.beginTime" /></p>
						     <p><s:property value="#resultObject.endTime" /></p>
						</td>
						<td class="tlr">
						
						   <s:if test="#resultObject.bound==1">
									部分商品参加
						   </s:if>
						   <s:elseif test="#resultObject.bound==3">
								       部分商品不参加
						   </s:elseif>
							
					
						
						</td>
						<td class="tlr">
						
						   <s:if test="#resultObject.favorMode==1">
									满减
						   </s:if>
						   <s:elseif test="#resultObject.favorMode==3">
								      每满减
						   </s:elseif>
							
					
						
						</td>
						<td class="tlr">
						<s:property value="#resultObject.createTime" />
						  
							
					
						
						</td>
					
						<td class="tlr" style="text-align:center;">	<a class="modify" href="<%=request.getContextPath()%>/jdGoods/importPromoSkuUI.action?promoId=<s:property value="#resultObject.promoId" />">导入商品</a></td>
					</tr>
				</s:iterator>
			</tbody>
			<tbody>
			<%@ include file="/admin/common/page.jsp"%>
			</tbody>
		</table>
		
		
       
	   

</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">
var openAddPromoUI = function(){
	window.location.href="<%=request.getContextPath()%>/jdGoods/addPromoUI.action";
	
}

var queryPromoList = function(){
	var action = "<%=request.getContextPath()%>/jdGoods/promoInfoList.action";
	$('#promoInfoForm').attr("action",action);
	$('#promoInfoForm').submit();
	
}

$(".modify").click(function(){
	var categoryId = $(this).parent().parent().children("td").get(0).innerHTML;
	var xiucode = $(this).parent().parent().children("td").get(5).innerHTML;
	var stock = $(this).parent().parent().children("td").get(7).innerHTML;
	var num = $(this).parent().parent().children("td").get(9).innerHTML;
	var brandCode = $(this).parent().parent().children("td").get(12).innerHTML;
	if(stock==0){
		alert("该商品的库存为0,不能进行修改操作");
		return;
	}
	var flag=$("#flag");
	var flagVaule=null;
	if(flag){
		flagVaule=$(flag).val();
	}
	window.open("<%=request.getContextPath()%>/jdGoods/wareInfoModify.action?xiucode="+xiucode+"&num="+num+"&flag="+flagVaule+"&xiuCategoryId="+categoryId+"&brandCode="+brandCode,"newwindow");
});


</script>
</body>
</html>