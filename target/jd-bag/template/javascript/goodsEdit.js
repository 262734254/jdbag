
$(document).ready(function () {
	
	/*自动加载图片 */
	$.post("ajaxGetGoodsImgByGoodsId.action", {goodsId:$("#goodsId").val(),supplierId:$("#supplierId").val()}, function (data) {
		
		var jsonObj = eval("(" + data + ")");
		for (var i = 0; i < jsonObj.length; i++) {	
			
			var idtempstart=jsonObj[i].path.lastIndexOf("/");
    		var idvalue=jsonObj[i].path.substr(idtempstart+1);
    		var idtempend=idvalue.lastIndexOf(".");
    		var idtemp=idvalue.substr(0,idtempend);
    		if(jsonObj[i].type==1){
    			$("#fileQueue").html($("#fileQueue").html()+"&nbsp;&nbsp;&nbsp;&nbsp;"
	    		  	+"<span id='"+idtemp+"' class='imgview'>" 
	    		  	+"<table><tr><td >"		
	    		  	+"<a href='" + jsonObj[i].path + "' target='_blank' class='thumbnailslink'><img height='80' width='80'   src='"+jsonObj[i].thumbpath+"'/></a>"
	    			+"</td></tr>"
	    			+"<tr><td><input type='radio'  onclick=\"selectmainFile('"+idtemp +"')\"   name='mainuploadFile' id='mainuploadFile"+idtemp+"' checked='checked' value='"+jsonObj[i].path+"'/>主图   &nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"removethis('"+idtemp+"') \">删除</a>"
	    		  	+"<input type='hidden' name='uploadFile' value='"+jsonObj[i].path+"'/></td></tr></table>"	
	    		  	+"</span>"	
	    		);
    			$("#mainuploadFile"+idtemp).attr("checked","true");
    			$("#mainFileid").val(idtemp);
    		}else{
    			$("#fileQueue").html($("#fileQueue").html()+"&nbsp;&nbsp;&nbsp;&nbsp;"
    	    		  	+"<span id='"+idtemp+"' class='imgview'>" 
    	    		  	+"<table><tr><td >"		
    	    		  	+"<a href='" + jsonObj[i].path + "' target='_blank' class='thumbnailslink'><img height='80' width='80'   src='"+jsonObj[i].thumbpath+"'/></a>"
    	    			+"</td></tr>"
    	    			+"<tr><td><input type='radio'  onclick=\"selectmainFile('"+idtemp +"')\"   name='mainuploadFile' id='mainuploadFile"+idtemp+"' value='"+jsonObj[i].path+"'"
    	    			+"/>主图   &nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"removethis('"+idtemp+"') \">删除</a>"
    	    		  	+"<input type='hidden' name='uploadFile' value='"+jsonObj[i].path+"'/></td></tr></table>"	
    	    		  	+"</span>"	
    	    		);
    		}	
			
		}
		$("#filecount").val(jsonObj.length);
		$('a.thumbnailslink').popImage();
	});

	
});
