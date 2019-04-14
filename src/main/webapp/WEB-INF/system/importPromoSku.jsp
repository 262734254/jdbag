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
								<dd>导入商品</dd>
							</dl>
						</div>
	
		<input type="hidden" name="currentPage" value="${currentPage }"/>
		<input type="hidden" name="pageSize" value="${pageSize}"/>
		<%-- <input type="hidden" id="stockOrder" name="stockOrder" value="<s:property value="stockOrder"/>"/>
	    <input type="hidden" id="jdstockNumOrder" name="jdstockNumOrder" value="<s:property value='jdstockNumOrder'/>"/>
	    <input type="hidden" name="jdwardIds" id="jdwardIds" value=""/> --%>
		
		<input id="flag" type="hidden" name="flag" value="flag"/>
	   <form id="improrPromoSkuForm" method="post" enctype="multipart/form-data" action="<%=request.getContextPath() %>/jdGoods/importPromoSku.action">
		<table class="mytable">		
			<tr>
				<%-- <td  style="width: 250px" >导入类型：&nbsp;
					<select>
					    <option value="">请选择</option>
					    <option value="1">导入增加</option>
					    <option value="2">导入删除</option>
					</select>
					<a>模板下载</a>
					
				</td>	 --%>			
			</tr>
			<tr>
			  <input type="hidden" name="promoId"  value="<s:property value="promoId" />"/>
				<td  colspan='4' style="width: 1000px" height='60px' >
					请选择文件名
					 <input type="file" name="uploadFile" id="uploadFile" size="40" value="浏览"/>
					 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a>模板下载</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		             <input type="button" id="uploadButton" value="提交" onclick="importPromoSku()"/>
				</td>
			</tr>
			
		</table>
	   </form>
		<hr />
	    <form id="queryJdPromoSkuForm">
		<table class="listtable" id="idTable">

			<thead>
			  <%-- 
			    <tr>
			   <td  colspan='9' height='100px'>
			        <p>
			        
				                       导入类型：&nbsp;
						<select>
						    <option value="">请选择</option>
						    <option value="1">导入增加</option>
						    <option value="2">导入删除</option>
						</select>
			        </p>
			        <br />
			        <p>
			                        文件名称：&nbsp;
					<s:textfield theme="simple" name="promoForm.name" />
					&nbsp;&nbsp;&nbsp;
					创建时间
					<s:textfield id="startTime" name="promoForm.beginTime" theme="simple" cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield> 
					
					至
					
					<s:textfield id="endTime" name="promoForm.endTime" theme="simple"  cssClass="Wdate" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly="true"></s:textfield>
					<input id="queryJdPromoSkuRec" type="button" onclick="getJdPromoSkuRec()" value="查询"/>
					</p>
				</td>
				
			   </tr> --%>
				<tr>
				  
					<th width="5%">文件名称</th>
					
					<th width="8%">导入时间</th>
					<th width="10%">导入商品数量</th>
					<th width="8%">成功数量</th>

					<th width="5%">失败数量</th>
					
					<th width="4%" id="idSize" >
					 操作
					</th>
					
				</tr>
			</thead>
			
			<tbody>
				<s:iterator value="pageView.records" var="resultObject" >
					<tr class="myclass">
						<td class="tlr"><s:property value="#resultObject.fileName" /></td>
						
						<td class="tlr"><s:property value="#resultObject.createTime" /></td>
						<td class="tlr">
						     <p><s:property value="#resultObject.importCount" /></p>
						   
						</td>
						<td class="tlr">
						  <p><s:property value="#resultObject.successCount" /></p>
						
						</td>
						<td class="tlr">
						  <p><s:property value="#resultObject.failCount" /></p>
						
						</td>
						
						<td class="tlr" style="text-align:center;"><a class="modify">下载商品</a></td>
					</tr>
				</s:iterator>
			</tbody>
			<tbody>
			<%@ include file="/admin/common/page.jsp"%>
			</tbody>
		</table>
		</form>
		
		
       
	   

</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">

var importPromoSku = function(){
	
	var action = "<%=request.getContextPath() %>/jdGoods/importPromoSku.action";
	$("#improrPromoSkuForm").attr("action",action);
	$("#improrPromoSkuForm").submit();
}

var getJdPromoSkuRec = function(){
	var action = "<%=request.getContextPath() %>/jdGoods/getJdPromoSkuRecList.action";
	$("#queryJdPromoSkuForm").attr("action",action);
	$("#queryJdPromoSkuForm").submit();
}
</script>
</body>
</html>