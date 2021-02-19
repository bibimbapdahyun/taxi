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
		<fmt:message key="validate.order.account.login"/><input type="text" name="login" /><br> 
		<fmt:message key="register.manager.form.password"/><input type="password" name="password" /><br> 
		<fmt:message key="register.manager.form.name"/><input type="text" name="name" /><br> 
		<fmt:message key="register.manager.form.surname"/><input type="text" name="surname" /><br>
		<fmt:message key="register.manager.form.mail"/><input type="email" name="mail" /><br>
		<c:forEach var="role" items="${accountRoles}">
			<input type="radio" name="role" value="${role.role}" /> <fmt:message key="register.manager.form.${role.role}"/>
		</c:forEach>
		<br>
		<c:forEach var="gender" items="${genders}">
			<input type="radio" name="gender" value="${gender.name}" /><fmt:message key="registerForm.gender.${gender.name}"/>
		</c:forEach>
		<br> <input type="hidden" name="command" value="registerAccount" />
		<input type="submit" name='<fmt:message key="register.manager.form.button.register"/>'/>
	</form>
</body>
</html>