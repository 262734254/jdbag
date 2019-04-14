$(function() {
	alert("======================");
/**
            编辑或删除限时特卖
             url: 提交的路径
            flag: 1代表编辑操作，2代表删除操作
    **/
    function editAndDeleteLimited(url,flag){
    	if(flag==1){
    		document.location.href=url;
    	}else if(flag==2){
    		if(confirm("你确定要删除么?")){
    			document.location.href=url;
    		}
    	}
    }
    
    
  /**
   * 将格式为startDate:2012-10-12 10:20:30
   * 转换为毫秒值，用于比较
   * **/

    function comparableTime(startDate){
    	if(startDate!=null && startDate!=""){
    		var time=startDate.split(" ");
    		var ymd=$.trim(time[0]);
    		var hms=$.trim(time[1]);
    		if(ymd!=null && ymd!="" && hms!=null && hms!=""){
    			var temp_ymd=ymd.split("-");
    			var temp_hms=hms.split(":");
    			if(temp_ymd!=null){
    				var date=new Date(temp_ymd[0],temp_ymd[1]-1,temp_ymd[2],temp_hms[0],temp_hms[1],temp_hms[2]);
    				//返回时间的毫秒值
    				return date.getTime();
    			}
    		}
    	}
    }
    
    
});