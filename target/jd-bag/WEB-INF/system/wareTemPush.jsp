<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入商品走秀码界面</title>
<link href="<%=request.getContextPath()%>/supplier/static/css/base.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/supplier/static/css/common.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/supplier/static/css/page_admin_main.css"	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/template/css/additional.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/template/javascript/bgiframe.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/template/uiplugin/weebox/src/bgiframe.js"></script>

<script type="text/javascript">
 $(document).ready(function(){
      
      $("#uploadButton").click(function(){
           var val= $.trim($("#uploadFile").val());
           if(val==null || val==""){
        	   alert("请选择上传文件");
        	   return;
            }
           //得到上传文件的扩展名
           var ext=val.substr(val.lastIndexOf(".")+1,val.length);
           if(ext!="xls" && ext!="xlsx"){
        	   alert("您上传的文件不是Excle文件,请上传Excle文件");
        	   return;
           }
           $("#goodsXiuCodeUpload").submit();
       });
      
      
      $("#uploadSkuButton").click(function(){
          var val= $.trim($("#uploadSkuFile").val());
          if(val==null || val==""){
       	   alert("请选择上传文件");
       	   return;
           }
          //得到上传文件的扩展名
          var ext=val.substr(val.lastIndexOf(".")+1,val.length);
          if(ext!="xls" && ext!="xlsx"){
       	   alert("您上传的文件不是Excle文件,请上传Excle文件");
       	   return;
          }
          $("#skuUpload").submit();
      });
      
	 });


</script>

</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">商品推送</h3></td>
				<td class="td03"></td>
			</tr>
		</tbody>
	</table>
	<table class="adminMain_main" style="width: 100%">
		<tbody>
			<tr>
				<td class="td01"></td>				
				<td class="adminMain_wrap" >
					<div class="wrapMain">
							<div class="adminUp_wrap">
								<dl class="adminPath clearfix">
									<dt>您现在的位置：</dt>
									
									<dd>商品推送</dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
							<div class="adminContent" >		
								<s:form id="goodsXiuCodeUpload" method="post"  enctype="multipart/form-data" action="wareTemPush">
									
									<div class="adminContent clearfix" style="width: 800px">
										 文件：
		                                 
		                                 <input type="file" name="uploadFile" id="uploadFile" size="40" value="浏览"/>
		                                  <input type="button" id="uploadButton" value="导入商品信息模板"/>
		                                 <%-- <a href="<%=request.getContextPath()%>/jd/xiuCodeTemplateDown.action" >模板下载</a> --%>
									</div>
									<div id="text" style="font-style: italic;color: red;font-size: small;">导入商品信息模板的同时会导出sku信息模板,填好sku模板再导入即可</div>
									<!--主体内容结束-->
								</s:form>
							</div>	
							
							<div class="adminContent" >		
								<s:form id="skuUpload" method="post"  enctype="multipart/form-data" action="wareSkuTemPush">
									
									<div class="adminContent clearfix" style="width: 800px">
										 文件：
		                                 
		                                 <input type="file" name="uploadFile" id="uploadSkuFile" size="40" value="浏览"/>
		                                  <input type="button" id="uploadSkuButton" value="导入sku信息模板"/>
		                                 <%-- <a href="<%=request.getContextPath()%>/jd/xiuCodeTemplateDown.action" >模板下载</a> --%>
									</div>
									<!--主体内容结束-->
								</s:form>
							</div>	
						
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
