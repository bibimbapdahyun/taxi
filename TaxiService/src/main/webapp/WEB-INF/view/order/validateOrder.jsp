<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Info Order</title>
</head>
<body>
	<c:choose>
		<%-- <c:when test="${order.places >= 10 }">
			${placesMessage}
			<form action="controller" method="POST">
				<br /> <input type="hidden" name="command"
					value="checkOutBigOrder"> <input type="submit"
					value="Submit">
			</form>
		</c:when> --%>
		<c:when test="${not empty ccp}">
			Order info: 
			<ul>
				<li><fmt:message key="validate.order.account.login"/> ${order.account.login}</li>
				<li><fmt:message key="receipt.start=Start"/> ${order.start}</li>
				<li><fmt:message key="receipt.finish=Finish"/> ${order.finish}</li>
				<li><fmt:message key="receipt.places=Places"/> ${order.places}</li>
				<li><fmt:message key="validate.order.number"/> ${order.type}</li>
				<li>${message}</li>
			</ul>
			<form action="controller" method="post">
				<c:forEach var="count" items="${ccp}">
					<input type="radio" name="count" value="${count.type.type}">${count.type.type}
					<ul>
						<li><fmt:message key="validate.order.car.count"/> ${count.cars.size()}</li>
						<li><fmt:message key="receipt.price"/> ${count.price}</li>
					</ul>
				</c:forEach>
				<input type="hidden" name=command value="createOrder"> 
				<input type="submit" value='<fmt:message key="orderForm.button.set.order"/>'>
			</form>
		</c:when>
		<c:when test="${empty Car && empty cpp}">
			${carsNotFound}
		</c:when>
		<c:when test="${not empty Car}">
			Order info: 
			<ul>
				<li><fmt:message key="receipt.start"/>${order.start}</li>
				<li><fmt:message key="receipt.finish"/>${order.finish}</li>
				<li><fmt:message key="receipt.places"/>${order.places}</li>
				<li><fmt:message key="statistics.format.order.type"/>${order.type.type}</li>
				<li><fmt:message key="statistics.format.order.cars.number"/>${Car.carNumber}</li>
				<li><fmt:message key="statistics.format.order.cars.mark"/>${Car.mark}</li>
				<li><fmt:message key="receipt.price"/>${order.price }</li>
			</ul>
			<form action="controller" method="post">
				<input type="hidden" name=command value="createOrder"> 
				<input type="submit" value='<fmt:message key="orderForm.button.set.order"/>'>
			</form>
		</c:when>
	</c:choose>
	<form action="controller" method="post">
		<input type="hidden" name="command" value="cancelOrder"> 
		<input type="submit" value='<fmt:message key="cancel.button"/>'>
	</form>
</body>
</html>