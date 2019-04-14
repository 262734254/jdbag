<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
   <table class="listtable">
		<thead>
			<tr>
				<th>走秀品牌ID</th>
				<th>走秀品牌名称</th>
			</tr>
		</thead>
		<tbody id="tg_tbd">
		<s:iterator value="pageView.records" var="resultObject" >
			<tr style="cursor:hand;" onclick="xiubrand_liclk(this);" id="tr_<s:property value="#resultObject.id" />">
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuBrandCode" />
				</td>
				<td  class="tlr" style="text-align: center">
					<s:property value="#resultObject.xiuBrandName" />
				</td>
			</tr>
		</s:iterator>
		</tbody>
	
		<tbody>
		<%@ include file="/WEB-INF/category/page.jsp"%>
		</tbody>
	</table>
