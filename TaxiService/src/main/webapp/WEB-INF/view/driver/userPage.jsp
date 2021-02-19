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
<fmt:message key="user.page.current.state"/> ${car.state.name}
<c:if test="${car.state.name eq 'waiting'}">
	<form action="controller" method="get">
		<input type="hidden" name="command" value="getDriverActualOrder">
		<input type="submit" value='<fmt:message key="user.page.button.actual.order"/>'>
	</form>
</c:if>
<form action="controller" method="post">
	<input type="hidden" name="command" value="changeState">
	<input type="submit" value='<fmt:message key="user.page.button.state.${car.state.name}"/>'>
</form>
<form action="controller" method="post">
	<input type="hidden" name="command" value="backToIndex">
	<input type="submit" value='<fmt:message key="receipt.button.back.to.index"/>'>
</form>
</body>
</html>