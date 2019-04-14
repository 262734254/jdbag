<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入商品销售属性映射表界面</title>
<link href="<%=request.getContextPath()%>/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<script src="<%=request.getContextPath()%>/template/javascript/calendar/My97DatePicker/WdatePicker.js" type="text/javascript"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/bgiframe.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/bgiframe.js"></script>

<script type="text/javascript">


var addPromoData=function  (){
	
	
	var action = "<%=request.getContextPath() %>/jdGoods/addPromo.action";
	$('#addPromoForm').attr("action",action);
	$('#addPromoForm').submit();
	
	
}

</script>

</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">导入商品走秀码</h3></td>
				<td class="td03"></td>
			</tr>
		</tbody>
	</table>
	<table class="adminMain_main" style="width: 100%">
		<tbody>
			<tr>
				<td class="td01"></td>				
				<td class="adminMain_wrap" >
					<div class="wrapMain">
							<div class="adminUp_wrap">
								<dl class="adminPath clearfix">
									<dt>您现在的位置：</dt>
									<dd>走秀网运营中心</dd>
									<dd>添加满减促销</dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
					</div>
					<div>
					<table width="100%" >
						<tbody>
						<form  method="post" id="addPromoForm" name="addPromoForm" >
						
						<tr>
							
						<td  style="height:80px ;text-align:right;"><label style="color:red">*</label>活动名称：&nbsp;<s:textfield theme="simple" name="addPromoForm.name" /></td>
						<td style="height:80px ;" >
						                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label style="color:red">*</label>活动时间：&nbsp;&nbsp;
								<s:textfield id="startTime" name="addPromoForm.startTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
								&nbsp;
								至
								&nbsp;
								<s:textfield id="endTime" name="addPromoForm.endTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
						</td>
						
						</tr>
						<tr>
							<td style="height:80px;text-align:right"><label style="color:red">*</label>满减类型：&nbsp;
							<select style="width:160px" name="addPromoForm.favorMode">
							   <option value="1">满减</option>
							   <option value="2">每满减</option>
							</select>
							</td>
							<td style="height:80px ">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label style="color:red">*</label>活动规则：&nbsp;满<s:textfield theme="simple" name="addPromoForm.quota" />元  减 <s:textfield theme="simple" name="addPromoForm.rate" />元</td>
						</tr>
						
						<tr>
							<td style="height:80px; text-align:right">京东会员级别：&nbsp;
							<select  style="width:160px" name="addPromoForm.member">
								<option value='50'>注册会员</option>
								<option value='56'>铜牌</option>
								<option value='61'>银牌</option>
								<option value='62'>金牌</option>
								<option value='105'>钻石</option>
								<option value='110'>VIP</option>
							</select>
							</td>
							<td style="height:80px; ">
							  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label style="color:red">*</label> 参与方式:
							  <select name="addPromoForm.bound">
							    <option value="1">部分商品参加</option>
							    <option value="3">部分商品不参加</option>
							    <select>
			                </td>
						</tr>
						<tr>
							<td style="height:80px; text-align:right">活动链接：
							  <textarea rows="1" cols="20" name="addPromoForm.link">
								
							  </textarea>
							  
							  </td>
							<td style="height:80px; ">
							  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 活动备注:
							  <textarea rows="1" cols="20" name="addPromoForm.jdDesc">
								
							  </textarea>
			                </td>
						</tr>
						
						<tr>
							<td style="height:80px; text-align:right"><input id="" style="width:80px" type="button" value="取消"></td>
							<td style="height:80px; ">
							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="addPromo" type="button" style="width:80px"  value="提交" onclick="addPromoData()">
			                </td>
						</tr>
						</form>
						</tbody>
						</table>
						
						</div>
				</td>
			</tr>
			
		</tbody>
	</table>
</body>


</html>
