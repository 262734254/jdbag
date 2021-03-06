<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商品信息页</title>
<link href="<%=request.getContextPath()%>/template/css/wareinfo.css" rel="stylesheet" type="text/css" /> 
<link href="<%=request.getContextPath()%>/template/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<link href="http://ware.shop.jd.com/common/css/popcombox.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/template/javascript/calendar/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/goodsList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/xheditor-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/template/javascript/xheditor_lang/zh-cn.js"></script>
<style type="text/css">
.store-type {
    text-align: left;
    width: 100%;
}
</style>
</head>
<body  id="mainframe">
	<div class="shop_main">
	<div class="shop_curr">
	<h3 class="h_h3 h_v5">
	修改商品
	</h3>
	<div class="sc_section">
		<p id="category" class="curr_info ci_v1">你选择的类目：
		<s:property value="xopCategory.superName"/> > <s:property value="xopCategory.familyName"/> > 
		<s:property value="xopCategory.className"/>
		<input id="xiuCid" type="hidden" value="<s:property value="xopCategory.classCode"/>"/>
		</p>
	<div class="order_tbl">
		<ul class="tbl_tab">
			<li><span>基本内容</span></li>
		</ul>
	</div>
	<div class="shop_ordout so_v1">
	<form id="form1"  method="post" action="<%=request.getContextPath() %>/jdGoods/wareInfoModify.action">
	<s:hidden name="product.attributes" id="attributes" value=""></s:hidden>
	<s:hidden name="xopCategory.childCode" id="xiuCateId" value=""></s:hidden>
	<input type="hidden" name="product.jdWareId" value="<s:property value="product.jdWareId" />" />
	<input type="hidden" name="product.shopCategory" id="shopCategory" value=""/>
	<input type="hidden" id="isShop" value="<s:property value="product.shopCategory"/>"/>
	
		<h3 class="h_h3 h_v1">
			基本内容编辑
			<span class="must">
			（
			<span class="txt">*</span>
			表示必填）
			</span>
		</h3>
		<div class="tbl_box2">
			<font size="3" color="red"> </font>
			<table class="tbl_type2 tt_v1" width="100%" cellspacing="5" cellpadding="0" border="0">
				<colgroup>
					<col style="width:16%">
					<col style="width:84%">
				</colgroup>
				<tbody>
					<tr>
						<td>
							<p class="txt_rgt">
								<span class="txt">*</span>
								商品名称：
							</p>
						</td>
						<td><p class="txt_lft"><input type="text" read="only" style="width:400px;height:18px" name="product.title" value="<s:property value="product.title" />"/></p></td>
														<input type="hidden" name="product.globalFlag" value="<s:property value="product.globalFlag" />"/>
						
					</tr>
					<tr>
						<td>
							<p class="txt_rgt">
								
								商品广告词：
							</p>
						</td>
						<td><p class="txt_lft"><input type="text" id="adContent" style="width:400px;height:18px" name="product.adContent" value="<s:property value="product.adContent" />"/></p></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tbl_box3" style="z-index:999;border-radius:15px">
			<span class="tb3_br1"></span>
			<span class="tb3_br2"></span>
			<span class="tb3_br3"></span>
			<span class="tb3_br4"></span>
			<h4 class="h_h4">商品属性</h4>
			<table class="tbl_type2" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col style="width:14%">
					<col style="width:86%">
				</colgroup>
				<tbody>
					<s:if test='%{jdBrand=="1"}'>
					<tr>
						<td>
							<p class="txt_rgt">
								<span  class="txt">*</span>
								 品牌
							</p>
						</td>
						<td>
							<p class="txt_lft">
								<input style="width:400px;height:18px" id="brand" read="only" type="text" name="product.jdBrandName" value="<s:property value="product.jdBrandName" />" title="<s:property value="product.jdBrandAid" />:<s:property value="product.jdBrandVid" />"/>
							</p>
						</td>
					</tr>
					</s:if>
					<s:iterator value="attrbuteInfoMap" var="tt">
					<tr>
						<td>
							<p class="txt_rgt">
								<s:iterator value="#tt.key" var="aaa">
								<s:if test="key=='true'"><span class="txt">*</span></s:if>
								<s:property value="#aaa.value"/>
								</s:iterator>
							</p>
						</td>
						<td>
							<%-- <p class="txt_lft a">
								<s:iterator value="#tt.value" var="aa">
									<s:if test="key==2">
										<s:iterator value="#aa.value" var="bb">
											<s:iterator value="#bb" var="cur">
												<input class="checkAttr" type="hidden" value="<s:property value="#cur.key"/>"/>
												<label title="mmm" class="chkwn">
													<input class="attr" type="checkbox" value="" />
														<input type="hidden" value="<s:property value="#cur.value.aid"/>"/>
														<input type="hidden" value="<s:property value="#cur.value.vid"/>"/>
														<s:property value="#cur.value.name"/>
												</label>
											</s:iterator>
										</s:iterator>
									</s:if>
									<s:elseif test="key==1">
										<select>
										<option></option>
										<s:iterator value="#aa.value" var="bb">
										<s:iterator value="#bb" var="cur">
											<option label="<s:property value="#cur.key"/>"  title="<s:property value="#cur.value.aid"/>:<s:property value="#cur.value.vid"/>"><s:property value="#cur.value.name"/></option>
										</s:iterator>
										</s:iterator>
										</select>
									</s:elseif>
									<s:elseif test="key==3">
										<input type="text" value="">
									</s:elseif>
								</s:iterator>
							</p> --%>
							
							<!-- 1、单选， 2、多选3、输入 -->
							<!-- 属性输入类型： 1、单选， 2、多选3、输入(最大50个字符）
							 -->
							<p class="txt_lft a">
								<s:iterator value="#tt.value" var="aa">
									<s:if test="key==2">
										<s:iterator value="#aa.value" var="bb">
										<input class="checkAttr" type="hidden" value="<s:property value="#bb.identify"/>"/>
										<label class="chkwn">
											<input class="attr" type="checkbox" value="" />
												<input type="hidden" value="<s:property value="#bb.aid"/>"/>
												<input type="hidden" value="<s:property value="#bb.vid"/>"/>
												<s:property value="#bb.name"/>
										</label>
										</s:iterator>
									</s:if>
									<s:elseif test="key==1">
										<select>
										<option></option>
										<s:iterator value="#aa.value" var="bb">
											<option label="<s:property value="#bb.identify"/>"  title="<s:property value="#bb.aid"/>:<s:property value="#bb.vid"/>"><s:property value="#bb.name"/></option>
										</s:iterator>
										</select>
									</s:elseif>
									<s:elseif test="key==3">
										<input type="text" value="">
									</s:elseif>
								</s:iterator>
							</p>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
		</div> 	
		 <div class="tbl_box3" style="z-index:998;border-radius:15px">
			<span class="tb3_br1"></span>
			<span class="tb3_br2"></span>
			<span class="tb3_br3"></span>
			<span class="tb3_br4"></span>
			<h4 class="h_h4">商品信息</h4>
			<table class="tbl_type2" width="100%" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col style="width:14%">
					<col style="width:18%">
					<col style="width:14%">
					<col style="width:18%">
					<col style="width:12%">
					<col style="width:18%">
				</colgroup>
				<tbody>
					<tr style="height: 22px">
						<td>
							<p class="txt_rgt"><span class="txt">*</span>京东分类:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text" read="only" name="product.cid" value="<s:property value="product.cid" />"/></p>
						</td>
						<td>
							<p class="txt_rgt"><span class="txt">*</span>商品走秀码:</p>
						</td>
						<td>
							<p class="txt_lft"><input type="text" read="only" name="product.xiucode" value="<s:property value="product.xiucode" />"/></p>
						</td>
						<td>
							<p class="txt_rgt"><span class="txt">*</span>进货价:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text" read="only" name="product.costprice" value="<s:property value="product.costprice" />"/>元</p>
						</td>
					</tr>
					<tr  style="height: 22px">
						<td>
							<p class="txt_rgt"><span class="txt">*</span>京东价:</p>
						</td>
						<td>
							<p class="txt_lft"><input id="jdPrice"  type="text" read="only" name="product.jdprice" value="<s:property value="product.jdprice" />"/>元</p>
						</td>
						<td>
							<p class="txt_rgt"><span class="txt">*</span>市场价:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text"  name="product.marketprice" value="<s:property value="product.marketprice" />"/>元</p>
						</td>
						<td>
							<p class="txt_rgt"><span class="txt">*</span>商品重量:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text" read="only" name="product.weight" value="<s:property value="product.weight" />"/><span style="font-size:14px;">kg</span></p>
						</td>
					</tr>
					<tr>
						<td>
							<p class="txt_rgt"><span class="txt">*</span>商品高度:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text" read="only" name="product.high" value="<s:property value="product.high" />"/><span style="font-size:14px;">mm</span></p>
						</td>
						<td>
							<p class="txt_rgt"><span class="txt">*</span>商品长度:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text" read="only" name="product.lenght" value="<s:property value="product.lenght" />"/><span style="font-size:14px;">mm</span></p>
						</td>
						<td>
							<p class="txt_rgt"><span class="txt">*</span>商品宽度:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text" read="only" name="product.wide" value="<s:property value="product.wide" />"/><span style="font-size:14px;">mm</span></p>
						</td>
						
						
					</tr>
					<tr>
					<td>
							<p class="txt_rgt">货号:</p>
						</td>
						<td>
							<p class="txt_lft"><input  type="text" name="product.itemNum" value="${param.xiucode }"/></p>
						</td>
					</tr>
					<tr>
						<td>
							<p class="txt_rgt">商品主图:</p>
						</td>
						<td colspan="5">
							<p class="txt_lft"> </p>
							<ul class="img-list" style="float:left;">
								<li>
								<input type="hidden" name="product.mainimagepath" value="<s:property value="product.mainimagepath" />"/>
								<img width="140" height="120" src="<s:property value="product.mainimagepath" />">
								</li>
							</ul>
							<p></p>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tbl_box3" style="z-index:997;border-radius:15px">
			<span class="tb3_br1"></span>
			<span class="tb3_br2"></span>
			<span class="tb3_br3"></span>
			<span class="tb3_br4"></span>
			<h4 class="h_h4">销售属性</h4>
			<table class="tbl_type2" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col style="width:100px">
					<col style="">
				</colgroup>
				<tbody>
					<tr>
					<td valign="top" colspan="2">
						<div class="tb-x2">
							<table id="skuAreaTable" cellspacing="0" cellpadding="0" border="0">
								<thead id="product_node_title">
									<tr>
										<th style="display: none"></th>
										<th width="24%">商家skuid</th>
										<th id="sale-1000012755">颜色</th>
										<th id="sale-1000012756">尺码</th>
										<th id="jdskus" width="20%">
										<span style="color:red;">*</span>
										库存
										<input name="product.stocknum" class="batStockNo" type="text" value="<s:property value="product.stocknum" />" size="8">
										</th>
										<th id="jdskup" width="24%">
									</tr>
								</thead>
								<tbody id="product_node">
								
									<s:iterator value="skuInfoList" var="skuinfo"  status='st'>
									
									<tr class="tmpskutr" skuname="1000012755:1502146386">
										<td style="display: none">
											<input type="hidden" name="skuinfo.attributes"  value="<s:property value="#skuinfo.attributes" />"/>
										</td>
										<td>
											<input type="text" name="skuinfo.skusn" id="skusn<s:property value='#st.index' />" value="<s:property value="#skuinfo.skusn" />"/>
										</td>
										<td>
											<input type="text" name="skuinfo.colorname" id="colorname<s:property value='#st.index' />" value="<s:property value="#skuinfo.colorname" />"/>
										</td>
										<td>
											<input type="text" name="skuinfo.sizevalue" value="<s:property value="#skuinfo.sizevalue" />"/>
										</td>
										<td>
											<input type="text" name="skuinfo.stocknum" value="<s:property value="#skuinfo.stocknum" />"/>
										</td>
									</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="tbl_box3 tb3_v1">
			<table class="tbl_type2" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
					<col style="width:8%">
					<col style="width:92%">
				</colgroup>
				<tbody>
					<tr>
						<td valign="top">
							<p class="txt_rgt">
								<span class="txt">*</span>
							商品描述：
							</p>
						</td>
						<td style="text-align: left"><textarea id="elm1" class="xheditor" rows="12" cols="120" style="width: 80%" name="product.notes"><s:property value='product.notes' /></textarea>
							<input id="xhe0_fixffcursor" type="text" style="position:absolute;display:none;">
						</td>
						<script type="text/javascript">
						$('#elm1').xheditor({tools:'full',skin:'default',showBlocktag:true,internalScript:false,internalStyle:false,width:1000,height:200,fullscreen:false,sourceMode:false,forcePtag:true,upImgUrl:"ubb2html.php",upImgExt:"jpg,jpeg,gif,png"});
						</script>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="tbl_box3">
			<span class="tb3_br1"></span>
			<span class="tb3_br2"></span>
			<span class="tb3_br3"></span>
			<span class="tb3_br4"></span>
			<h4 class="h_h4">店内分类</h4>
			<table class="tbl_type2" border="0" cellspacing="0" cellpadding="0">
				<tbody>
					<tr>
						<td>
							<dl class="store-type" style="float:left;">
								<s:iterator value="jdSellerCategoryMap" var="se">
									<label><s:property value="#se.key.name"/></label>
									<s:iterator value="#se.value" var="secat">
										<input class="sellerCat" type="checkbox" style="margin-left:15px" value="<s:property value="#secat.parentId"/>-<s:property value="#secat.cid"/>"/>
										<label><s:property value="#secat.name"/></label>
									</s:iterator>
									<br>
								</s:iterator>
							</dl>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div><input id="baocun" type="button" value="保存"  style="text-align: center;"/></div>
	</form>
	</div>
	</div>
	</div>
	</div>
<script type="text/javascript">

 $(document).ready(function(){
	 var adContent=$("#adContent").val();
	 if(adContent==""){
		 $("#adContent").val("海外大牌精选每日上新 品质保证 无忧售后") 
	 }
	$("input[type='text'][read='only']").each(function(e){
		var value = $(this).val();
		var wareName = $(this).parent().parent().prev().children().text();
		if(!wareName.contains("商品名称") && $.trim(value) != ""){
			$(this).attr("readonly","readonly");
		}
	});
	
	$(".sellerCat").each(function(i){
		var shopAttr = $("#isShop").val();
		if(shopAttr != "" && shopAttr.indexOf($(this).val())>=0){
			$(this).attr("checked",true);
		}
	});
	
	var jdPrice = $("#jdPrice").val();

	$(".txt_rgt").each(function(e){
		var attrName = $(this).text();
		if(attrName.indexOf('价格')>=0){
			var attrValues = $(this).parent().next().children().children().children();
			var count = attrValues.length;
			for(var i=1;i<count;i++){
				var attrValue = attrValues.parent().get(0).options[i].value;
				//attrValue=attrValue.replace('元','');
				var reg = /[\u4E00-\u9FA5]/g;
				if(reg.test(attrValue)){
					var intValues = attrValue.replace(reg,'').replace("－","-").trim();
					//alert("intValues-->"+intValues);
					if(intValues.indexOf('-')>0){
						var numValue =intValues.split('-');
						var minValue =parseInt(numValue[0].trim());
						var maxValue = parseInt(numValue[1].trim());
						//alert("minValue="+minValue+",maxValue="+maxValue+ ",jdPrice="+jdPrice);
						if(minValue<jdPrice && jdPrice<maxValue){
							attrValues.parent().get(0).options[i].selected = true;
							attrValues.parent().get(0).disabled = "disabled";
							break;
						}
						//700元以上，700以下
					}else if(intValues <= parseInt(jdPrice)){
						//alert("intValues==>"+intValues);
						//attrValues.parent().get(0).options[i].value;
						attrValues.parent().get(0).options[i].selected = true;
						attrValues.parent().get(0).disabled = "disabled";
						break;
					}
				
					
					/* if(minValue<jdPrice){
						alert("minValue  "+minValue+",jdPrice="+jdPrice);
						attrValues.parent().get(0).options[i].selected = true;
						attrValues.parent().get(0).disabled = "disabled";
						break;
					} */
						
				}else{					
			
					var ss =attrValue.replace("－","-").split('-');
					var minValue =parseInt(ss[0].trim());
					var maxValue = parseInt(ss[1].trim());
					if(minValue<jdPrice && jdPrice<maxValue){
						attrValues.parent().get(0).options[i].selected = true;
						attrValues.parent().get(0).disabled = "disabled";
						break;
					}
				}
				
			}
		};
	});
	

	var otherOption="其它,其他";
	$(".a").each(function(e){
		var options = $(this).find("option");
		var counts = options.length;
		for(var i=1;i<counts;i++){
			var option=options.parent().get(0).options[i];
			var attrValue = option.label;
			if(attrValue!=""){
				option.selected = true;
				//options.parent().get(0).disabled = "disabled";
			}else{
				var optionText= $(option).text();
					if(optionText && $.trim(optionText)!='' && !option.disabled){
						if(otherOption.indexOf(optionText)>=0){
						    option.selected = true;
							break;
						}
					} 
			}
		}
	});
	
	$(".checkAttr").each(function(){
		var checkVal = $(this).val();
		if(checkVal!=""){
			$(this).next().children().get(0).checked=true;
			//$(this).next().children().get(0).disabled = "disabled";
		}
	});
	
	$("#baocun").click(function(){
		var bitian = $("span.txt");
		var flag = true;
		bitian.each(function(i){
			if(i>1 && i<$(bitian).length-1){
				var tt = $(this).parent().parent().next().children().find("select");
				if($(tt).val()==""){
					alert("带红色星号的是必填的，请填写！！！");
					$(tt).focus();
					flag = false;
					return false;
				}
				
				var ttt = $(this).parent().parent().next().find("label");
				var isCheck = false;
				if(ttt.length>0){
					for(var i =0;i<ttt.length;i++){
						var mm = $(ttt[i]).children().get(0).checked;
						if(mm){
							isCheck = mm;
						}
					}
					if(!isCheck){
						alert("带红色星号的是必填的，请填写！！！！");
						flag = false;
						return false;
					}
				}
			}else if(i==1){
				var wareName = $(this).parent().parent().next().children().children();
				if($(wareName).val()==""){
					alert("带红色星号的是必填的，请填写！！！");
					$(wareName).focus();
					flag = false;
					return false;
				}
			}else if(i==$(bitian).length-1){
				var wareDesc = $(this).parent().parent().next().children();
				if($(wareDesc).html() == ""){
					alert("带红色星号的是必填的，请填写！！！");
					$(wareDesc).focus();
					flag = false;
					return false;
				}
			}
		});
		if(flag){
			var attribute = "";
			$("[class='attr'][type='checkbox']").each(function(i){
				if($(this).is(':checked')){
					var attrNameId = $(this).next().val();
					var attrValueId = $(this).next().next().val();
					attribute = attribute+attrNameId+":"+attrValueId+"|";
				}
			});
			
			$(".tbl_box3:first select").each(function(i){
				var attrva = $(this).val();
				var optionValues = $(this).children();
				var optionCount = optionValues.length;
				for(var i=1;i<optionCount;i++){
					var attrValue = optionValues.parent().get(0).options[i].value;
					if(attrva==attrValue){
						var attrTitle = optionValues.parent().get(0).options[i].title;
						attribute = attribute+attrTitle+"|";
						break;
					}
				}
			});
			
			var sellerCat ="";
			$(".sellerCat").each(function(i){
				var isChecked = $(this).attr("checked");
				if(isChecked){
					sellerCat = sellerCat+$(this).val()+";";
				}
			});
			if($.trim(sellerCat)!=""){
				sellerCat = sellerCat.substring(0, sellerCat.length-1);
			}
			var wareWellerCat = $("#shopCategory").val(sellerCat);
			
			var brandAtt = $("#brand").attr("title");
			if(brandAtt!=undefined){
				attribute = attribute+brandAtt;
			}else{
				attribute = attribute.substring(0, attribute.length-1);
			}
			var attributes=$("#attributes").val(attribute);
			var xiuCid = $("#xiuCid").val();
			var xiuCodeId = $("#xiuCateId").val(xiuCid);
			var itemnum=$("#itemnum").val();
			var action = "<%=request.getContextPath() %>/jdGoods/warePush.action";
			$("#form1").attr("action",action);
			$("#form1").submit();
			
		}
	});
});

</script>
</body>
</html>