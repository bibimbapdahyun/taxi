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
	<c:if test="${not empty registerMessage}">
		<ul>
			<li> ${registerMessage}</li>
		</ul>
	</c:if>
	<form action="controller" method="POST">
		
		<fmt:message key="registerForm.login"/>
		<input type="text" name="login" placeholder="(066) 665 86 73"/><br>

		<fmt:message key="registerForm.password"/>
		<input type="password" name="password"/><br>

		<fmt:message key="registerForm.name"/>
		<input type="text" name="name"/>
		
		
		<c:forEach var="gender" items="${genders}">
			<input type="radio" name="gender" value="${gender.name}"/> <fmt:message key="registerForm.gender.${gender.name}"/>
		</c:forEach><br>
		
		
		<input type="hidden" name="command" value="registerUser"/>
		<input type="submit" value='<fmt:message key="registerForm.button.register"/>'/>
	</form>
</body>
</html>