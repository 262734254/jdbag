<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 

"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>同步京东-走秀类目 </title> 
<c:set var="path" value="<%=request.getContextPath()%>"></c:set>
<link href="${path}/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="${path}/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="${path}/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<script src="${path}/template/javascript/jquery-1.9.1.min.js"></script>
<script src="${path}/template/javascript/bgiframe.js"></script>
<script src="${path}/template/uiplugin/weebox/src/bgiframe.js"></script>

</head>
<body> 
<table border="0" cellspacing="0" cellpadding="0" class="adminMain_top"> 
<tbody> 
<tr> 
<td class="td01"></td> 
<td class="td02"><h3 class="topTitle fb f14">同步京东-走秀类目</h3></td> 
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
	<dd>同步京东-走秀类目/属性/属性值</dd>
</dl>
</div>
	<input type="button" onclick="sysn();" id="btn" value="同步京东-走秀类目/属性/属性值"/>
	<input type="hidden" id="flag" name="flag" value=""/>
	<div id="rs" style="display:none;">
		请稍后查看数据
	</div>
</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
</body> 
</html> 
<script>
function sysn(){
	if(window.confirm("Are u sure?")){
		$("#btn").attr("disabled",true);
		var flagValue='${param.flag}';
		$("#flag").val(flagValue);
		$.ajax({type:'post',
			url:'${path}/cate!sycnCategoryInfo.action',
			data:"flag="+$("#flag").val(),
			dataType:'text',
			success:function(msg){
				if(msg=="succ"){
					$("#rs").show();
				}
			}
		});
	}
}
</script>