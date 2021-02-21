<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
 	
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js" integrity="sha384-KsvD1yqQ1/1+IA7gi3P0tyJcT3vR+NdBTt13hSJ2lnve8agRGXTTyNaBYmCR/Nwi" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.min.js" integrity="sha384-nsg8ua9HAw1y0W1btsyWgBklPnCUAFLuTMS2G72MMONqmOymq585AcH49TLBQObG" crossorigin="anonymous"></script>
<title>Login</title>
</head>
<body>
	<c:if test="${not empty registerMessage}">
		${registerMessage}
	</c:if>
	<c:if test="${not empty loginMessage}">
		${loginMessage}
	</c:if>
	<form class="row g-3" action="controller" method="post">
		<input type="hidden" name="command" value="login" />
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