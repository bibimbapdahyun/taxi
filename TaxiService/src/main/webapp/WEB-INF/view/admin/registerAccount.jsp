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
	<c:forEach var="role" items="${accountRoles}">
		<input type="radio" name="role" value="${role.role}"/><fmt:message key="register.account.role.${role.role}"/>
	</c:forEach>
	<input type="hidden" name="command" value="getRegisterAccountForm"/>
	<input type="submit" name='<fmt:message key="register.account.button.next"/>'/>
</form>
<form action="controller" method="get">
	<input type="hidden" name="command" value="backToIndex"/>
	<input type="submit" name='<fmt:message key="receipt.button.back.to.index"/>'/>
</form>
</body>
</html>