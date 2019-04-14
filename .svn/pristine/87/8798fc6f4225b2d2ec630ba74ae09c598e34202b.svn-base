String.prototype.Trim = function()    
{    
	return this.replace(/(^\s*)|(\s*$)/g, "");    
}

function goodsSubmit(goodsID){
	
	$.post("../goods/ajaxGetGoodsSubmit.action", {goodsId:goodsID}, function (data) {

		var jsonObj = eval(''+data+'');
		
		if(jsonObj=="1"){
			var temp=$("#goodsName"+goodsID).text();
		//	$("#showdiv").html("此商品“"+temp+"”提交待审核成功");			
		
			
			myShowDiv("此商品  ["+temp+"] 提交待审核成功");			
			// alert("此商品“"+temp+"”提交待审核成功");
			$("#goodsStatus"+goodsID).text("待审核");
			var temp="<span class='disabledlink'>库存</span>&nbsp;<br/><span class='disabledlink'>价格</span>&nbsp;<br/><span class='disabledlink'>编辑</span>&nbsp;<br><span class='disabledlink'>提交</span>";
			
			$("#hidden"+goodsID).html(temp);	
			//$("#opertaor"+goodsID).parent().parent().css('background-color', '#0011D4;');
			$("#goodsId"+goodsID).attr("disabled",true);
		}else{
			alert("提交失败");
		}
	});
}


function updatePrice(goodsID,marketPrice,xiuPrice,activity){
	if(marketPrice!=null&&marketPrice!=""){
		$("#marketPrice"+goodsID).html(marketPrice);	
	}
	if(xiuPrice!=null&&xiuPrice!=""){
		$("#xiuPrice"+goodsID).html(xiuPrice);	
	}
	if(activity!=null&&activity!=""){
		$("#activity"+goodsID).html(activity);	
	}
}


function updateinventory(goodsID,inventory){
	if(inventory!=null&&inventory!=""){
		$("#inventory"+goodsID).html(inventory);	
	}	
}

