
$(document).ready(function () {
	
	/* 超类编码 */
	$.post("goods/ajaxgetGoodsTypeSuperCode.action", function (data) {

		
		var superCodetemp=$("#superCodetemp").val();
		var jsonObj = eval("(" + data + ")");
		for (var i = 0; i < jsonObj.length; i++) {
			var $option = $("<option></option>");			
			$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
			$option.text(jsonObj[i].goodsTypeName);
			if(superCodetemp==(jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName)){
				$option.attr("selected", true); 
			}
			$("#superCode").append($option);	
			
		}
	
	//	alert($("#superCode").html());
		//alert($("#superCode").htm());
	});
	
	/* 大类编码*/
	$("#superCode").change(function () {
		$.post("goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#superCode").val(),level:1}, function (data) {
			/* 大类编码 */
			$("#familyCode option[value!='']").remove();
			/* 中类编码 */
			$("#classCode option[value!='']").remove();
			/* 小类编码 */
			$("#childCode option[value!='']").remove();
			var familyCodetemp=$("#familyCodetemp").val();
			
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.text(jsonObj[i].goodsTypeName);
				if(familyCodetemp==(jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName)){
					$option.attr("selected", true); 
				}
				$("#familyCode").append($option);
			}
		});
	});
	
	/* 大类编码*/
	$("#familyCode").change(function () {
		$.post("goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#familyCode").val(),level:2}, function (data) {
			/* 中类编码 */
			$("#classCode option[value!='']").remove();
			/* 小类编码 */
			$("#childCode option[value!='']").remove();
			var classCodetemp=$("#classCodetemp").val();
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.text(jsonObj[i].goodsTypeName);
				if(classCodetemp==(jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName)){
					$option.attr("selected", true); 
				}
				$("#classCode").append($option);
			}
		});
	});
	/* 中类编码*/
	$("#classCode").change(function () {
		$.post("goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#classCode").val(),level:3}, function (data) {
		
			/* 小类编码 */
			$("#childCode option[value!='']").remove();
			var childCodetemp=$("#childCodetemp").val();
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.text(jsonObj[i].goodsTypeName);
				if(childCodetemp==(jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName)){
					$option.attr("selected", true); 
				}
				$("#childCode").append($option);
			}
		});
	});
	
	/* 品牌 */
	

		$.post("goods/ajaxGetSupplierBrandCode.action",{supplierId:$("#supplierId").val()},function (data) {
		//	$("#brandCode").html("<option value=''>请选择</option>");
			var brandCode=$("#brandCodetemp").val();
			var jsonObj = eval('' + data + '');
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");	
				$option.attr("value", jsonObj[i].brandCode);
				if(jsonObj[i].brandNameZHS!=null&&jsonObj[i].brandNameZHS!=""){
					$option.text(jsonObj[i].brandNameZHS);	
				}else{
					if(jsonObj[i].brandNameUS!=null&&jsonObj[i].brandNameUS!=""){
						$option.text(jsonObj[i].brandNameUS);	
					}else{
						continue;
					}
				}				
				if(brandCode==jsonObj[i].brandCode){
					$option.attr("selected", true); 
				}
				$("#brandCode").append($option);	
				
			}
		});

	
	
	
});
