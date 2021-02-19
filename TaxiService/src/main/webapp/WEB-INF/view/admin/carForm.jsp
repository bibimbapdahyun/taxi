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
<form action="controller" method="post">
	
	<fmt:message key="car.form.number"/><input type="text" name="number" placeholder="number">
	<fmt:message key="car.form.mark"/><input type="text" name="mark" placeholder="mark">
	<fmt:message key="car.form.places"/><input type="number" name="place" placeholder="places">
	<c:forEach var="type" items="${carTypes}">
		<input type="radio" name="type" value="${type.type}"><fmt:message key="car.form.type.${type.type}"/>
	</c:forEach>
	<input type="hidden" name="command" value="registerCar">
	<input type="submit" value='<fmt:message key="car.form.button.create"/>'>
</form>
<form action="controller" method="post">
	<input type="hidden" name="command" value="backToIndex">
	<input type="submit" value='<fmt:message key="receipt.button.back.to.index"/>'>
</form>
</body>
</html>