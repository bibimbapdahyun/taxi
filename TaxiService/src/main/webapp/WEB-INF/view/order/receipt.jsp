<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Response Order</title>
</head>
<body>
	Order ${order.id} was created
	<ul>
		<li><fmt:message key="receipt.start"/> ${order.start}</li>
		<li><fmt:message key="receipt.finish"/> ${order.finish}</li>
		<li><fmt:message key="receipt.places"/> ${order.places}</li>
		<li><fmt:message key="receipt.price"/> ${order.price}</li>
		<li><fmt:message key="receipt.car.type.${order.type.type}"/></li>
	</ul>
	<c:choose>
		<c:when test="${not empty Car && empty cars}">
			<ul>
				<li>${Car.carNumber}</li>
				<li>${Car.mark}</li>
			</ul>
		</c:when>
		<c:when test="${empty Car && not empty cars}">
			<c:forEach var="car" items="${cars}">
				<ul>
					<li>${car.carNumber}</li>
					<li>${car.mark }</li>
				</ul>
			</c:forEach>
		</c:when>
	</c:choose>
	<form action="controller" method="post">
		<input type="hidden" name="command" value="backToIndex" /> <input
			type="submit" value='<fmt:message key="receipt.button.back.to.index"/>' />
	</form>
</body>
</html>