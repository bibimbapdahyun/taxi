<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Order</title>
</head>
<body>
<form action="controller" method="POST">
	<fmt:message key="orderForm.start"/><input type="text" name="from" placeholder="Start address"/>
	<br>
	<fmt:message key="orderForm.finish"/><input type="text" name="to" placeholder="End address"/>
	<br>
	<fmt:message key="orderForm.places"/><input type="number" name="places" placeholder="Count of places"/>
	<br>
	<c:forEach var="type" items="${carTypes}">
		<input type="radio" name="type" value="${type.type}"/> <fmt:message key="orderForm.car.type.${type.type }"/>
	</c:forEach>
	<input type="hidden" name="command" value="setOrder"/>
	<br>
	<input type="submit" value='<fmt:message key="orderForm.button.set.order"/>'/>
</form>
</body>
</html>