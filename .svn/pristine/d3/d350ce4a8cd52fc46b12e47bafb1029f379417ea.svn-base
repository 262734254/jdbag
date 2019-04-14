
String.prototype.Trim = function()    
{    
	return this.replace(/(^\s*)|(\s*$)/g, "");    
}  
$(document).ready(function () {	        
//	$("#brandCodespan").hide();
	
	$.post("goods/ajaxGetSupplierBrandCode.action",{supplierId:$("#supplierId").val()}, function (data) {
		var jsonObj = eval('' + data + '');
			for (var i = 0; i < jsonObj.length; i++) {				
				var $option = $("<option>请选择</option>");			
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
					
				$("#brandCode").append($option);
			}
		});
	
	/* 超类编码 */
	$.post("goods/ajaxgetGoodsTypeSuperCode.action", function (data) {
		
		var jsonObj = eval("(" + data + ")");
		for (var i = 0; i < jsonObj.length; i++) {
			var $option = $("<option></option>");			
			$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
			$option.text(jsonObj[i].goodsTypeName);
			$("#superCode").append($option);	
			
		}
		//$("#superCode").html("<option value='请选择二级分类'></option>");
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
			$("#familyCode").html("<option value='-1'>请选择二级分类</option>");
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.text(jsonObj[i].goodsTypeName);
				$("#familyCode").append($option);
			}
			$("#goodsTypeNameSpan").html("");
		});
	});
	
	/* 大类编码*/
	$("#familyCode").change(function () {
		$.post("goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#familyCode").val(),level:2}, function (data) {
			/* 中类编码 */
			$("#classCode option[value!='']").remove();
			/* 小类编码 */
			$("#childCode option[value!='']").remove();
			$("#classCode").html("<option value='-1'>请选择三级分类</option>");
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.text(jsonObj[i].goodsTypeName);
				$("#classCode").append($option);
			}
			$("#goodsTypeNameSpan").html("");
		});
	});
	/* 中类编码*/
	$("#classCode").change(function () {
		$.post("goods/ajaxgetGoodsTypeChildCode.action", {goodsTypeCode:$("#classCode").val(),level:3}, function (data) {
		
			/* 小类编码 */
			$("#childCode option[value!='']").remove();
			$("#childCode").html("<option value='-1'>请选择四级分类</option>");
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].goodsTypeCode+"::"+jsonObj[i].goodsTypeName);
				$option.text(jsonObj[i].goodsTypeName);
				$("#childCode").append($option);
			}
			$("#goodsTypeNameSpan").html("");
		});
	});
	
	$("#childCode").change(function () {
		$("#goodsTypeNameSpan").html("");
	});
	//新增页面的
  $("#goodsSkuImg").click(function(){
	 
	  filterNum=$("#skuNum").val();
	  
	  filterNum++;
	 
	   $("#dtmaindiv").append("<div style='padding: 8px 30px;' name='dtdiv'  id='dtdiv"+(filterNum)+"'>"+
			  				"<input type='hidden'  name='goodshiddenSku' id='goodshiddenSku"+filterNum+"' value='"+filterNum+"' >"+			  				
	  						"<input type='radio'  id='goodsSkuId"+filterNum+"' name='goodsSku'  value='"+filterNum+"' >&nbsp;"+
	  					   "<input type='hidden' size='4' id='sort"+filterNum+"' />"+
	  					   "颜色:<input type='text' size='16' maxlength='16'  onblur='checkSku("+filterNum+")'  id='color"+filterNum+"' />&nbsp;&nbsp;&nbsp;"+
	  					   "尺码:<input type='text' size='4' maxlength='16'  onblur='checkSku("+filterNum+")'  id='size"+filterNum+"' />&nbsp;&nbsp;&nbsp;"+
	  					   "库存:<input type='text' size='4' maxlength='7' id='inventory"+filterNum+"' onblur='checkinventory(this)'/>" +
	  					   "<span style='color: #FF0000;'>&nbsp;*&nbsp;</span><span name='inventorySpan' id='inventorySpan"+ filterNum+"'/>&nbsp;&nbsp;"+
	  					  // "供应商商品编码:<input type='text' size='10'maxlength='20'  onblur='checkgoodSkuIDs(this)' id='goodSkuID"+filterNum+"' />&nbsp;"+ 
	  					  "<input type='hidden' size='10'maxlength='20'  onblur='checkgoodSkuIDs(this)' id='goodSkuID"+filterNum+"' />"+
	  					   "<a href=\'#\' id='dele"+filterNum+"'  onclick=\'deldtdiv("+filterNum+")\'>删除</a>"+
	  					   "</div>");
	    $("#skuNum").val(filterNum);  
	    $("#dtmaindiv :radio").unbind('click');
	    $("#dtmaindiv :radio").bind('click',function(){
	    	var flag=true;
	    	var radioObj=$(this).attr('id');
	    	var   index   =   radioObj.replace(/\D/g,   ""); 
	    	if($(this).attr('checked')){
	    		var deleButton=$("#dele"+index);
	    		if(index==1){
	    			 flag=false;
		    		$("#dele1").remove();
		    		
		    	}else{
		    		 $(deleButton).hide();	
		    	}

	    		//======================================
	    		var  radios= $("#dtmaindiv :radio");
	    		for(var i=0;i<radios.length;i++){
	    			var idValue=$(radios[i]).attr('id');
	    			//
	    			var   index2   =   idValue.replace(/\D/g,   ""); 
	    			if(index2!=1){
	    				if(radioObj!=idValue){
		    				$("#dele"+index2).show();
		    			}
	    			}else{
	    				 if(flag){
			    				$("#dele1").remove();
			    				$("#dtdiv1").append( "<a href=\'#\' id='dele1'  onclick='deldtdiv(1)'>删除</a>");
			    				
	    				 }
	    				  
	    				
	    			 }
	    			
	    			
	    		}
	    	}
	    		
	    });
	 
  });
  
  //编辑页面的SKU 
  $("#goodsSkuImgEdit").click(function(){
	  $("#selectmainsku").val($("#mainsku").val());
	  var usertype=$("#usertype").val();
		 if(usertype==1){
			 alert("不能新增SKU");
			 return false;
		 }else{
			  filterNum=$("#skuNum").val();
			  
			  filterNum++;
			 
			   $("#editdtmaindiv").append("<div style='padding: 8px 30px;' name='dtdiv'  id='dtdiv"+(filterNum)+"'>"+
					  				"<input type='hidden'  name='goodshiddenSku' id='goodshiddenSku"+filterNum+"' value='"+filterNum+"' >"+			  				
			  						"<input type='radio'  id='goodsSkuId"+filterNum+"' name='goodsSku'  value='"+filterNum+"' >&nbsp;"+
			  						"<input type='hidden'	id='skuid"+filterNum+"'	value=''/>"+
			  					   "<input type='hidden' size='4'  id='sort"+filterNum+"'/>"+
			  					   "<span id='sortSpan"+ filterNum+"'/>"+
			  					   "颜色:<input type='text' size='16' maxlength='16' onblur='checkSku("+filterNum+")' id='color"+filterNum+"' />&nbsp;&nbsp;&nbsp;"+
			  					   "尺码:<input type='text' size='4' maxlength='16'  onblur='checkSku("+filterNum+")'  id='size"+filterNum+"' />&nbsp;&nbsp;&nbsp;"+
			  					   "库存:<input type='text' size='4' maxlength='7' id='inventory"+filterNum+"' onblur='checkinventory(this)'/>" +
			  					   "&nbsp;<span style='color: #FF0000;'>*</span><span name='inventorySpan' id='inventorySpan"+ filterNum+"'/>&nbsp;&nbsp;&nbsp;&nbsp;"+
			  					   //"供应商商品编码:<input type='text' size='10' maxlength='20' onblur='checkgoodSkuIDs(this)' id='goodSkuID"+filterNum+"' />"+
			  					 "<input type='hidden' size='10' maxlength='20' onblur='checkgoodSkuIDs(this)' id='goodSkuID"+filterNum+"' />"+
			  					   "<a href=\'#\' id='dele"+filterNum+"'  onclick=\'deldtdivEidt("+filterNum+")\'>删除</a>"+
		
			  					   "</div>");
			    $("#skuNum").val(filterNum);  
			    $("#editdtmaindiv :radio").unbind('click');
			    $("#editdtmaindiv :radio").bind('click',function(){
			  	  var checkedvalue=$("#selectmainsku").val();
			  		//  checkedvalue++;
			  	    	var flag=true;
			  	    	var radioObj=$(this).attr('id');
			  	    	var   index   =   radioObj.replace(/\D/g,   ""); 
			  	    	if($(this).attr('checked')){
			  	    		var deleButton=$("#dele"+index);
			  	    		if(index==checkedvalue){
			  	    			 flag=false;
			  		    		$("#dele"+index).remove();
			  		    		
			  		    	}else{
			  		    		$("#selectmainsku").val(index);
			  		    		$("#dele"+checkedvalue).remove();
			  		    		$("#dtdiv"+checkedvalue).append( "<a href=\'#\' id='dele"+checkedvalue+"'  onclick='deldtdivEidt("+checkedvalue+")'>删除</a>");
			  		    		$("#dele"+index).remove();
			  		    	}

			  	    		//======================================
			  	    
			  	    	}
			  	    		
			  	    });
		
		 }
  });
  $("#editdtmaindiv :radio").bind('click',function(){
	  var checkedvalue=$("#mainsku").val();
	  if(checkedvalue==""||checkedvalue==undefined){
		  checkedvalue=$("#selectmainsku").val();
	  }else{
		  $("#selectmainsku").val(checkedvalue);
	  }
		//  checkedvalue++;
	    	var flag=true;
	    	var radioObj=$(this).attr('id');
	    	var   index   =   radioObj.replace(/\D/g,   ""); 
	    	if($(this).attr('checked')){
	    		var deleButton=$("#dele"+index);
	    		if(index==checkedvalue){
	    			 flag=false;
		    		$("#dele"+index).remove();
		    		
		    	}else{
		    		$("#selectmainsku").val(index);
		    		$("#dele"+checkedvalue).remove();
		    		$("#dtdiv"+checkedvalue).append( "<a href=\'#\' id='dele"+checkedvalue+"'  onclick='deldtdivEidt("+checkedvalue+")'>删除</a>");
		    		$("#dele"+index).remove();
		    	}

	    		//======================================
	    
	    	}
	    		
	    });
  
  $("#uploadify").uploadify({
	   'uploader'       : '../jqueryupload/uploadify.swf',
	   'script'         : '../scripts/uploadify',//servlet的路径或者.jsp 这是访问servlet 'scripts/uploadif' 
	   'method'         :'GET',  //如果要传参数，就必须改为GET
	   'cancelImg'      : '../jqueryupload/cancel.png',
	   'folder'         : 'uploads', //要上传到的服务器路径，
	   'queueID'        : 'fileQueue',
	   'auto'           : false, //选定文件后是否自动上传，默认false
	   'multi'          : true, //是否允许同时上传多文件，默认false
	   'simUploadLimit' : 1, //一次同步上传的文件数目  
	   'sizeLimit'      : 1048576, //设置单个文件大小限制，单位为byte  
	   'queueSizeLimit' : 5, //限制在一次队列中的次数（可选定几个文件）。默认值= 999，而一次可传几个文件有 simUploadLimit属性决定。
	   'fileDesc'       : '支持格式:jpg', //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
	   'fileExt'        : '*.jpg',//允许的格式 
	   'buttonImg'      : '../jqueryupload/button_browse.png',//选择文件按钮背景
	   'width'          : 65,
	   'height'			: 30,
	   'scriptData'     :{'supplierId':$('#supplierId').val(),'operatorId':$('#operatorId').val(),'command':'uploadFile'}, // 多个参数用逗号隔开 'name':$('#name').val(),'num':$('#num').val(),'ttl':$('#ttl').val()
	   onSelect: function(e, queueId, fileObj) {
			var filecount=$("#filecount").val();
			filecount++;						
			if(filecount>5){	
				alert("图片只许上传5张");
				return false;
			}
			
	   },
	  	onComplete: function (event, queueID, fileObj, response, data) {
	    var value = response ;
	    //  alert("文件:" + fileObj.name + "上传成功");
	    var filecount=$("#filecount").val();
	    var strs= new Array(); //定义一数组
	    strs=response.split(","); //字符分割      
	    var temp=$("#fileQueue").html();
	    if(response.indexOf("formatNamerror")>=0){
	    	 alert("文件:" + fileObj.name + "上传失败，只许上传jpg格式!");
	    }else{
	    
	    for (i=0;i<strs.length ;i++ )   
	    {   

	    		var idtempstart=strs[i].lastIndexOf("/");
	    		var idvalue=strs[i].substr(idtempstart+1);
	    		var idtempend=idvalue.lastIndexOf(".");
	    		var idtemp=idvalue.substr(0,idtempend);
	    		var thumbpath=strs[i];
	    		var mainpath=thumbpath.replace("/thumb/", "/");
	    		if(filecount==0){
	    			$("#fileQueue").html($("#fileQueue").html()+"&nbsp;&nbsp;&nbsp;&nbsp;"			    		  
		        		 	+"<span id='"+idtemp+"' class='imgview'>" 
			    		  	+"<table><tr><td >"		    		  	
			    		  	+"<a href='" + mainpath + "' target='_blank' class='thumbnailslink'><img height='80' width='80' title='"+fileObj.name+"'  src='"+thumbpath+"'/>" 
			    		  	+"</td></tr>"
			    		  	+"<tr><td><input type='radio'  onclick=\"selectmainFile('"+idtemp +"')\" name='mainuploadFile' id='mainuploadFile"+idtemp +"'  value='"+mainpath+"'/>主图   &nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"removethis('"+idtemp+"') \">删除</a>"		    		
			    		  	+"<input type='hidden' name='uploadFile' value='"+mainpath+"'/></td></tr></table>" 
			    			+"</span>"	    	
			    		  	
			    	);
	    			$("#mainuploadFile"+idtemp).attr("checked","true");
	    			$("#mainFileid").val(idtemp);
	    		}else{
	    		
	    			$("#fileQueue").html($("#fileQueue").html()+"&nbsp;&nbsp;&nbsp;&nbsp;"			    		  
		        		 	+"<span id='"+idtemp+"' class='imgview'>" 
			    		  	+"<table><tr><td >"		    		  	
			    		  	+"<a href='" +mainpath + "' target='_blank' class='thumbnailslink'><img height='80' width='80' title='"+fileObj.name+"'  src='"+thumbpath+"'/>" 
			    		  	+"</td></tr>"
			    		  	+"<tr><td><input type='radio' onclick=\"selectmainFile('"+idtemp +"')\"  name='mainuploadFile' id='mainuploadFile"+idtemp +"' value='"+mainpath+"'/>主图   &nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"removethis('"+idtemp+"') \">删除</a>"		    		
			    		  	+"<input type='hidden' name='uploadFile' value='"+mainpath+"'/></td></tr></table>" 
			    			+"</span>"	    	
			    		  	
			    	);
	    			var mainFileid=$("#mainFileid").val();
	    			if(mainFileid!=""){	    				
	    				$("#mainuploadFile"+mainFileid).attr("checked","true");
	    			}
	    		}
	    		
	    		filecount++;
	    		$("#filecount").val(filecount);	    	
	    	
	    } 
	    $(".uploadifyQueueItem").remove();
	      
	    $('a.thumbnailslink').popImage();
	    }
	   },  
	   onError: function(event, queueID, fileObj) {  
		   if(eval(fileObj.size>1048576)){
			   alert("此 [\" "+fileObj.name+" \"]的大小超过1M,不可上传!");
			   jQuery('#uploadify').uploadifyCancel(queueID);
		   }else{
			   alert("文件:" + fileObj.name + "上传失败");
		   }
	      
	   },  
	   onCancel: function(event, queueID, fileObj){  
	    alert("取消了" + fileObj.name);  
	   
	  	}
	  });

	
});

function uploasFile(){ 
	
	var filecount=$("#fileQueue span img").length;
	var uploadfile=$(".uploadifyQueueItem").length;
	
	
	if(eval(filecount+uploadfile)==0){
		alert("请上传商品图片");
		return false;
	}
	if(eval(filecount+uploadfile)>5){
		alert("只可上传5张图片");
		$(".uploadifyQueueItem").remove();
		jQuery('#uploadify').uploadifyClearQueue();
		return false;
	}else{
		if(eval(uploadfile)>0){
		  //设置 scriptData 的参数
		  $('#uploadify').uploadifySettings('scriptData',{'supplierId':$('#supplierId').val(),'operatorId':$('#operatorId').val()});
	     //上传
		  jQuery('#uploadify').uploadifyUpload();
		}
	  return true;
		  
	}
		 
}

function getBrandByName(){
	if($("#brandNameInput").val()==""){
		alert("请输入查询的品牌");
		return false;
	}
	$("#brandCodespan").show();
	
	$.post("goods/ajaxGetGoodsBrandCode.action", {brandNameInput:$("#brandNameInput").val()}, function (data) {
		$("#brandCode option[value!='']").remove();

		var jsonObj = eval(''+data+'');

		if(jsonObj.length<1){
			var $option = $("<option></option>");
			$option.attr("value", '-1');
			$option.text('无符合条件的记录');
			$("#brandCode").append($option);
		}else{
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
			$("#brandCode").append($option);
		}}
	});
}

function deldtdiv(index)
{        
	//skucount=$("#skucount").val();  
	
    $("#dtdiv"+index+"").remove();//删除当前行
    checkSKU();
 
  //  $("#skucount").val(skucount-1);
    
}	

function deldtdivEidt(index)
{     
	var synstatus=$("#synstatus"+index+"").val();
    var deleteskuids=$("#deleteSkuIds").val();
    var deletesku=$("#skuid"+index+"").val();
    if(synstatus==1){
    	alert("此SKU已同步，无法进行删除！");
    	return false;
    }else{
    
	    if(deleteskuids==""||null==deleteskuids){
	    	if(deletesku==""||null==deletesku){    		
	    	}else{
	    		deleteskuids=deletesku;
	    	}    	
	    }else{
	    	if(deletesku==""||null==deletesku){    		
	    	}else{
	    		deleteskuids=deleteskuids+"::"+deletesku;
	    	}
	    }
	 //   $("#skuNum").val($("#skuNum").val()-1)
	    $("#deleteSkuIds").val(deleteskuids);
	    $("#dtdiv"+index+"").remove();//删除当前行
	    checkSKU();
    }
    
}
 
//新增保存商品信息
function savegoods(){
	changeBrandName();
	var form = $("#addgoodsForm");

	 var getgoods=$("input[name='goodsSku']");
	 skucount=getgoods.length;
	 if(skucount==0){
		 alert("SKU不能为空!");
		 return ;
	 }
	  for(var j=0;j<skucount;j++){
		  	var  i=getgoods[j].value;	
		  	var sku=""+j+":"+$("#color"+i).val().Trim()+":"+$("#size"+i).val().Trim()+":"+$("#inventory"+i).val().Trim()+":"+$("#goodsCode").val().Trim();
		  	if(getgoods[j].checked==true){
		  		sku=sku+":1";
		  	}else{
		  		sku=sku+":0";
		  	}			 
			$("#goodshiddenSku"+i).val(sku);
	  }
	//校验市场价marketPrice: 走秀价xiuPrice  活动价activityPrice
	  if(insertGoodsCheckFrmNew()){
		  form.submit(); 
	  }
}


//编辑保存商品信息
function saveupdategoods(){
	changeBrandName();
	var form = $("#addgoodsForm");

	 var getgoods=$("input[name='goodsSku']");	
	 skucount=getgoods.length;	
	 if(skucount==0){
		 alert("SKU不能为空!");
		 return ;
	 }
	  for(var j=0;j<skucount;j++){
		  	var  i=getgoods[j].value;	
		  	var sku=""+j+":"+$("#color"+i).val().Trim()+":"+$("#size"+i).val().Trim()+":"+$("#inventory"+i).val().Trim()+":"+$("#goodsCode").val().Trim();
		  	if(getgoods[j].checked==true){
		  		sku=sku+":1";
		  	}else{
		  		sku=sku+":0";
		  	}		
		 	sku=sku+":"+$("#skuid"+i).val().Trim();
			$("#goodshiddenSku"+i).val(sku);
	  }
	//校验市场价marketPrice: 走秀价xiuPrice  活动价activityPrice
	  if(insertGoodsCheckFrmNew()){
		  form.submit(); 
	  }
}

function changeBrandName(){	
	$("#brandName").val($("#brandCode").find("option:selected").text());	
}


function removethis(oid)  {

	$("#"+oid).remove();
	var filecount=$("#filecount").val();
	$("#filecount").val(eval(filecount-1));
	
	var mainFileid=$("#mainFileid").val();
	if(oid==mainFileid){
		$("#mainFileid").val("");
		alert("主图已删除，请重新选择主图！");
	}
	
}

function insertGoodsCheckFrm() {
	if($("#goodsName").val()==""){
		document.getElementById("msg").innerHTML = "商品名称不能为空！";
		$("#goodsName").focus();
		return false;
	} else if($("#brandCode").val()=="-1"||$("#brandCode").val()==null){
		document.getElementById("msg").innerHTML = "品牌不能为空！";
		$("#brandCode").focus();
		return false;
	} else if($("#superCode").val()=="-1"||$("#superCode").val()==null){
		document.getElementById("msg").innerHTML = "商品一级分类不能为空！";
		$("#superCode").focus();
		return false;
	} else if($("#familyCode").val()=="-1"||$("#familyCode").val()==null){
		document.getElementById("msg").innerHTML = "商品二级分类不能为空！";
		$("#familyCode").focus();
		return false;
	} else if($("#classCode").val()=="-1"||$("#classCode").val()==null){
		document.getElementById("msg").innerHTML = "商品三级分类不能为空！";
		$("#classCode").focus();
		return false;
	} else if($("#childCode").val()=="-1"||$("#childCode").val()==null){
		document.getElementById("msg").innerHTML = "商品四级分类不能为空！";
		$("#childCode").focus();
		return false;
	} else if ($("#marketPrice").val()=="") {
		document.getElementById("msg").innerHTML = "市场价不能为空！";
		$("#marketPrice").focus();
		return false;				
	} else if (!/^[1-9]\d*$/.test($("#marketPrice").val())) {
		document.getElementById("msg").innerHTML = "市场价应该为正整数！";
		$("#marketPrice").focus();
		return false;				
	} else if ($("#xiuPrice").val()=="") {
		document.getElementById("msg").innerHTML = "走秀价不能为空！";
		$("#xiuPrice").focus();
		return false;				
	} else if (!/^[1-9]\d*$/.test($("#xiuPrice").val())) {
		document.getElementById("msg").innerHTML = "走秀价应该为正整数！";
		$("#xiuPrice").focus();
		return false;				
	} else if ($("#activityPrice").val()=="") {
		document.getElementById("msg").innerHTML = "活动价不能为空！";
		$("#activityPrice").focus();
		return false;				
	} else if (!/^[1-9]\d*$/.test($("#activityPrice").val())) {
		document.getElementById("msg").innerHTML = "活动价应该为正整数！";
		$("#activityPrice").focus();
		return false;				
	} else {		
			return true;	
	
	}
}


function insertGoodsCheckFrmNew() {
	checkgoodsName();
	var goodsNametag = $("#goodsNametag").val();
	if(goodsNametag=="success"){
		if(checkbrandCode()){
			if(checkgoodsTyeCode()){
				if(checkmarketPrice()){
					if(checkxiuPrice()){
						if(checkactivityPrice()){
							checkgoodsCode();
							var goodsCodetag = $("#goodsCodetag").val();
							if(goodsCodetag=="success"){
								//核对库存
								checkSKU();
								var skutag=$("#skutag").val();
								if(skutag=="true"){
									if(checkInventoryOnSubmit()){
										if(uploasFile()){
											if(checkMainUploadFile()){
												if(updateTextArea()){									
													return true;	
												}
										
											}
										}
									}	
								}
							}
						}
					}
				}
			}
		}
	}
}

//表单提交时校验库存
function checkInventoryOnSubmit(){
	var flag=true;

	 var getgoods=$("input[name='goodsSku']");
	 var skucount=getgoods.length;
	  for(var j=0;j<skucount;j++){
		  	var  index=getgoods[j].value;	
		  	//库存
		  	var inventory="inventory"+index;
		  	//排序
		  	var sort="sort"+index;
		  	$("#inventorySpan"+index).html("");	
			if($("#"+inventory).val()==""||$("#"+inventory).val()==null){
				$("#"+inventory).html("");
				alert("库存不能为空");
				$("#inventorySpan"+index).html("<font color='red'>库存不能为空！</font>");
				$("#"+inventory).focus();
				flag=false;
				break;
				
			}else if(!/^[0-9]\d*$/.test($("#"+inventory).val())){
				alert("库存为整数");
				$("#inventorySpan"+index).html("<font color='red'>库存为整数</font>");
				$("#"+inventory).focus();
				flag=false;
				break;
			  }
			else if($("#"+inventory).val().length>7){
			
				$("#inventorySpan"+index).html("<font color='red'>库存为的长度不能超过7位</font>");
				$("#"+inventory).focus();
				flag=false;
				break;
			  }
			if($("#"+sort).val()==""||$("#"+sort).val()==null){
				
			}else{
				if(!/^[0-9]\d*$/.test($.trim($("#"+sort).val()))){
					alert("排序项必须是整数,请重新输入！");
					$("#"+sort).val('');
					$("#"+sort).focus();
					flag=false;
					break;
					}
			}
			
			//1
				
				
			} 
	     return flag;
		  	
	  }



 function checkMainUploadFile(){
	 
	var  checkval= $("input[name='mainuploadFile']:checked").val();
	if(checkval==null||checkval==undefined){
		alert("请选择主图!");
		return false;
	}else{
		return true;
	}
 }
 
 function selectmainFile(mainFileid){
	 $("#mainFileid").val(mainFileid);
	
 }


