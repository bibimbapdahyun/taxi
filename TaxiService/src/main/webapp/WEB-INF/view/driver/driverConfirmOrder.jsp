<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:choose>
	<c:when test="${not empty order}">
		<fmt:message key="validate.order.number"/> ${order.id}
		<fmt:message key="receipt.start"/>${order.start}
		<fmt:message key="receipt.finish"/>${order.finish}
		<fmt:message key="statistics.format.order.date"/>${order.data}
		<fmt:message key="receipt.price"/>${order.price}
		<fmt:message key="validate.order.account.login"/>${order.account.login}
		<fmt:message key="receipt.places"/>${order.places}
		<fmt:message key="statistics.format.order.state"/>${order.tState.name}
		<form action="controller" method="get">
			<input type="hidden" name="command" value="finishOrder" /> 
			<input type="submit" value='<fmt:message key="driver.finish.order"/>' />
		</form>
	</c:when>
	<c:when test="${not empty errorMessage}">
		${errorMessage}
	</c:when>
</c:choose>
	<hr>
	<c:if test="${not empty orders}">
		<c:forEach var="order" items="${orders}">
			<fmt:message key="validate.order.number"/>${order.id}
			<fmt:message key="receipt.start"/>${order.start}
			<fmt:message key="receipt.finish"/>${order.finish}
			<fmt:message key="statistics.format.order.date"/>${order.data}
			<fmt:message key="receipt.price"/>${order.price}
			<fmt:message key="validate.order.account.login"/>${order.account.login}
			<fmt:message key="receipt.places"/>${order.places}
			<fmt:message key="statistics.format.order.state"/>${order.tState.name}
			<br>
		</c:forEach><br>
	</c:if>
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
	<form action="controller" method="get">
		<input type="hidden" name="command" value="backToIndex" /> <input
			type="submit" value='<fmt:message key="receipt.button.back.to.index"/>'/>
	</form>
</body>
</html>