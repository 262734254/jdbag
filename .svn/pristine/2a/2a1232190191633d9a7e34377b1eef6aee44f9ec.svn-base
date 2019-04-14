<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>上下架不对接管理</title> 
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
				<td class="td02"><h3 class="topTitle fb f14">上下架不对接管理</h3></td> 
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
								<dd>上下架不对接管理</dd>
							</dl>
						</div>
		<form id="productBlackForm" method="post" action="<%=request.getContextPath() %>/jdGoods/blackProductList.action">
		<input type="hidden" id="currentPage" name="currentPage" value="${currentPage }"/>
		<input type="hidden" id="onClickQueryButton" name="onClickQueryButton" value="${onClickQueryButton}"/>
		<input type="hidden" id="recordId" name="id" value="${id }"/>
		
		<table class="mytable" border="0" width="100%">
		
		
		<!-- 
			private String xiuCode; //多个走秀码

	private String title; //商品名称
	
	private Integer timeType=0; //时间类型,0:请选择时间,1:上次确认时间,2:创建时间
	
	private String  importUserName; //导入的用户名称(操作人)
	
	private Integer onLineStatus=-1 ;//京东上下架状态：-1:京东上下架状态,0:未上架,1:在售,2:下架
	
	private Integer isButtJoint=0; //上下架对接状态: 0:上下架对接状态,1:未对接,2:已对接(走秀商品上下架是否与京东对接)
	 
	private Integer  confirmStatus=0; //确认状态: 0:确认状态 ,1:已确认,2:待确认,3:已过期(第一次导入确认状态为:1已确认是对未对接的确认)
	 
	private String startDate; //页面查询开始时间
		 
	private String endDate;//页面查询结束时间
		
		
		 -->
			<tr>
			     <td>走秀码:</td>
				 <td>
				    <textarea theme="simple" rows="3" cols="10" name="productForm.xiuCode" ><s:property value="productForm.xiuCode"/></textarea>
			     </td>
			
				   <td>
				             商品名称:<s:textfield theme="simple" name="productForm.title"></s:textfield>
				 </td>
				 
				 <td>
			              操作人:  <s:textfield theme="simple" name="productForm.confirmUserName"></s:textfield>
			      </td>
				
			</tr>
			
			

			
				  
                  <td>
		   		   <s:select  theme="simple" list="#{1:'已确认',2:'待确认',3:'已过期'}" listKey="key" listValue="value"
                                  name="productForm.confirmStatus" value="productForm.confirmStatus" headerKey="0" headerValue="确认状态">
                    </s:select>
                                   
				   </td>
				 
				 
				   <td>
		   		   <s:select  theme="simple" list="#{1:'上次确认时间',2:'创建时间'}" listKey="key" listValue="value"  onchange="isShowTime(this.value)"
                                  name="productForm.timeType" value="productForm.timeType" headerKey="0" headerValue="请选择时间">
                      </s:select>
                      
                        <span id="timeTd">
					<s:textfield id="startTime" name="productForm.startDate" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
					至
					<s:textfield  id="endTime" name="productForm.endDate" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
				   </span>
                                   
				   </td>
			
				 
				  
				   
				   
			       	 
			       <td>
		   		   <s:select  theme="simple" list="#{0:'未上架',1:'在售',2:'下架'}" listKey="key" listValue="value"
                                  name="productForm.onLineStatus" value="productForm.onLineStatus" headerKey="-1" headerValue="京东上下架状态">
                      </s:select>
                                   
				   </td>
				   
				     <td>
		   		   <s:select  theme="simple" list="#{1:'未对接',2:'已对接'}" listKey="key" listValue="value"
                                  name="productForm.isButtJoint" value="productForm.isButtJoint" headerKey="0" headerValue="上下架对接状态">
                      </s:select>
                                   
				   </td>


              <td>
		   		   <s:select  theme="simple" list="#{50:'50',100:'100',300:'300',500:'500'}" listKey="key" listValue="value"
                                  name="pageSize" value="pageSize" headerKey="0" headerValue="每页显示条数">
                      </s:select>
                                   
				   </td>
				
				
			</tr>

			<tr>
				<td style="width: 350px;" colspan="8"/>
					<input id="query" type="button" value="查询"/>&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="batchImportProduct" type="button" value="批量导入商品"/>
				</td>
			</tr>
			<tr></tr>
		</table>
		<hr />
	<!-- 	<table>
			<tr>
				<td colspan="3">
					<a href="#" class="checkAll">全选</a>
					<a href="#" class="inverse">反选</a>
					<input type="button" value="删除选中项" onclick="deleteSku()"/>
				</td>
			</tr>
		</table> -->
		<hr />
		<table class="listtable">
			<thead>
	
				<tr>
				    <th width="4%"><input  type="checkbox" <s:if test="#request.pageView==null or #request.pageView.records.size()<=0 "> disabled="disabled"</s:if> id="categoryIds"/></th>
				    <th width="6%">走秀码</th>
				    <th width="18%">商品名称</th>
				    <th width="10%">京东上架状态</th>
				    <th width="10%">上下架对接状态</th>
					<th width="8%">确认状态</th>
					<th width="12%">上次确认时间</th>
					<th width="12%">创建时间</th>
					<th width="7%">操作人</th>
					<th >操作</th>
				</tr>
			</thead>



	
			<tbody>
				<s:iterator value="#request.pageView.records" var="resultObject"  >
					<tr class="myclass">
					
					<td style="width: 5px; text-align: center;">
						<input type="checkbox" name="ids"  value="<s:property value='#request.resultObject.id'/>" />
						</td>
					   <td style="width: 5px;"><s:property value="#request.resultObject.xiuCode" /></td>
					   <td style="width: 15px;">
					    <s:if test="#request.resultObject.title!=null">
					      <s:property value="#request.resultObject.title" />
					    </s:if>
					    <s:else>
					     <font style="color:red">商品在店内不存在</font>
					    </s:else>
					   
					   
					   </td>
					   <!-- ;//商品销售状态：0:未上架,1:在售,2:下架 -->
					   <td style="width: 6px;">
							<s:if test="#request.resultObject.onLineStatus eq 0 ">
								未上架
							</s:if>
							<s:elseif test="#request.resultObject.onLineStatus eq 1">
							  在售
							</s:elseif>
							<s:elseif test="#request.resultObject.onLineStatus eq 2">
							下架 
							</s:elseif>
							<s:else>
							   未定义 
							</s:else>
					   </td>
					<!--    上下架对接状态,默认为1,未对接,2:已对接(走秀商品上下架是否与京东对接) -->
					    <td style="width: 6px;">
							<s:if test="#request.resultObject.isButtJoint eq 1 ">
								未对接
							</s:if>
							<s:elseif test="#request.resultObject.isButtJoint eq 2">
							         已对接
							</s:elseif>
							<s:else>
							   未定义 
							</s:else>
					   </td>
					   <!-- 确认状态:1:已确认,2:待确认,3:已过期(第一次导入确认状态为:1已确认是对未对接的确认) -->
					     <td style="width: 6px;">
							<s:if test="#request.resultObject.confirmStatus eq 1 ">
								已确认
							</s:if>
							<s:elseif test="#request.resultObject.confirmStatus eq 2">
							        待确认
							</s:elseif>
							<s:elseif test="#request.resultObject.confirmStatus eq 3">
							       已过期
							</s:elseif>
							<s:else>
							   未定义 
							</s:else>
					   </td>
					   
					   <td style="width: 10px;"><s:property value="#request.resultObject.confirmDate" /></td>
					    <td style="width: 10px;"><s:property value="#request.resultObject.createDate" /></td>
					   <td style="width: 25px;"><s:property value="#request.resultObject.confirmUserName" /></td>
					     <td style="width: 25px;">
					     <a 
					     <s:if test="#request.resultObject.confirmStatus eq 1 ">
								 style="text-decoration: underline;color: gray;"
							</s:if><s:else>
							 onclick="setOnLine(<s:property value='#request.resultObject.id'/>)"
							</s:else>
							
					     >上下架不对接 </a>
					          
					    &nbsp;&nbsp;&nbsp;
					          <a onclick="deleteProduct(<s:property value='#request.resultObject.id'/>)" >删除</a>
					     </td>
					</tr>
				</s:iterator>
			</tbody>
			
			<tbody>
			<%@ include file="/admin/common/page.jsp"%>
			</tbody>
			
			<tr >
			  <td style="width: 350px;" colspan="10"/>
			    <input type="button" value="批量上下架不对接 " onclick="batchSetOnLine()" <s:if test="#request.pageView==null or #request.pageView.records.size()<=0 ">disabled="disabled"</s:if>   />&nbsp;&nbsp;&nbsp;
			    <input type="button" value="导出商品" onclick="exportBlackProduct()" <s:if test="#request.pageView==null or #request.pageView.records.size()<=0">disabled="disabled"</s:if> />&nbsp;&nbsp;&nbsp;
			     <input type="button" value="批量删除 " onclick="batchDeleteProduct()" <s:if test="#request.pageView==null or #request.pageView.records.size()<=0">disabled="disabled"</s:if>  />&nbsp;&nbsp;
			  </td>
			</tr>
		</table>

	</form>
</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">
$(document).ready(function(){
		var timeType='${productForm.timeType }';
		if(timeType<=0){
			$('#timeTd').hide();
		}
	
});
$("#query").click(function(){
	var action = "<%=request.getContextPath() %>/jdGoods/blackProductList.action";
	$('#productBlackForm').attr("action",action);
	$('#onClickQueryButton').val(true);
	var currentPage =$('#currentPage').val(1);
	$('#productBlackForm').submit();
});

$("#goButton").click(function(){
	$('#onClickQueryButton').val(true);
	var page = $("#goPageno").val();
	if (! /[0-9]+/.test(page)) {
		alert("请输入数字");
		$("#goPageno").val('');
		return;
	}
	
	page = parseInt(page);
	var totalPage = '${pageView.totalpage }';
	if(totalPage==null || totalPage==''){
		totalPage=1;
	}
	
	if(page > totalPage) {
		page = totalPage;
	}
	
	topage(page);
});

//到指定的分页页面
function topage(page){
	$('#onClickQueryButton').val(true);
	var form = document.forms[0];
	form.action = "<%=request.getContextPath()%>/jdGoods/blackProductList.action";
	form.currentPage.value=page;
	form.submit();
}

function isShowTime(val){
	if(val){
		if(val>=1){
			$('#timeTd').show();
			return;
		}
	}
	$('#timeTd').hide();
}

$("#categoryIds").click(function(){
	
	//复选框是否被选中
	var isChecked=$(this).attr('checked');
	var checkName="ids";
	if(isChecked){
		//如果被选中,在所有复选框的也被选中
		checkAll(checkName);
	}else{
		//如果未被选中,在所有复选框的也未被选中
		reserveCheck(checkName);
	}
	
	
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
/**
 * 不对接商品上下架(单个)
 */
function setOnLine(id){
	if(id!=null && id!=""){
		if(confirm("您确定该商品不对接京东上下架么?")){ 	
			<%-- var url="<%=request.getContextPath()%>/jdGoods/setOnLine.action?id="+id;
			window.location.href=url; --%>
			
			 $("#recordId").val(id);
			 var url="<%=request.getContextPath()%>/jdGoods/setOnLine.action";
			 $("#productBlackForm").attr('action',url);
			 $("#productBlackForm").submit();
			 
			
		}
	}
	
}
function batchSetOnLine(){
	if(isSelected()){
		 var isConfirm= confirm("您确定要执行此操作么?");
		   if(isConfirm){
			   var url="<%=request.getContextPath()%>/jdGoods/setOnLine.action";
			   $("#productBlackForm").attr('action',url);
			   $("#productBlackForm").submit(); 
			   
		   }
	}else{
		 alert("请选中操作项");
	}
	
}
/**
 * 删除商品(单个)
 */
function deleteProduct(id){
	if(id!=null && id!=""){
		if(confirm("您确定删除该商品么?")){ 	
			<%-- var url="<%=request.getContextPath()%>/jdGoods/deleteProduct.action?id="+id;
			window.location.href=url; --%>
			$("#recordId").val(id);
			 var url="<%=request.getContextPath()%>/jdGoods/deleteProduct.action";
			 $("#productBlackForm").attr('action',url);
			 $("#productBlackForm").submit();
		}
	}
}

function batchDeleteProduct(){
	if(isSelected()){
		 var isConfirm= confirm("您确定要执行此操作么?");
		   if(isConfirm){
			   var url="<%=request.getContextPath()%>/jdGoods/deleteProduct.action";
			   $("#productBlackForm").attr('action',url);
			   $("#productBlackForm").submit();
		   }
	}else{
		 alert("请选中操作项");
	}
	
}

function isSelected(){
	  var isSelected=false;
	   //为批量删除,需要判断复选框是否有被选中了
		   var names = document.getElementsByName("ids");
			var len = names.length;
			if (len) {
				var i = 0;
				for (i = 0; i < len; i++) {
					//复选框有被选中
					if (names[i].checked) {
						isSelected=true;
						break;
					}
				}
			}else{
				//一个的情况
				isSelected=names.checked
			}
			return  isSelected;
}
/**
 * 批量导入商品按钮
 */
$("#batchImportProduct").click(function(){
	var url="<%=request.getContextPath()%>/jdGoods/exportExcelBlackUI.action";
	window.location.href=url;
});

function exportBlackProduct(){
	if(confirm("您确定要导出数据么?")){
		 var url="<%=request.getContextPath()%>/jdGoods/exportBlackProduct.action";
		 $("#productBlackForm").attr('action',url);
		 $("#productBlackForm").submit();	
	}	
}


</script>
</body>
