<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="<%=request.getContextPath()%>/admin/static/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/admin/static/css/page_admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/admin/static/js/jquery-huadong.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$(".list").hide();

	$("h3.trigger").toggle(function(){
		$(this).addClass("sole"); 
		}, function () {
		$(this).removeClass("sole");
	});
	
	$("h3.trigger").click(function(){
		$(this).next(".list").slideToggle("slow,");
	});

});
</script>

</head>
<body>
<div class="adminLeft">
<h3 class="trigger">运营中心</h3>
<ul class="list">

   
<s:iterator value="#session.labelList">
		<li><a href="<%=request.getContextPath()%>/<s:property value="pageUrl" />" target="mainFrame"><s:property value="pageName" /></a></li>
</s:iterator>  
<%-- 
   <li><a href="<%=request.getContextPath()%>/jdGoods/exportExcelUI.action" target="mainFrame"><s:property value="pageName" />导入商品走秀码</a></li>

<li><a href="<%=request.getContextPath()%>/jdGoods/wareInfoList.action" target="mainFrame"><s:property value="pageName" />商品信息</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/logListRecord.action" target="mainFrame"><s:property value="pageName" />日志列表</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/wareTem.action" target="mainFrame"><s:property value="pageName" />商品模板推送</a></li>

  <li><a href="<%=request.getContextPath()%>/jdGoods/exportExcelBlackUI.action" target="mainFrame"><s:property value="pageName" />导入商品黑名单</a></li>
   <li><a href="<%=request.getContextPath()%>/jdGoods/blackProductListUI.action" target="mainFrame"><s:property value="pageName" />上下架不对接管理</a></li>


<li><a href="<%=request.getContextPath()%>/jdGoods/deleWareUI.action" target="mainFrame"><s:property value="pageName" />删除商品和商品SKU界面</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/skuInfoList.action" target="mainFrame"><s:property value="pageName" />商品SKU信息</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/deleProductUI.action" target="mainFrame"><s:property value="pageName" />批量删除商品界面</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/updateAdContentUI.action" target="mainFrame"><s:property value="pageName" />批量更新广告词界面</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/updateProductTitleUI.action" target="mainFrame"><s:property value="pageName" />商品标题修改界面</a></li>
<li><a href="<%=request.getContextPath()%>/order!searchOrder.action?orderState=WAIT_SELLER_STOCK_OUT" target="mainFrame"><s:property value="pageName" />订单管理</a></li>
<li><a href="<%=request.getContextPath()%>/order/orderInfoList.action?jdOrderTrackForm.placeResult=3" target="mainFrame"><s:property value="pageName" />扣减库存失败订单列表</a></li>
<li><a href="<%=request.getContextPath()%>/cate!queryJDToLocalRef.action" target="mainFrame"><s:property value="pageName" />类目映射设置</a></li>
<li><a href="<%=request.getContextPath()%>/cate!queryXiuJdCategory.action" target="mainFrame"><s:property value="pageName" />类目映射查询</a></li>
<li><a href="<%=request.getContextPath()%>/cate!searchCategorys.action" target="mainFrame"><s:property value="pageName" />属性值映射设置</a></li>
<li><a href="<%=request.getContextPath()%>/cate!queryXiuJdAttValue.action" target="mainFrame"><s:property value="pageName" />属性值映射查询</a></li>
<li><a href="<%=request.getContextPath()%>/cate!toRefxiuJdBrand.action" target="mainFrame"><s:property value="pageName" />品牌映射设置</a></li>
<li><a href="<%=request.getContextPath()%>/cate!queryXiuJdBrand.action" target="mainFrame"><s:property value="pageName" />品牌映射查询</a></li>
<li><a href="<%=request.getContextPath()%>/cate!forward.action?path=sycn_c_a_v" target="mainFrame"><s:property value="pageName" />同步类目/属性/属性值</a></li>

<li><a href="<%=request.getContextPath()%>/jdGoods/getDataUI.action" target="mainFrame"><s:property value="pageName" />SQL查询界面</a></li>

<li><a href="<%=request.getContextPath()%>/jdGoods/pullAttrAndValueUI.action" target="mainFrame"><s:property value="pageName" />同步京东属性与属性值到本地数据库</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/importSkuInfoUI.action" target="mainFrame"><s:property value="pageName" />导入销售属性</a></li> --%>
<li><a href="<%=request.getContextPath()%>/jdGoods/promoInfoList.action" target="mainFrame"><s:property value="pageName" />满减促销</a></li>
<li><a href="<%=request.getContextPath()%>/jdGoods/wareInfoBatchList.action" target="mainFrame"><s:property value="pageName" />批量推送商品信息</a></li>
<li><a href="<%=request.getContextPath()%>/jdProduct/updateOrderEmailUI.action" target="mainFrame"><s:property value="pageName" />通知邮箱配置</a></li>

</ul>
<!--adminLeft-->
</div>
</body>
</html>
