<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>京东-走秀类目映射</title>
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<link href="${path}/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="${path}/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<link href="${path}/resources/style/order_list.css" rel="stylesheet" type="text/css" />
<script src="${path}/template/javascript/jquery-1.9.1.min.js"></script>
<script src="${path}/template/javascript/bgiframe.js"></script>
<script src="${path}/template/uiplugin/weebox/src/bgiframe.js"></script>
<script>
$(document).ready(function(){
	window.name="xiu_jd_c";
	<c:if test="${oper_rs eq 'succ'}">
		alert("修改成功!");
		window.close();
		window.returnValue="succ";
	</c:if>
	<c:if test="${oper_rs eq 'lost'}">
		alert("修改失败!类目已被映射");
	</c:if>
});
function tocls(t){
	var obj = window.showModalDialog('${path}/cate!chooseCategory.action?type='+t+'&cur_time='+new Date(),'','dialogWidth:800px;dialogHeight:400px;status:0;');
	try{
		if(obj.id=="" || obj.id=="undefined" || obj.id==null){
			return;
		}
	}catch(e){
		return;
	}
	
	
	if("jd"==t){
		$("#jdCid").val(obj.id);
		$("#jdName").val(obj.name);
		$("#jdFullName").val(obj.jdFullName);
	}
	if("xiu"==t){
		$("#xiuCid").val(obj.id);
		$("#xiuName").val(obj.name);
	}
}
</script>
</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">京东-走秀类目映射</h3></td>
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
									
									<dd>修改京东-走秀类目映射 </dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
<div class="adminContent" >		
<font style="font-size: 15px;font-weight: bolder;">修改京东-走秀类目映射 ：</font>
<hr/>
<form target="xiu_jd_c" action="${path}/cate!editXiuJdCategory.action" method="post">
<table style="margin-top: 0px;margin-left: 50px;" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td style="text-align:right;"><p>&nbsp;</p>京东类目ID：</td>
		<td style="text-align: left;"><p>&nbsp;</p>
			<input onclick="tocls('jd');" style="cursor:hand;" readonly="readonly" id="jdCid" name="xiuJdCategory.jdCid" value="<s:property value="xiuJdCategory.jdCid"/>"/>
		</td>
		<td style="text-align:right;"><p>&nbsp;</p>&nbsp;&nbsp;京东类目名称：</td>
		<td style="text-align: left;"><p>&nbsp;</p>
			<input style="border: 0px;" readonly="readonly" id="jdName" name="xiuJdCategory.jdName" value="<s:property value="xiuJdCategory.jdName"/>"/>
		</td>
	</tr>
		<tr>
		<td style="text-align:right;">
		<p>&nbsp;</p>走秀类目ID：</td>
		<td style="text-align: left;">
		<p>&nbsp;</p>
			<input onclick="tocls('xiu');" style="cursor:hand;" readonly="readonly" id="xiuCid" name="xiuJdCategory.xiuCid" value="<s:property value="xiuJdCategory.xiuCid"/>"/>
		</td>
		<td style="text-align:right;">
		<p>&nbsp;</p>&nbsp;&nbsp;走秀类目名称：
		</td>
		<td style="text-align: left;">
			<p>&nbsp;</p>
			<input  style="border: 0px;" readonly="readonly" id="xiuName" name="xiuJdCategory.xiuName" value="<s:property value="xiuJdCategory.xiuName"/>"/>
		</td>
	</tr>
	<tr>
		<td style="text-align:center;" colspan="4">
			<p>&nbsp;</p>
			<input type="hidden" name="xiuJdCategory.id" value="<s:property value="xiuJdCategory.id"/>"/>
			<input type="hidden" id="jdFullName" name="xiuJdCategory.jdFullName" value="<s:property value="xiuJdCategory.jdFullName"/>"/>
			<input style="" type="submit" value=" 确 定 "/>
		</td>
	</tr>
</table>
</form>
<p>&nbsp;</p>
	<!--主体内容结束-->
</div>	
						
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
