<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>京东属性和属性值拉取</title> 
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
</head>
<body>
	<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
		<tbody> 
			<tr> 
				<td class="td01"></td> 
				<td class="td02"><h3 class="topTitle fb f14">京东属性和属性值拉取</h3></td> 
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
								<dd>京东属性和属性值拉取</dd>
							</dl>
						</div>
	<form id="wareInfoForm" method="post" action="<%=request.getContextPath() %>/jdGoods/wareInfoList.action">
		<table class="mytable">
			<tr>
				<td style="width: 500px">京东分类:
					<s:select list="jdCategories"  id="firstCategoriesSelect"  theme="simple" name="firstCategoryId" 
					 onchange="getNextCategory(this.value,'2')" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
					
				    <s:select list="jdCategories2"  id="sendCategorySelect"  theme="simple" name="sendCategoryId"  value="sendCategoryId" 
				    onchange="getNextCategory(this.value,'3')" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
						
					<s:select list="jdCategories3"  id="threeCategorySelect"  theme="simple"  name="threeCategoryId" value="threeCategoryId" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
				</td>
			</tr>
			<tr id="checkPass" style="display:none">
				<td>验证密码：<input type="password" id="pullCheckPass" value=""/></td>
			</tr>
			<tr>
				<td><input type="button" value="拉取属性和属性值" id="pullAttrAndValue"/></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><textarea id="xiuCodes" cols="80" rows="5"></textarea></td>
			</tr>
			<tr>
				<td><input type="button" value="拉取京东后台商品" id="pullProduct"/></td>
			</tr>
			<div id="check">请稍后查看数据</div>
	  </table>
 </form>
</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">

function getNextCategory(categoryId,num){
	var sendCategorySelect=$("#sendCategorySelect");
	var threeCategorySelect=$("#threeCategorySelect");
	if(categoryId==null || categoryId==""){
		sendCategorySelect.hide();
		threeCategorySelect.hide();
	}
	
	if(num==2){
		sendCategorySelect.empty();
		threeCategorySelect.empty();
	}
	
	if(num==3){
		threeCategorySelect.empty();
		
	}
	$.ajax({type:'post',
		url:'<%=request.getContextPath()%>/jdGoods/getJdCategory.action',
		data:'categoryId='+categoryId,
		dataType:'text',
		success:function(data){
			var jsonObj = eval(''+data+'');
			if(jsonObj!=null && jsonObj!=""){
			      
				if(num==2){
				$("<option>").val("").text("==请选择分类==").appendTo("#sendCategorySelect");
				sendCategorySelect.show();
				threeCategorySelect.hide();
				}
				if(num==3){
					$("<option>").val("").text("==请选择分类==").appendTo("#threeCategorySelect");
					threeCategorySelect.show();
					
				}
				
			}
			 for(var i=0;i<jsonObj.length;i++){
				var name=jsonObj[i].name;
				var id=jsonObj[i].id;
				var option=$("<option>");
				if(num==2){
					$(option).val(id).text(name).appendTo("#sendCategorySelect");
				}
				if(num==3){
					$(option).val(id).text(name).appendTo("#threeCategorySelect");
				}
			} 
		}
	});
	
}
 
$("#pullAttrAndValue").click(function(){
	var selectCategory=true;
	var firstCategoryVal=$('#firstCategoriesSelect').val();
	 if(firstCategoryVal!=null && firstCategoryVal!=""){
		 var sendCategoryVal=$('#sendCategorySelect').val();
		 if(sendCategoryVal==null || sendCategoryVal==""){
			 selectCategory=false;
		 }
	       var threeCategoryVal=$('#threeCategorySelect').val();
		 if(threeCategoryVal==null || threeCategoryVal==""){
			 selectCategory=false;
		 }
	
	 }
	 if(!selectCategory){
		 alert("请选择下分类");
		 return ;
	 }
	var pullCheck =  $("#pullCheckPass").val();
	if(pullCheck != null && pullCheck=="123456"){
		var action = "<%=request.getContextPath() %>/jdGoods/pullAttrAndValue.action";
		$('#wareInfoForm').attr("action",action);
		$('#wareInfoForm').submit();
	}else{
		 alert("请输入验证密码");
		 return ;
	}
})

$("#threeCategorySelect").click(function(){
	var threeCategoryValue=$("#threeCategorySelect").val();
	if(threeCategoryValue != null && threeCategoryValue !=""){
		$("#checkPass").show();
	}
})

$("#pullProduct").click(function(){
	var isConfirm = confirm("你确定执行此操作?");
	if(isConfirm){
		$("#pullProduct").attr("disabled",true);
		var xiuCodes = $("#xiuCodes").val();
		$.ajax({type:'post',
			url:'<%=request.getContextPath() %>/jdGoods/pullProduct.action',
			data:"xiucodes="+xiuCodes,
			dataType:'text',
			success:function(msg){
				if(msg=="success"){
					$("#check").show();
				}
			}
		});
		<%-- if(xiuCodes != null && $.trim(xiuCodes) != ""){
			window.location.href = "<%=request.getContextPath() %>/jdGoods/pullProduct.action?xiucodes="+xiuCodes;
		} --%>
	}
})
</script>
</body>
</html>