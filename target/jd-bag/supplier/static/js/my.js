$(document).ready(function () {
	/* 超类编码 */
	$.post("ajaxgetGoodsTypeSuperCode.action", function (data) {
		var jsonObj = eval("(" + data + ")");
		for (var i = 0; i < jsonObj.length; i++) {
			var $option = $("<option></option>");
			$option.attr("value", jsonObj[i].pid);
			$option.text(jsonObj[i].pname);
			$("#familyCode").append($option);
		}
	});
	/* 大类编码*/
	$("#familyCode").change(function () {
		$.post("ajaxgetGoodsTypeChildCode.action", {pid:$("#familyCode").val()}, function (data) {
			/* 中类编码 */
			$("#classCode option[value!='']").remove();
			/* 小类编码 */
			$("#childCode option[value!='']").remove();
			
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].cid);
				$option.text(jsonObj[i].cname);
				$("#classCode").append($option);
			}
		});
	});
	/* 中类编码*/
	$("#classCode").change(function () {
		$.post("ajaxgetGoodsTypeChildCode.action", {pid:$("#classCode").val()}, function (data) {
			/* 中类编码 */
			$("#classCode option[value!='']").remove();
			/* 小类编码 */
			$("#childCode option[value!='']").remove();
			
			var jsonObj = eval("(" + data + ")");
			for (var i = 0; i < jsonObj.length; i++) {
				var $option = $("<option></option>");
				$option.attr("value", jsonObj[i].cid);
				$option.text(jsonObj[i].cname);
				$("#childCode").append($option);
			}
		});
	});
	
	
	 $("#uploadify").uploadify({
		   'uploader'       : '../../../jqueryupload/uploadify.swf',
		   'script'         : 'scripts/uploadify',//servlet的路径或者.jsp 这是访问servlet 'scripts/uploadif' 
		   'method'         :'GET',  //如果要传参数，就必须改为GET
		   'cancelImg'      : '../../../jqueryupload/cancel.png',
		   'folder'         : 'uploads', //要上传到的服务器路径，
		   'queueID'        : 'fileQueue',
		   'auto'           : false, //选定文件后是否自动上传，默认false
		   'multi'          : true, //是否允许同时上传多文件，默认false
		   'simUploadLimit' : 1, //一次同步上传的文件数目  
		   'sizeLimit'      : 19871202, //设置单个文件大小限制，单位为byte  
		   'queueSizeLimit' : 5, //限制在一次队列中的次数（可选定几个文件）。默认值= 999，而一次可传几个文件有 simUploadLimit属性决定。
		   'fileDesc'       : '支持格式:jpg或gif', //如果配置了以下的'fileExt'属性，那么这个属性是必须的  
		   'fileExt'        : '*.jpg;*.gif',//允许的格式
		   'scriptData'     :{'name':$('#name').val()}, // 多个参数用逗号隔开 'name':$('#name').val(),'num':$('#num').val(),'ttl':$('#ttl').val()
		  	onComplete: function (event, queueID, fileObj, response, data) {
		    var value = response ;
		      alert("文件:" + fileObj.name + "上传成功");
		      fileObj.filePath
		   },  
		   onError: function(event, queueID, fileObj) {  
		    alert("文件:" + fileObj.name + "上传失败");  
		   },  
		   onCancel: function(event, queueID, fileObj){  
		    alert("取消了" + fileObj.name);  
		   } 
		  });
});
