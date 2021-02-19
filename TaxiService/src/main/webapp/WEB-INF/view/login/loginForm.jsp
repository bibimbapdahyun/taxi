<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<c:if test="${not empty registerMessage}">
		${registerMessage}
	</c:if>
	<c:if test="${not empty loginMessage}">
		${loginMessage}
	</c:if>
	<form action="controller" method="post">
		<input type="hidden" name="command" value="login"/>
		<fmt:message key="loginFormJsp.login"/>
		<input type="text" name="login"/><br>

		<fmt:message key="loginFormJsp.password"/>
		<input type="password" name="password"/><br>
		
		<input type="submit" value='<fmt:message key="loginFormJsp.button.login"/>'/>
	</form>
	<form action="controller" method="get">
		<input type="hidden" name="command" value="getIndexJsp"/>
		<input type="submit" value='<fmt:message key="cancel.button"/>'/>
	</form>
	<form action="controller" method="get">
		<input type="hidden" name="command" value="getRegisterForm"/>
		<input type="submit" value='<fmt:message key="loginFormJsp.button.register"/>'/>
	</form>
</body>
</html>