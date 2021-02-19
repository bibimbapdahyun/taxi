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
${errorMessage}
<form action="controller" method="post">
	<input type="text" name="login"/> <fmt:message key="validate.order.account.login"/>
	<br><input type="password" name="password"/> <fmt:message key="register.manager.form.password"/>
	<br><input type="email" name="mail"/> <fmt:message key="register.manager.form.mail"/>
	<br><input type="text" name="name"/> <fmt:message key="register.manager.form.name"/>
	<br><input type="text" name="surname"/> <fmt:message key="register.manager.form.surname"/>
	<br><c:forEach var="gender" items="${genders}">
		<input type="radio" name="g" value="${gender.name}"/><fmt:message key="registerForm.gender.${gender.name}"/>
	</c:forEach>
	<hr>	
	<input type="hidden" name="command" value="createAccount"/>
	<input type="submit" value='<fmt:message key="configuration.page.create.account"/>'/>
</form>
<hr>
<form action="controller" method="get">
	<input type="hidden" name="command" value="backToIndex"/>
	<input type="submit" value='<fmt:message key="receipt.button.back.to.index"/>'/>
</form>
</body>
</html>