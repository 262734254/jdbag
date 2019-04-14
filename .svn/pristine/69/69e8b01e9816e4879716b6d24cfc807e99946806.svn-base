<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单SKU详情信息</title>
<link href="<%=request.getContextPath()%>/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/template/css/weebox.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/jqueryupload/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/weebox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/bgiframe.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weebox.css"	 />
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weebox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/bgiframe.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weescroller.js"></script>

<script type="text/javascript">
function closeBox(){
	
    window.parent.window.$.weeboxs.close();

   }
		
</script>


</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">订单SKU详情信息</h3></td>
				<td class="td03"></td>
			</tr>
		</tbody>
	</table>
	<table class="adminMain_main">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="adminMain_wrap">
					<div class="wrapMain">
							<div class="adminContent clearfix">

								  	<table width="100%" border="1" cellpadding="0" cellspacing="0" class="table_bg02" style="margin-top: 10px">
								  	<tr style="text-align: center;">
										<td class="tdBox" >商品名称</td>
										<td class="tdBox" >走秀SKU码</td>
										<td class="tdBox" >购买数量</td>
									</tr>
									<s:if test="itemInfos.size()>0">
									   <s:iterator value="itemInfos" var="resultObject">
											<tr style="text-align: center;">
											<td class="tdBox" style="width: 60%" >
											
											   <a href="http://item.jd.com/<s:property value='#resultObject.skuId' />.html" target="_blank" >
											   <s:property value='#resultObject.skuName' />
											   </a>
												
											</td>
											<td class="tdBox" style="width: 20%" > 
												<s:property value='#resultObject.outerSkuId' />
											</td>
											<td class="tdBox" style="width: 20%" >
												<s:property value='#resultObject.itemTotal' />
											</td>
									 </tr>
									</s:iterator>
									</s:if>
								   <s:else>
								   <tr>
								      <td class="tdBox" colspan="5">对不起，该订单目前无购买信息 </td>
								    </tr >
								   </s:else>
								</table>
								<input style="text-align: center;margin-left: 250px" type="button"  value=" 返回   "  onclick="javascript:closeBox()"/>
								
								<!--adminContent-->
							</div>
							<!--主体内容结束-->
						
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
