String.prototype.Trim = function()    
{    
	return this.replace(/(^\s*)|(\s*$)/g, "");    
}

function checkgoodsName(){	
	 var name=$("#goodsName").val();	
	 	name =$.trim(name);	
	 	$("#goodsName").val(name);
		if(name==""){			
			$("#goodsNameSpan").html("<font color='red'>商品名称不能为空</font>");	
		//	$("#goodsName").focus();
			return false;
		}else{			 
			if(name.length>65){
				$("#goodsNameSpan").html("<font color='red'>商品名称不能超过65个字符</font>");	
			//	$("#goodsName").focus();
				return false;
			}else{
				if(name==$("#goodsNameHidden").val()){
					$("#goodsNameSpan").html("");
					$("#goodsNametag").val("success");
					return true;
				}else{				
					$.post("goods/ajaxSearchGoodsName.action", {goodsName:$("#goodsName").val(),supplierId:$("#supplierId").val()}, function (data) {
						var jsonObj = eval(data);
						if(jsonObj==true){							
							$("#goodsNameSpan").html("<font color='red'>此商品名称已经存在！请重新输入商品名称</font>");
							
							$("#goodsNametag").val("fail");
							return false;
						}else{
							$("#goodsNameSpan").html("");
							$("#goodsNametag").val("success");
							return true;
						}
					
					});
					return true;
				}
			}
		
		}
	}

//核对市场价
function checkmarketPrice(){
	 var marketPrice=$("#marketPrice").val();
	 	marketPrice =$.trim(marketPrice);
	 	$("#marketPrice").val(marketPrice);
		if(marketPrice==""){			
			$("#priceSpan").html("<font color='red'>市场价不能为空</font>");	
		//	$("#marketPrice").focus();
			return false;
		}else{	
			if(!/^[0-9]+\d*(.[0-9]{1,2})?$/.test(marketPrice)){
				$("#priceSpan").html("<font color='red'>市场价只能为整数或小数点保存2位的数</font>");	
			//	$("#marketPrice").focus();
				return false;
			}
			 if(eval(marketPrice)<=0){
				 $("#priceSpan").html("<font color='red'>市场价不能为0</font>");		
				// $("#marketPrice").focus();
				 return false;
			 }
			
			if(checkxiuPrice()){
				if(checkactivityPrice()){
					$("#priceSpan").html("");		
				}
			}			
			return true;
		}
}
//核对走秀价
function checkxiuPrice(){
	 var xiuPrice=$("#xiuPrice").val();	
	 	xiuPrice =$.trim(xiuPrice);	
	 	$("#xiuPrice").val(xiuPrice);
		if(xiuPrice==""){	
			$("#priceSpan").html("");
			$("#priceSpan").html("<font color='red'>走秀价不能为空</font>");		
			//$("#xiuPrice").focus();
			return false;
		}else{	
			if(!/^[0-9]+\d*(.[0-9]{1,2})?$/.test(xiuPrice)){
				$("#priceSpan").html("<font color='red'>走秀价只能为整数或小数点保存2位的数</font>");	
			//	$("#xiuPrice").focus();
				return false;
			}
				 var marketPrice=$("#marketPrice").val();	
				 if(eval(xiuPrice)>eval(marketPrice)){
					 $("#priceSpan").html("<font color='red'>走秀价不能大于市场价</font>");
				//	 $("#xiuPrice").focus();
					 return false;
				 }else{
					 if(eval(xiuPrice)<=0){
						 $("#priceSpan").html("<font color='red'>走秀价不能为0</font>");
						// $("#xiuPrice").focus();
						 return false;
					 }else{
						 $("#priceSpan").html("");
						 return true;
					 }
					 
					
				 }
			
		}
}
//核对活动价
function checkactivityPrice(){
	 var activityPrice=$("#activityPrice").val();
	 	activityPrice =$.trim(activityPrice);	
	 	$("#activityPrice").val(activityPrice);
		if(activityPrice==""){	
			$("#priceSpan").html("");
			return true;
		}else{		
			//  /^[1-9]\d*$/
			if(!/^[0-9]+\d*(.[0-9]{1,2})?$/.test(activityPrice)){
				$("#priceSpan").html("<font color='red'>活动价只能为整数或小数点保存2位的数</font>");	
				//$("#activityPrice").focus();
				return false;
			}
			
				 var xiuPrice=$("#xiuPrice").val();		
				 if(eval(activityPrice)>eval(xiuPrice)){
					 $("#priceSpan").html("<font color='red'>活动价不能大于走秀价</font>");
					// $("#activityPrice").focus();
					 return false;
				 }else{
					 if(eval(activityPrice)<=0){
						 $("#priceSpan").html("<font color='red'>活动价不能为0,但可以为空</font>");
						 $("#activityPrice").val("");
					 }else{
						 $("#priceSpan").html(""); 
						 return true;
					 }
						
					
				 }
			
		}
}


function checkbrandCode(){
 		
 	$("#brandCode").val($.trim($("#brandCode").val()));
	if($("#brandCode").val()=="-1"||$("#brandCode").val()==null){
		 $("#brandNameSpan").html("<font color='red'>品牌不能为空！</font>");
		
			return false;
	}else{
		 $("#brandNameSpan").html("");
		return true;
	}
}

 function checkgoodsCode(){	 
		var idValue=$("#goodsCode").val();		
		var goodsCodeValue =$.trim(idValue);	
		$("#goodsCode").val(goodsCodeValue);
		if(goodsCodeValue==""){
			$("#goodsCodeSpan").html("<font color='red'>商品货号不能为空</font>");				
			return false;
		}else{
			var goodsCodeLength=goodsCodeValue.replace(/[^\x00-\xff]/g,"xxx").length; 
			if(goodsCodeLength>30){
				$("#goodsCodeSpan").html("<font color='red'>商品货号不能超过10个汉字或者30个字符</font>");			
				return false;
			}else{
				if(goodsCodeValue==$("#goodsCodeHidden").val()){
					$("#goodsCodeSpan").html("");
					$("#goodsCodetag").val("success");
					return true;
				}else{				
					$.post("goods/ajaxSearchGoodsCode.action", {goodsCode:$("#goodsCode").val(),supplierId:$("#supplierId").val()}, function (data) {
						var jsonObj = eval(data);
						if(jsonObj==true){
							$("#goodsCodeSpan").html("<font color='red'>此商品货号已经存在！请重新输入商品货号</font>");
							$("#goodsCodetag").val("fail");
							return false;
						}else{
							$("#goodsCodetag").val("success");
							$("#goodsCodeSpan").html("");
							return true;
						}
					
					});
				}
					
			}
		}
 }
 

function checkgoodsTyeCode(){
	
	$("#superCode").val($.trim($("#superCode").val()));
	$("#familyCode").val($.trim($("#familyCode").val()));
	$("#classCode").val($.trim($("#classCode").val()));
	$("#childCode").val($.trim($("#childCode").val()));
	
	if($("#superCode").val()=="-1"||$("#superCode").val()==null){
		$("#goodsTypeNameSpan").html("");	
		$("#goodsTypeNameSpan").html("<font color='red'>商品一级分类不能为空！</font>");
		$("#superCode").focus();
		return false;
	} else if($("#familyCode").val()=="-1"||$("#familyCode").val()==null){
		$("#goodsTypeNameSpan").html("");	
		$("#goodsTypeNameSpan").html("<font color='red'>商品二级分类不能为空！</font>");
		$("#familyCode").focus();
		return false;
	} else if($("#classCode").val()=="-1"||$("#classCode").val()==null){
		$("#goodsTypeNameSpan").html("");	
		$("#goodsTypeNameSpan").html("<font color='red'>商品三级分类不能为空</font>");
		$("#classCode").focus();
		return false;
	} else if($("#childCode").val()=="-1"||$("#childCode").val()==null){
		$("#goodsTypeNameSpan").html("");	
		$("#goodsTypeNameSpan").html("<font color='red'>商品四级分类不能为空！</font>");
		$("#childCode").focus();
		return false;
	}else{
		$("#goodsTypeNameSpan").html("");	
		return true;
	}
}

/**失去焦点时对库存进行核对 obj代表当前库存的输入框对象**/ 
function checkinventory(obj){
	var inventory=obj.id;
	var   index   =   inventory.replace(/\D/g,   "");  
	var mycolor=$.trim($("#color"+index).val());
	var mysize=$.trim($("#size"+index).val());
	var myskn=mycolor+mysize;
	//var skunum=$("#skuNum").val();
	var getgoods=$("input[name='goodsSku']");	
	var skunum=getgoods.length;
	
	$("#inventorySpan"+index).html("");
	$("#"+inventory).val($.trim($("#"+inventory).val()));
	if($("#"+inventory).val()==""||$("#"+inventory).val()==null){
		$("#"+inventory).html("");	
		$("#inventorySpan"+index).html("<font color='red'>&nbsp;库存不能为空！</font>");
		$("#skutag").val("false");
	//	$("#"+inventory).focus();
		return false;
	}else{

		if(!/^[0-9]\d*$/.test($("#"+inventory).val())){
			$("#inventorySpan"+index).html("<font color='red'> 库存为整数</font>");	
			$("#skutag").val("false");
			return false;
		}
		if($("#"+inventory).val().length>7){
			$("#inventorySpan"+index).html("<font color='red'> 库存为的长度不能超过7位</font>");	
			$("#skutag").val("false");
			return false;
		}
		checkSku(index);
		
		//  for (var i=1;i<=skunum;i++){
		//	  if(i==index){
		//		  $("#skutag").val("true");
		//		  $("#inventorySpan"+index).html("");
		//	  }else{
		//		  var skutemp=$.trim($("#color"+i).val())+$.trim($("#size"+i).val());
		//		  if(skutemp==myskn){
		//			  $("#inventorySpan"+index).html("<font color='red'> 此SKU的库存和颜色和第"+i+"行的SKU相同</font>");
		//			  $("#skutag").val("false");
		//			  return false;
		//						 
		//		  }else{
		//			  $("#skutag").val("true");
		//			  $("#inventorySpan"+index).html("");
		//		  }
		//	  }
		//  }
		return true;
	} 
	return false;
}


/**失去焦点时对供应商编号进行核对 obj代表当前供应商编号的输入框对象**/ 
function checkgoodSkuIDs(obj){
	var idValue=obj.id;
	//alert("idValue "+idValue);
	var   index   =   idValue.replace(/\D/g,   "");  
	var goodSkuValue =$.trim($("#"+idValue).val());
	
	if(goodSkuValue!=null && goodSkuValue!=""){
		if( /[\u4E00-\u9FA5]/.test(goodSkuValue)){
			alert("供应商编号不能含有中文");
			$("#"+idValue).val('');
		//	$("#"+idValue).focus();
			
		}
	}
	return false;
	
}

function checkSku(inventoryindex){	
	 $("span[name='inventorySpan']").html("");
	  $("div[name='dtdiv']").css({ "background": "#FFFFFF" }) ;
		var getgoods=$("input[name='goodsSku']");	
		var skunum=getgoods.length;
		var   index   =   inventoryindex;  	
		var tag=checkNextSku(inventoryindex);
		if(tag){
			for(var i=1;i<=skunum;i++){
			  var  myindex=getgoods[i-1].value;	
				if(myindex==inventoryindex){
					continue;
				}
				if(checkNextSku(myindex)){					
				}else{
					break;
				}
				
			}
		}
		return;
	}

function checkNextSku(inventoryindex){	
	var   index   =   inventoryindex;  
	var mycolor=$.trim($("#color"+index).val());
	if(mycolor==""){
		mycolor="无";
	}

	var mysize=$.trim($("#size"+index).val());
	if(mysize==""){
		mysize="均码";
	}
	var getgoods=$("input[name='goodsSku']");
	var myskn=mycolor+mysize;
	var skunum=getgoods.length;	
	  for (var i=1;i<=skunum;i++){
		  var  myindex=getgoods[i-1].value;	
		  if(myindex==index){
			  $("#skutag").val("true");
			  $("#inventorySpan"+index).html("");
			  continue;
		  }else{			  
			  var selectcolor=$.trim($("#color"+myindex).val());
			  var selectsize=$.trim($("#size"+myindex).val());
				if(selectcolor==""){
					selectcolor="无";
				}
				if(selectsize==""){
					selectsize="均码";
				}
			  var skutemp=selectcolor+selectsize;
			
			  if(skutemp==myskn){
				 
				  $("#inventorySpan"+index).html("<font color='red'> 此SKU的库存、颜色和第"+i+"行的SKU相同</font>");
				  $("#dtdiv"+myindex).css({ "background": "#FFCC33" }) ;
				  $("#dtdiv"+index).css({ "background": "#FFCC33" }) ;
				  $("#inventorySpan"+myindex).html("<font color='red'> 此为第"+i+"行</font>");
				  $("#skutag").val("false");
				return false;
							 
			  }else{
				  $("#dtdiv"+myindex).css({ "background": "#FFFFFF" }) ;
				  $("#dtdiv"+index).css({ "background": "#FFFFFF" }) ;
				  $("#skutag").val("true");
				  $("#inventorySpan"+index).html("");
				  $("#inventorySpan"+myindex).html("");
				  continue;
			  }
		  }
	  }
	  return true;
}

function checkSKU(){
	//var skunum=$("#skuNum").val();
	var getgoods=$("input[name='goodsSku']");	
	var skunum=getgoods.length;
	for(var j=1;j<=skunum;j++){
		 var  myindex=getgoods[j-1].value;	
		if(checkNextSku(myindex)){			
		}else{
			break;
		}
	}
	
}

function focusFun(obj,span){
	var mycolor=obj.id;
	var   index   =   mycolor.replace(/\D/g,   "");
	if(span=='colorSpan'){
		$("#colorSpan"+index).html("<font color='red'> 颜色的长度不能超过25</font>");	
	}
		
		
	
}

function clearFocus(obj){
	var mycolor=obj.id;
	var   index   =   mycolor.replace(/\D/g,   "");  
	$("#colorSpan"+index).html("");
}


function checkSort(obj){
	var mysort=obj.id;
	var   index   =   mysort.replace(/\D/g,   "");  
	$("#sortSpan"+index).html("");
	if($("#"+mysort).val()==""||$("#"+mysort).val()==null){
	
	}else{

		if(!/^[1-9]\d*$/.test($("#"+mysort).val())){
			$("#sortSpan"+index).html("<font color='red'> 排序号为整数</font>");	

			return false;
		}
		
		return true;
	} 
	return false;
}
