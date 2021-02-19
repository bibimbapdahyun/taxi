<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Statistics</title>
</head>
<body>
	<c:if test="${not empty errorMessage}">
		<fmt:message key="statistics.error.message"/>${errorMessage}
	</c:if>
	<form action="controller" method="get">
		<input type="hidden" name="command" value="getStatistics" />
		<input type="hidden" name="page" value="1"/>
		<input type="submit" value='<fmt:message key="index.manager.statistics"/>'/>
	</form>
	<form action="controller" method="get">
		<input type="radio" name="sort" value="up" checked="checked"><fmt:message key="statistics.up"/>
		<input type="radio" name="sort" value="down"><fmt:message key="statistics.down"/>
		<input type="hidden" name="page" value="1"/>
		<input type="hidden" name="command" value="sortByDate"/>
		<input type="submit" value='<fmt:message key="statistics.sort.by.date"/>' />
	</form>
	<form action="controller" method="get">
		<input type="radio" name="sort" value="up" checked="checked"><fmt:message key="statistics.up"/>
		<input type="radio" name="sort" value="down"><fmt:message key="statistics.down"/>
		<input type="hidden" name="page" value="1"/>
		<input type="hidden" name="command" value="sortByPrice" /> 
		<input type="submit" value='<fmt:message key="statistics.sort.by.price"/>' />
	</form>
	<form action="controller" method="get">
		<input type="date" name="startDate" required="required"/>
		<input type="hidden" name="command" value="filterByDate" /> 
		<input type="hidden" name="page" value="1"/>
		<input type="submit" value='<fmt:message key="statistics.filter.by.date"/>' />
	</form>
	<form action="controller" method="get">
		<input type="text" name="login"/>
		<input type="hidden" name="command" value="filterByAccount" /> 
		<input type="hidden" name="page" value="1"/>
		<input type="submit" value='<fmt:message key="statistics.filter.by.account"/>' />
	</form>
	<hr>
	<form action="controller" method="post">
		<input type="hidden" name="command" value="backToIndex">
		<input type="submit" value='<fmt:message key="receipt.button.back.to.index"/>'>
	</form>
	<hr>
	<c:if test="${not empty orders}">
		<c:forEach var="order" items="${orders}">
			<fmt:message key="validate.order.number"/> ${order.id}
			<fmt:message key="orderForm.start"/> ${order.start}
			<fmt:message key="orderForm.finish"/> ${order.finish}
			<fmt:message key="statistics.format.order.date"/> ${order.data}
			<fmt:message key="receipt.price"/> ${order.price}
			<fmt:message key="validate.order.account.login"/> ${order.account.login}
			<fmt:message key="orderForm.places"/> ${order.places}
			<fmt:message key="statistics.format.order.state"/> ${order.tState.name}
			<fmt:message key="statistics.format.order.type"/> ${order.type.type}
			<fmt:message key="statistics.format.order.cars"/> 
			<c:forEach var="c" items="${order.car}">
				<fmt:message key="statistics.format.order.cars.number"/> ${c.carNumber}
				<fmt:message key="statistics.format.order.cars.mark"/> ${c.mark}
			</c:forEach><br>
		</c:forEach><br>
	</c:if>
	<hr>
	<%= request.getParameter("command") %>/
	${cmd}/
	${pageCount}/
	<%= request.getParameter("page") %>
	<hr>
	<c:choose>
		<c:when test="${page > 1}">
			<a href="controller?command=${cmd}&page=${page - 1}"><fmt:message key="statistics.page.prev"/></a>
		</c:when>
		<c:otherwise>
			<fmt:message key="statistics.page.prev"/>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${page < pageCount}">
			<a href="controller?command=${cmd}&page=${page + 1}"><fmt:message key="statistics.page.next"/></a>
		</c:when>
		<c:otherwise>
			<fmt:message key="statistics.page.next"/>
		</c:otherwise>
	</c:choose>
	<hr>
</body>
</html>