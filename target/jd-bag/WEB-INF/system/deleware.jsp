<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>删除商品和商品SKU信息界面 (不包含京东上的商品)</title>
<link href="<%=request.getContextPath()%>/template/css/wareinfo.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link href="http://ware.shop.jd.com/common/css/popcombox.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/template/javascript/calendar/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/goodsList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/xheditor-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/xheditor_lang/zh-cn.js"></script>
</head>
<body>

	<form id="deleteWareInfoForm" method="post" action="<%=request.getContextPath() %>/jdGoods/deleWareUI.action">
	 <font style="color:red;"> 只删除本地数据库中的记录(不包含京东上的商品)</font> 
	        </br> 请输入商品走秀码,每个走秀码独占一行:
	   <font style="color:red">最多支持200个</font>
	  <textarea rows="10" cols="50" name="xiuCodes"></textarea>
       <input id="deleteWareInfo" type="button" value="删除"/> 
       <br/>
       <br/>
       <br/>
	商品ID&nbsp;<input id="jdWareId"  name="jdWareId" type="text" value=""/>&nbsp;<input id="addPic" type="button" value="删除图片原有图片并上传新图片"/>
	</form>
	
</div> 
</td> 
<td class="td03"></td> 
</tr> 
</tbody> 
</table>
<script type="text/javascript">
//删除商品和商品SKU信息 <input id="deleteWareInfo" type="button" value="查询"/>
$("#deleteWareInfo").click(function(){
	if(confirm("您确定要删除商品么?")){
		var action = "<%=request.getContextPath() %>/jdGoods/deleWare.action";
		$('#deleteWareInfoForm').attr("action",action);
		$('#deleteWareInfoForm').submit();
	}
	
});

$("#addPic").click(function(){
	var action="<%=request.getContextPath() %>/jdGoods/deleAndAddPic.action";
	$('#deleteWareInfoForm').attr("action",action);
	$('#deleteWareInfoForm').submit();
});
	
</script>
</body>

</html>