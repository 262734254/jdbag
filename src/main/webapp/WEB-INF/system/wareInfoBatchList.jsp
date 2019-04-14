<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>商品分页信息</title> 
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
				<td class="td02"><h3 class="topTitle fb f14">商品分页信息</h3></td> 
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
								<dd>商品确认信息</dd>
							</dl>
						</div>
	<form id="wareInfoForm" method="post" action="<%=request.getContextPath() %>/jdWare/wareInfoList.action">
		<input type="hidden" name="currentPage" value="${currentPage }"/>
		<input type="hidden" name="pageSize" value="${pageSize}"/>
		<input type="hidden" id="stockOrder" name="stockOrder" value="<s:property value="stockOrder"/>"/>
	    <input type="hidden" id="jdstockNumOrder" name="jdstockNumOrder" value="<s:property value='jdstockNumOrder'/>"/>
	    <input type="hidden" name="jdwardIds" id="jdwardIds" value=""/>
		<s:if test="status!=0">
		<input id="flag" type="hidden" name="flag" value="flag"/>
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
				<td  style="width: 250px">商品标题&nbsp;
					<s:textfield theme="simple" name="jdProductInfoForm.title" />
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td  style="width: 500px" >创建时间&nbsp;&nbsp;
					<s:textfield id="startTime" name="jdProductInfoForm.startTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					至
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<s:textfield id="endTime" name="jdProductInfoForm.endTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
				</td>
				<td style="width: 500px">批 次 号&nbsp;&nbsp;&nbsp;<s:textfield theme="simple" name="jdProductInfoForm.num" />
				是否推送&nbsp;&nbsp;<s:select id="resultStatus"  theme="simple" name="jdProductInfoForm.status" headerKey="" headerValue="全部" list="#{'0':'未推送','1':'已推送','2':'推送失败'}" cssStyle="width:100px" value="jdProductInfoForm.status"></s:select></td>
			</tr>
			
			<tr>
				<td style="width: 500px">京东商品ID&nbsp;<s:textfield theme="simple" name="jdProductInfoForm.jdWareId" />
					&nbsp;库存&nbsp;&nbsp;<s:select id="wareStock"  theme="simple" name="jdProductInfoForm.wareStock" headerKey="" headerValue="全部" list="#{'0':'库存为0','1':'库存不为0'}" cssStyle="width:100px" value="jdProductInfoForm.wareStock"></s:select>
				</td>
				<td style="width: 500px">店铺分类 
					<s:select id="firstShopCategory" list="firstShopCategorys" theme="simple" name="firstShopCategoryId" listKey="cid"
					listValue="name" headerKey="" headerValue="请选择店铺一级分类"
					onchange="getNextShopCategory(this.value)"></s:select>
					<s:select id="secondShopCategory" list="secondShopCategorys" theme="simple" name="secondShopCategoryId" value="secondShopCategoryId" listKey="cid"
					listValue="name" headerKey="" headerValue="请选择店铺二级分类"></s:select>
				</td>
			</tr>
			<tr>
				<td style="width: 500px">商品销售售状态&nbsp;
					<s:select id="resultOnLineStatus"  theme="simple" name="jdProductInfoForm.onLineStatus" headerKey="" headerValue="全部" list="#{'0':'未上架','1':'在售','2':'下架'}" cssStyle="width:100px" value="jdProductInfoForm.onLineStatus"></s:select>
				</td>
			</tr>
			<tr>
			  <td colspan="2"><span style="float: left;padding-top: 40px;">商品走秀码:<font style="color: red">(每个走秀码独占一行)</font></span>
			    <textarea rows="5" cols="80" name="xiuCodes" ><s:property value="xiuCodes"/></textarea>
			  </td>
			</tr>
			<tr>
				<td style="width: 150px;"><input id="query" type="button" value="查询"/></td>
				<!-- <td ><input id="export" type="button" value="导出"/></td> -->
			</tr>
	
		<tr>
		 <td colspan="17"> <hr style="width: 100%;color: black" /></td>
		
		</tr>
		
		<td colspan="4">
			<a href="#" class="isgray"
		 <s:if test="pageView.records.size()>0">onclick="checkAll('xiucodes')" </s:if>
		<s:else> style="text-decoration: underline;color: gray;"</s:else> 
		><!-- 全选 -->  </a>
		<a href="#" class="isgray" <s:if test="pageView.records.size()>0">onclick="reserveCheck('xiucodes')"</s:if> <s:else> style="text-decoration: underline;color: gray;"</s:else>  ><!-- 反选 --> </a>
       <%-- <input id="deleteProductByXiuCodes"  <s:if test="pageView.records.size()<1">disabled="disabled"</s:if>  type="button" onclick="deleteProduct()" value="删除选中项"/>	
		<input type="button" onclick="shopCategoryList('<%=request.getContextPath() %>/jdWare/shopCategoryList.action')" value="编辑店内分类"/> --%>
		
		
	<!-- 	<label><input name="shopType" type="checkbox" value="ebay" />同步到ebay店 </label> 
		<label><input name="shopType" type="checkbox" value="bag" />同步到包店</label>  -->
		<label><input name="shopType" type="hidden" value="bag" checked ='checked' /></label> 
		
		<input type="button" onclick="pushProductInAll()" value="推送全部商品"/> 
		
		<input type="button" onclick="pushProductInSelect()" value="推送选中商品"/> 
		
		 </td>
		</tr>
		</table>
		<hr />
		</s:if>
		<s:else>
			<input type="hidden" name="num" value="${num}"/>
		</s:else>
		<table class="listtable" id="idTable">

			<thead>
				<tr>
				  <th style="display: none"></th>
					<th width="5%">全选</th>
					<th width="5%">京东分类</th>
					<th width="8%">京东商品ID</th>
					<th width="10%">京东标题</th>
					<th width="8%">商品走秀码</th>

					<th width="5%">商品京东价</th>
					<th width="4%" id="idSize" >
					  <span  class="xs-sort-bar">
						<dl  class="sort-box">
			                    
			                    <dd><a  style="color: #666666;text-decoration: none;" href="#" title="<s:property value='stockOrder'/>"  class="" id="jdStockNum">
			                                                   库存
			                    <b class=""></b><i id="jdStockNumOrderValue" class="<s:property value='jdstockNumOrder'/>"></i></a>
			                    </dd>
			              </dl>
					</span>
					</th>
					<th width="5%">是否推送</th>
					<th width="5%">批次号</th>
					<th width="5%">销售状态</th>
					<th width="8%">店铺分类</th>
					<th width="5%">品牌编码</th>
					<th width="8%">创建时间</th>
					<th width="5%">编辑</th>
				</tr>
			</thead>
			
			<tbody>
				<s:iterator value="pageView.records" var="resultObject" >
				
					<tr class="myclass">
					<td style="display: none"><s:property value="#resultObject.categoryid" /></td>
					    <td style="height:50px;text-align:center;" nowrap><!-- 选择 -->	
					   	
						<input type="checkbox" name="xiucodes"  value="<s:property value='#resultObject.xiucode' />"
						
						  />	
							
		       	      </td>
					
						<%-- <td style="display: none"><s:property value="#resultObject.categoryid" /></td> --%>
						<td  class="tlr" style="text-align: center">
							<s:property value="#resultObject.cid" />
						</td>
						<td class="tlr"><s:property value="#resultObject.jdWareId" /></td>
						<td class="tlr"><s:property value="#resultObject.title" /></td>
						<td class="tlr"><s:property value="#resultObject.xiucode" /></td>
						<td class="tlr"><s:property value="#resultObject.jdprice" /></td>
						<td class="tlr"><s:property value="#resultObject.stocknum" /></td>
						<td class="tlr">
							<s:if test="status==1">
								已推送
							</s:if>
							<s:elseif test="status==0">
								未推送
							</s:elseif>
							<s:elseif test="status==2">
								推送失败
							</s:elseif>
							<s:else>
								已推送
							</s:else>
						</td>
						<td class="tlr"><s:property value="#resultObject.num" /></td>
						<td class="tlr">
							<s:if test="onLineStatus==1">
								在售
							</s:if>
							<s:elseif test="onLineStatus==2">
								下架
							</s:elseif>
							<s:elseif test="onLineStatus==0">
								未上架
							</s:elseif>
						</td>
						<td class="tlr"><s:property value="#resultObject.shopCategory" /></td>
						<td class="tlr"><s:property value="#resultObject.brandCode" /></td>
						<td class="tlr"><s:property value="#resultObject.createdate" /></td>
						<td class="tlr" style="text-align:center;"><a class="modify">修改</a></td>
					</tr>
				</s:iterator>
			</tbody>
			<tbody>
			<%@ include file="/admin/common/page.jsp"%>
			</tbody>
		</table>
		
		<a href="#" class="isgray"
		 <s:if test="pageView.records.size()>0">onclick="checkAll('xiucodes')" </s:if>
		<s:else> style="text-decoration: underline;color: gray;"</s:else> 
		>全选  </a>
		<a href="#" class="isgray" <s:if test="pageView.records.size()>0">onclick="reserveCheck('xiucodes')"</s:if> <s:else> style="text-decoration: underline;color: gray;"</s:else>  >反选 </a>
       <%-- <input id="deleteProductByXiuCodes"  <s:if test="pageView.records.size()<1">disabled="disabled"</s:if>  type="button" onclick="deleteProduct()" value="删除选中项"/>	
       
	   <input type="button" onclick="shopCategoryList('<%=request.getContextPath() %>/jdWare/shopCategoryList.action')" value="编辑店内分类"/> --%>
 </form>
</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">

$(document).ready(function() {
	 var isDisabled=false;
	   var names = document.getElementsByName("xiucodes");
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
	
	$("#query").click(function(){
		var selectCategory=true;
		var firstCategoryVal=$('#firstCategoriesSelect').val();
		var shopCategorySelect = true;
		var firstShopCategoryVal = $("#firstShopCategory").val();
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
		 if(firstShopCategoryVal != null && firstShopCategoryVal != ""){
			 var secondShopCategoryVal = $("#secondShopCategory").val();
			 if(secondShopCategoryVal == null || secondShopCategoryVal==""){
				 shopCategorySelect = false;
			 }
		 }
		 
		 if(!shopCategorySelect){
			 alert("请选择京东店内分类");
			 return ;
		 }
		 
		var action = "<%=request.getContextPath() %>/jdWare/wareInfoBatchList.action";
		$('#wareInfoForm').attr("action",action);
		var form = document.forms[0];
		form.currentPage.value=1;
		$('#wareInfoForm').submit();
	});
	
	//到指定的分页页面
	function topage(page){
		var form = document.forms[0];
		form.action = "<%=request.getContextPath()%>/jdWare/wareInfoBatchList.action";
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
		window.open("<%=request.getContextPath()%>/jdWare/wareInfoModify.action?xiucode="+xiucode+"&num="+num+"&flag="+flagVaule+"&xiuCategoryId="+categoryId+"&brandCode="+brandCode,"newwindow");
	});
	
	$("#export").click(function(){
		var action = "<%=request.getContextPath()%>/jdWare/exportWareInfo.action";
		$('#wareInfoForm').attr("action",action);
		$('#wareInfoForm').submit();
		<%-- window.location.href="<%=request.getContextPath()%>/jdWare/exportWareInfo.action"; --%>
	});
	
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
			url:'<%=request.getContextPath()%>/jdWare/getJdCategory.action',
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
	
	//批量删除商品
	function deleteProduct(){
		var isSelected=false;
		//需要判断复选框是否又被选中了
	   var names = document.getElementsByName("xiucodes");
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
			   
		
	   if(isSelected){
		  var isConfirm= confirm("您确定要执行此操作么?");
		   if(isConfirm){
			   var form = document.forms[0];
			   //synStatus=1是要删除京东的商品,isDelete=1删除后要回到商品列表页面
			  var requestPath= "<%=request.getContextPath()%>/jdWare/deleProduct.action?synStatus=1&isDelete=1";
				form.action =requestPath ;
				form.submit();
		   }
		   
	   }else{
		   alert("请选中操作项");
	   }
		   
	}
	
	
	//批量删除商品
	function pushProductInSelect(){
		var isSelected=false;
		//需要判断复选框是否又被选中了
	   var names = document.getElementsByName("xiucodes");
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
			   
		
	   if(isSelected){
		  var isConfirm= confirm("您确定要推送选中商品吗?");
		   if(isConfirm){
			   var form = document.forms[0];
			   //synStatus=1是要删除京东的商品,isDelete=1删除后要回到商品列表页面
			<%--   var requestPath= "<%=request.getContextPath()%>/jdWare/deleProduct.action?synStatus=1&isDelete=1"; --%>
			  
			  var requestPath = "<%=request.getContextPath() %>/jdWare/pushWareInBatch.action?isPushAll=1";
				form.action =requestPath ;
				form.submit();
		   }
		   
	   }else{
		   alert("请选中操作项");
	   }
		   
	}
	
	
	//批量删除商品
	function pushProductInAll(){
	
	  var isConfirm= confirm("您确定要推送所有商品吗?");
	   if(isConfirm){
		   var form = document.forms[0];
		   //synStatus=1是要删除京东的商品,isDelete=1删除后要回到商品列表页面
		<%--   var requestPath= "<%=request.getContextPath()%>/jdWare/deleProduct.action?synStatus=1&isDelete=1"; --%>
		  
		  var requestPath = "<%=request.getContextPath() %>/jdWare/pushWareInBatch.action?isPushAll=0";
			form.action =requestPath ;
			form.submit();
	   }  
	  
		   
	}
	
	
	function getNextShopCategory(shopCategoryId){
		var secondShopCategory = $("#secondShopCategory");
		if(shopCategoryId==null || shopCategoryId==""){
			secondShopCategory.hide();
		}
		
		secondShopCategory.empty();
		$.ajax({type:'post',
			url:'<%=request.getContextPath()%>/jdWare/getJdShopCategory.action',
			data:'shopCategoryId='+shopCategoryId,
			dataType:'text',
			success:function(data){
				var jsonObj = eval(''+data+'');
				if(jsonObj!=null && jsonObj!=""){
				$("<option>").val("").text("请选择店铺二级分类").appendTo("#secondShopCategory");
					secondShopCategory.show();
				}
				for(var i=0;i<jsonObj.length;i++){
					var name=jsonObj[i].name;
					var cid=jsonObj[i].cid;
					var option=$("<option>");
					$(option).val(cid).text(name).appendTo("#secondShopCategory");
				} 
			}
		});
	};
	
	
	$("#idSize").click(function(){
		var stockStr = $("#stockOrder").val();
		if(stockStr==null || $.trim(stockStr) == ''){
			stockStr = "asc";
		}else if(stockStr=="desc"){
			stockStr="asc";
		}else{
			stockStr="desc";
		}
		$("#stockOrder").val(stockStr);
		var jdStockNum=$("#jdStockNum").attr('title');
		//升序   ""
		if("asc"==jdStockNum){
			$("#jdStockNumOrderValue").removeClass("down");
			$("#jdstockNumOrder").val("down");
			
		}else{
			//降序  jdstockNumOrder down
			$("#jdStockNumOrderValue").addClass("down");
			$("#jdstockNumOrder").val("");
		}
		var action ="<%=request.getContextPath() %>/jdWare/wareInfoList.action";
		$('#wareInfoForm').attr("action",action);
		$('#wareInfoForm').submit();
	})
	
	function shopCategoryList(url){
		var isXuan = false;
		var jdWareIds ="";
		var stock = "";
	    $("input[type='checkbox'][name='xiucodes']").each(function(){
	    	var checkBox = $(this).attr("checked");
	    	if(checkBox){
	    		isXuan = true;
	    		jdWareIds = jdWareIds+$(this).parent().next().next().text()+",";
	    		stock = $(this).parent().next().next().next().next().next().next().text();
	    		if(stock.trim() == '0'){
	    			return false;
	    		}
	    	}
	    });
	    if(stock.trim()=='0'){
	    	alert("商品的库存为0,不能更新商品店内分类，请重新选择");
	    	return;
	    }
	    
		if(!isXuan){
			alert("请选择要修改店内分类的商品ID");
			return;
		}
		if(jdWareIds != ""){
			jdWareIds = jdWareIds.substring(0, jdWareIds.length-1);
			$("#jdwareIds").val(jdWareIds);
		}
		url = url+'?jdwardIds='+jdWareIds;
		weeboxDialog = $.weeboxs.open(url, {
			
			title : '店内分类批量修改',
			contentType : 'iframe',
			width : 280,
			height : 470,
			async : false,
			modal: true,
			showButton: false
		});
	}
	
	
	function pushInBatch(){
		var action = "<%=request.getContextPath() %>/jdWare/pushWareInBatch.action";
		$('#wareInfoForm').attr("action",action);
		var form = document.forms[0];
		form.currentPage.value=1;
		$('#wareInfoForm').submit();
	}
	
</script>
</body>
</html>