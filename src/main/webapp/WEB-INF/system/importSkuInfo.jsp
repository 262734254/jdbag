<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导入商品销售属性映射表界面</title>
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
           $("#importSkuInfo").submit();
       });
      
	 });


</script>

</head>
<body>
	<table class="adminMain_top">
		<tbody>
			<tr>
				<td class="td01"></td>
				<td class="td02"><h3 class="topTitle fb f14">导入商品走秀码</h3></td>
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
									
									<dd>导入商品销售属性映射表</dd>
								</dl>								
							
								<!--adminUp_wrap-->
							</div>
							<div class="adminContent" >		
								<s:form id="importSkuInfo" method="post"  enctype="multipart/form-data" action="importSkuInfo">
									
									<div class="adminContent clearfix" style="width: 500px">
										 文件：
		                                 
		                                 <input type="file" name="uploadFile" id="uploadFile" size="40" value="浏览"/>
		                                  <input type="button" id="uploadButton" value="导入"/>
		                                  </br>
		                                 <hr />
		                                 Excle模板为:</br>
		                                         如果是<font style="color:red">颜色</font>映射模板为: 类目1  	类目2	类目3	京东颜色	  走秀颜色
		                  </br>           
		                                      如果是<font style="color:red">尺码</font>映射模板为:  类目1	          类目2	 类目3	京东尺寸	 走秀尺寸
       
		                               <!--   <div>
		                                 
		                                 		<table class="listtable" width="300px">
		                                 			<tr>
		                                 				<th>商品名称</th>
		                                 				<th>供应商货号</th>
		                                 				<th>SKU</th>
		                                 				<th>库存</th>
		                                 			</tr>
		                                 			<tr>
		                                 				<td width="50px"></td>
		                                 				<td width="50px"></td>
		                                 				<td width="150px">64001844001</td>
		                                 				<td width="50px">100</td>		                                 				
		                                 			</tr>
		                                 			<tr>
		                                 				<td></td>
		                                 				<td></td>
		                                 				<td>64001844002</td>
		                                 				<td>200</td>		                                 				
		                                 			</tr>
		                                 			<tr>
		                                 				<td></td>
		                                 				<td></td>
		                                 				<td>64001844003</td>
		                                 				<td>300</td>		                                 				
		                                 			</tr>
		                                 		</table>
		                                 		
		                                 	
		                                 </div>
		 -->
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
