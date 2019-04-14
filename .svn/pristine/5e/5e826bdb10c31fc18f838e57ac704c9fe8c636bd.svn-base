
$(function() {
	
	/* 超类编码 */
	$.post("/goods/ajaxgetGoodsTypeSuperCode.action", function (data) {
		
		var jsonObj = eval("(" + data + ")");
		for (var i = 0; i < jsonObj.length; i++) {
			var $option = $("<option></option>");			
			//$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
			$option.attr("value", jsonObj[i].goodsTypeCode);
			$option.text(jsonObj[i].goodsTypeName);
			$("#superCodeChooser").append($option);			
		}

		if ($('#paramsSuperCode').val() != '') {
			$('#superCodeChooser').val($('#paramsSuperCode').val());
			
			if ($('#paramsFamilyCodeName').val() != '') {
				$("#familyCodeChooser").append($("<option></option>").attr('selected', 'selected').text($('#paramsFamilyCodeName').val()));
			}
			
			if ($('#paramsClassCodeName').val() != '') {
				$("#classCodeChooser").append($("<option></option>").attr('selected', 'selected').text($('#paramsClassCodeName').val()));
			}
			
			if ($('#paramsChildCodeName').val() != '') {
				$("#childCodeChooser").append($("<option></option>").attr('selected', 'selected').text($('#paramsChildCodeName').val()));
			}
		}
	});
	
	/* 大类编码*/
	$("#superCodeChooser").change(function () {
		$('#paramsSuperCode').val($(this).val());
		$('#paramsFamilyCode').val('');
		$('#paramsClassCode').val('');
		$('#paramsChildCode').val('');
		$('#paramsSuperCodeName').val($(this).find('option:selected').text());
		$('#paramsFamilyCodeName').val('');
		$('#paramsClassCodeName').val('');
		$('#paramsChildCodeName').val('');
		$.post("/goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#superCodeChooser").val(),level:1}, function (data) {
			/* 大类编码 */
			$("#familyCodeChooser option[value!='']").remove();
			/* 中类编码 */
			$("#classCodeChooser option[value!='']").remove();
			/* 小类编码 */
			$("#childCodeChooser option[value!='']").remove();
		
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				//$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.attr("value", jsonObj[i].goodsTypeCode);
				$option.text(jsonObj[i].goodsTypeName);
				$("#familyCodeChooser").append($option);
			}
			
		});
	});
	
	/* 大类编码*/
	$("#familyCodeChooser").change(function () {
		$('#paramsFamilyCode').val($(this).val());
		$('#paramsClassCode').val('');
		$('#paramsChildCode').val('');

		$('#paramsFamilyCodeName').val($(this).find('option:selected').text());
		$('#paramsClassCodeName').val('');
		$('#paramsChildCodeName').val('');
		$.post("/goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#familyCodeChooser").val(),level:2}, function (data) {
			/* 中类编码 */
			$("#classCodeChooser option[value!='']").remove();
			/* 小类编码 */
			$("#childCodeChooser option[value!='']").remove();
			
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				//$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.attr("value", jsonObj[i].goodsTypeCode);
				$option.text(jsonObj[i].goodsTypeName);
				$("#classCodeChooser").append($option);
			}
		});
	});
	
	/* 中类编码*/
	$("#classCodeChooser").change(function () {
		$('#paramsClassCode').val($(this).val());
		$('#paramsChildCode').val('');

		$('#paramsClassCodeName').val($(this).find('option:selected').text());
		$('#paramsChildCodeName').val('');
		$.post("/goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#classCodeChooser").val(),level:3}, function (data) {
		
			/* 小类编码 */
			$("#childCodeChooser option[value!='']").remove();
			
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				//$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.attr("value", jsonObj[i].goodsTypeCode);
				$option.text(jsonObj[i].goodsTypeName);
				$("#childCodeChooser").append($option);
			}
		});
	});
	
	$("#childCodeChooser").change(function () {
		$('#paramsChildCode').val($(this).val());
		$('#paramsChildCodeName').val($(this).find('option:selected').text());
	});
});

$(function() {
	$('#brandCodeChooser').change(function() {
		$this = $(this);
		$('#paramsBrandName').val($(this).find('option:selected').text());
		$('#paramsBrandCode').val($(this).val());
	});
	
	if ($('#paramsBrandCode').val() != '') {
		$("#brandCodeChooser").append($("<option></option>").attr('selected', 'selected').val($('#paramsBrandCode').val()).text($('#paramsBrandName').val()));
	}
});

function getBrandByName(){
	$.post("/goods/ajaxGetGoodsBrandCode.action", {brandNameInput:$("#brandNameInput").val()}, function (data) {
		$("#brandCodeChooser option[value!='']").remove();
		
		var jsonObj = eval(''+data+'');

		if(jsonObj.length<1){
			$("#brandCodeChooser").html("");
			var $option = $("<option></option>");
			$option.attr("value", '');
			$option.attr("selected", 'selected');
			$option.text('无符合条件的记录');
			$("#brandCodeChooser").append($option);
		}else{
		$("#brandCodeChooser").find("option").text('请选择品牌');
		for (var i = 0; i < jsonObj.length; i++) {
			var $option = $("<option></option>");
			$option.attr("value", jsonObj[i].brandCode);
			if(jsonObj[i].brandNameZHS!=null&&jsonObj[i].brandNameZHS!=""){
				$option.text(jsonObj[i].brandNameZHS);				
			}else if(jsonObj[i].brandNameUS!=null&&jsonObj[i].brandNameUS!=""){
				$option.text(jsonObj[i].brandNameUS);								
			}else{
				continue;
			}
			$("#brandCodeChooser").append($option);
		}}
	});
}