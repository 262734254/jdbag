<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>商品SKU分页信息</title> 
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
				<td class="td02"><h3 class="topTitle fb f14">商品SKU分页信息</h3></td> 
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
								<dd>商品SKU确认信息</dd>
							</dl>
						</div>
	<form id="skuInfoForm" method="post" action="<%=request.getContextPath() %>/jdGoods/skuInfoList.action">
		<input type="hidden" name="currentPage" value="${currentPage }"/>
		<input type="hidden" name="pageSize" value="${pageSize}"/>
		
		<table class="mytable">
			<tr>
				<%-- <td>导入excel的批次号&nbsp;<s:textfield id="num" theme="simple"  name="num"></s:textfield></td> --%>
				<td style="width: 500px">京东分类ID&nbsp;
					<s:select list="jdCategories"  id="firstCategoriesSelect"  theme="simple" name="firstCategoryId" 
					 onchange="getNextCategory(this.value,'2')" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
					
			
				    <s:select list="jdCategories2"  id="sendCategorySelect"  theme="simple" name="sendCategoryId"  value="sendCategoryId" 
				    onchange="getNextCategory(this.value,'3')" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
						
					<s:select list="jdCategories3"  id="threeCategorySelect"  theme="simple"  name="threeCategoryId" value="threeCategoryId" 
					listKey="id" listValue="name"  headerKey="" headerValue="==请选择分类==" /> 
					</td>
				</td>
				<td style="width: 250px">商品走秀码&nbsp;<s:textfield theme="simple" name="jdSkuInfoForm.xiuCode" /></td>
				<td style="width: 350px">京东商品ID&nbsp;<s:textfield theme="simple" name="jdSkuInfoForm.jdWareId" /></td>
			</tr>
			<tr>
				<td colspan="2">创建时间&nbsp;&nbsp;
					<s:textfield id="startTime" name="jdSkuInfoForm.startTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					至
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:textfield id="endTime" name="jdSkuInfoForm.endTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
				</td>
				<td>图片上传状态&nbsp;&nbsp;<s:select id="resultStatus"  theme="simple" name="jdSkuInfoForm.status" headerKey="" headerValue="全部" list="#{'0':'图片未上传','1':'图片上传成功','2':'图片上传失败','3':'该颜色图片已经上传','4':'图片上传异常'}" cssStyle="width:100px" value="jdSkuInfoForm.status"></s:select></td>
			</tr>
			<tr>
				<td>商品SKU码&nbsp;<s:textfield theme="simple" name="jdSkuInfoForm.skuSn" /></td>
				
				<td colspan="2">图片状态更新为&nbsp;<s:textfield theme="simple" name="jdSkuInfoForm.statu" /><font style="color:red">该输入项不会被设置为查询条件</font></td>
			</tr>
			<tr>
				<td colspan="3"><span style="float: left;padding-top: 50px;">京东商品sku编码：<font style="color:red;">(每个京东商品sku编码独占一行)</font></span>
					<textarea cols="60" rows="6" name="jdSkuIds"><s:property value="jdSkuIds"/></textarea>
				</td>
			</tr>
			<tr>
				<td style="width: 350px;">
					<input id="query" type="button" value="查询"/>
					<input id="delete" type="button" value="删除"/><font style="color:red;">(只支持根据京东商品sku编码删除条件)</font>
				</td>
			</tr>
			<tr></tr>
		</table>
		<hr />
		<table>
			<tr>
				<td colspan="3">
					<a href="#" class="checkAll">全选</a>
					<a href="#" class="inverse">反选</a>
					<input type="button" value="删除选中项" onclick="deleteSku()"/>
				</td>
			</tr>
		</table>
		<hr />
		<table class="listtable">
			<thead>
				<tr>
					<th width="5%">请选择</th>
					<th width="5%">京东分类</th>
					<th width="5%">京东商品ID</th>
					<th width="5%">商品走秀码</th>
					<th width="5%">商品sku码</th>
					<th width="5%">京东商品sku编码</th>
					<th width="10%">图片上传状态</th>
					<th width="40%">商品主图路径</th>
					<th width="5%">编辑</th>
				</tr>
			</thead>
			
			<tbody>
				<s:iterator value="pageView.records" var="resultObject" >
					<tr class="myclass">
						<td style="width: 30px;text-align: center"><input type="checkbox" value="<s:property value="#resultObject.jdSkuId" />"/></td>
						<td   style="width: 30px;">
							<s:property value="#resultObject.cid" />
						</td>
						<td style="width: 30px;"><s:property value="#resultObject.jdWareId" /></td>
						<td  style="width: 30px;"><s:property value="#resultObject.xiuCode" /></td>
						<td  style="width: 30px;"><s:property value="#resultObject.skuSn" /></td>
						<td  style="width: 30px;"><s:property value="#resultObject.jdSkuId" /></td>
						<td >
							<s:if test="status==0">
								图片未上传
							</s:if>
							<s:elseif test="status==1">
								图片上传成功
							</s:elseif>
							<s:elseif test="status==2">
								图片上传失败
							</s:elseif>
							<s:elseif test="status==3">
								该颜色图片已上传
							</s:elseif>
							<s:else>
								图片上传异常
							</s:else>
						</td>
						<td  style="width: 300px;" ><s:property value="#resultObject.mainImagePath" /></td>
						<td class="tlr" style="text-align:center;"><a class="modify">修改</a></td>
					</tr>
				</s:iterator>
			</tbody>
			
			<tbody>
			<%@ include file="/admin/common/page.jsp"%>
			</tbody>
		</table>
		<table>
			<tr>
				<td colspan="3">
					<a href="#" class="checkAll">全选</a>
					<a href="#" class="inverse">反选</a>
					<input type="button" value="删除选中项" onclick="deleteSku()"/>
				</td>
			</tr>
		</table>
	</form>
</div> 
</td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">
	
	$("#query").click(function(){
		var action = "<%=request.getContextPath() %>/jdGoods/skuInfoList.action";
		$('#skuInfoForm').attr("action",action);
		$('#skuInfoForm').submit();
	});
	
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.action = "<%=request.getContextPath()%>/jdGoods/skuInfoList.action";
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
	
	$(".modify").mouseover(function(){
		$(this).css("cursor","pointer");
	});
	//全选
	$(".checkAll").click(function(){
		$("input[type='checkbox']").each(function(){
			$(this).attr("checked",true);
		})
	})
	//反选
	$(".inverse").click(function(){
		var isChecked = false;
		$("input[type='checkbox']").each(function(){
			isChecked = $(this).attr("checked");
			if(isChecked){
				$(this).attr("checked",false);
			}else{
				$(this).attr("checked",true);
			}
		})
	});
	//批量删除京东商品SKU
	function deleteSku(){
		var isSelect = false;
		var jdSkuIds = "";
		//循环复选框，查找选中项
		$("input[type='checkbox']").each(function(){
			var isChecked = $(this).attr("checked");
			if(isChecked){//该复选框已被选中
				isSelect = true;
				jdSkuIds = jdSkuIds + $(this).val()+",";
			}
		})
		
		if(jdSkuIds != ""){//存在被选中的复选框，将SKU的最后一个逗号去掉
			jdSkuIds = jdSkuIds.substring(0,jdSkuIds.length-1);
		}
		
		if(!isSelect){
			alert("请选择需要删除的选项");
		}else{
			if(confirm("您确定要删除选中项吗?")){
				var action = "<%=request.getContextPath() %>/jdGoods/deleteSku.action?delJdSkuIds="+jdSkuIds;
				$("#skuInfoForm").attr("action",action);
				$("#skuInfoForm").submit();
			}
		}
	}
	
	$("#delete").click(function(){
		if(confirm("您确定要执行此操作吗?")){
			var action = "<%=request.getContextPath() %>/jdGoods/deleteSkuBySkuIds.action";
			$("#skuInfoForm").attr("action",action);
			$("#skuInfoForm").submit();
		}
	})
	
	//根据京东父分类查询下级分类
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
	
</script>
</body>
</html>