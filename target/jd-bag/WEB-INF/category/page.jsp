<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<s:if test='! pageView.records.isEmpty()'>
		<tr>
		<td colspan="12">
		       <%-- 总记录数 必须 --%>
				<c:set var="totalSize" value="${pageView.totalrecord }" />
				<%-- 每页记录数 必须 --%>
				<c:set var="pageSize" value="${pageSize}" />
				<%-- 当前请求也码 必须 --%>
				<c:set var="currentPage" value="${pageView.currentpage }" />
				<%-- 总页数 --%>
				<c:set var="totalPages" value="${pageView.totalpage }" />
		
		
				<%-- 分页导航条显示的页数 --%>
				<c:set var="spanPages" value="5" />
				<%-- 定义默认显示的导航页数 --%>
				<c:if test="${spanPages == null || spanPages <= 0}">
					<c:set var="spanPages" value="5" />
				</c:if>

		<div class="pagenav">
						<span>共${totalSize}条记录，每页显示${pageSize}条， 页码：${currentPage}/${totalPages}</span>
		
						
						<c:set var="startNav" value="${currentPage - (spanPages/2-1)}" />
								
						<c:if test="${startNav < 2}">
							<c:set var="startNav" value="1" />
						</c:if>
						<c:set var="endNav" value="${startNav + spanPages - 1}" />
						<c:if test="${endNav >= totalPages}">
							<c:set var="endNav" value="${totalPages}" />
							<c:set var="startNav" value="${endNav - spanPages + 1}" />
						</c:if>
						<c:if test="${startNav < 1}">
							<c:set var="startNav" value="1" />
						</c:if>
						
						
						<c:if test="${(startNav+0) > 1}">
							<a class="pageNavLink" href="javascript:topage('1')">1</a>
							<span>...</span>
						</c:if>
						
						<%-- 输出页数导航 开始 --%>
						<c:forEach var="i" begin="${startNav}" end="${endNav}">
							<c:choose>
								<c:when test="${i == currentPage}">
									<span class="cur">${i}</span>
								</c:when>
								<c:otherwise>
									<a class="pageNavLink" href="javascript:topage('${i}')">${i}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<%-- 输出页数导航 结束 --%>
		
						<c:if test="${endNav < totalPages}">
							<span>...</span>
							<a class="pageNavLink" href="javascript:topage('${totalPages }')">${totalPages }</a>
						</c:if>
						
						<input type="text" size="1" id="goPageno"></input><button onclick="topage2();" type="button" id="goButton">跳转</button>
				</div>
					</td>
				</tr>
	</s:if>
	<s:else>
		<tr><td colspan="15" class="cred">对不起没有符合条件的记录！</td></tr>
	</s:else>