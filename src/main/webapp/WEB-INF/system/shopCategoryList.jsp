<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>京东店内分类列表</title> 
<link href="<%=request.getContextPath()%>/template/css/base.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/common.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/page_admin_main.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/template/javascript/calendar/My97DatePicker/WdatePicker.js" type="text/javascript"></script>	
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/goodsList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery-1.3.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weebox.css"	 />
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weebox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/bgiframe.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/weescroller.js"></script>
<!-- 自动填充插件 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery.autocomplete.js"></script>

</head>
<body>
	<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
		<tbody> 
			<tr> 
				<td class="td01"></td> 
				<td class="td02"><h3 class="topTitle fb f14">京东店内分类列表</h3></td> 
				<td class="td03"></td> 
			</tr> 
		</tbody> 
	</table>

	<table border="0" cellspacing="0" cellpadding="0" class="adminMain_main"> 
		<tbody> 
			<tr> 
				<td class="td01"></td> 
				<td class="adminMain_wrap"> 
					<div class="wrapMain"> 
	<form id="shopCategoryForm" method="post" action="<%=request.getContextPath() %>/jdGoods/updateShopCategory.action">
		<input id="jdwardIds" type="hidden" name="jdwardIds" value="<s:property value="jdwardIds"/>"/>
		<table>
			<tbody>
				<tr>
					<td>
						<dl class="store-type">
							<s:iterator value="jdSellerCategoryMap" var="firstCat">
								<dt><label><s:property value="#firstCat.key.name"/></label></dt>
								<dd>
									<ul>
										<s:iterator value="#firstCat.value" var="secondCat">
										<li style="position: relative;left:30px;">
											<input type="checkbox" title="<s:property value="#secondCat.parentId"/>-<s:property value="#secondCat.cid"/>"/>
											<label><s:property value="#secondCat.name"/></label>
										</li>
										</s:iterator>
									</ul>
								</dd>
							</s:iterator>
						</dl>
					</td>
				</tr>
			</tbody>
		</table>
		<input id="coverShopCat" type="button" value="覆盖"/>&nbsp;&nbsp;&nbsp;<input id="addShopCat" type="button" value="新增"/>
	</form>
</div> 
</td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">
	$("#coverShopCat").click(function(){
		var shopCategoryId = "";
		var isChecked = false;
		$("input[type='checkbox']").each(function(){
			isChecked = $(this).attr("checked");
			if(isChecked){
				shopCategoryId = shopCategoryId + $(this).attr("title")+";";
			}
		});
		if(shopCategoryId == ""){
			alert("请选择京东店内分类");
			return;
		}else{
			shopCategoryId = shopCategoryId.substring(0, shopCategoryId.length-1);
		}
		
		var jdwareIds = $("#jdwardIds").val();
	
		$.ajax({type:'post',
			url:'<%=request.getContextPath() %>/jdGoods/updateShopCategory.action',
			data:'jdShopCategory='+shopCategoryId+'&jdwardIds='+jdwareIds,
			dataType:'text',
			success:function(data){
				if(data!=null){
					if("success"==data){
						var parentForm = window.parent.document.forms[0];
						var action ="<%=request.getContextPath() %>/jdGoods/wareInfoList.action";
						$(parentForm).attr("action",action);
						$(parentForm).submit();
						window.parent.window.$.weeboxs.close();
					}
				}
			}
		});
	});
	
	$("#addShopCat").click(function(){
		var shopCategoryId = "";
		var isChecked = false;
		$("input[type='checkbox']").each(function(){
			isChecked = $(this).attr("checked");
			if(isChecked){
				shopCategoryId = shopCategoryId + $(this).attr("title")+";";
			}
		});
		if(shopCategoryId == ""){
			alert("请选择京东店内分类");
			return;
		}else{
			shopCategoryId = shopCategoryId.substring(0, shopCategoryId.length-1);
		}
		
		var jdwareIds = $("#jdwardIds").val();
	
		$.ajax({type:'post',
			url:'<%=request.getContextPath() %>/jdGoods/addShopCategory.action',
			data:'jdShopCategory='+shopCategoryId+'&jdwardIds='+jdwareIds,
			dataType:'text',
			success:function(data){
				if(data!=null){
					if("success"==data){
						var parentForm = window.parent.document.forms[0];
						var action ="<%=request.getContextPath() %>/jdGoods/wareInfoList.action";
						$(parentForm).attr("action",action);
						$(parentForm).submit();
						window.parent.window.$.weeboxs.close();
					}
				}
			}
		});
	});
	
</script>
</body>
</html>