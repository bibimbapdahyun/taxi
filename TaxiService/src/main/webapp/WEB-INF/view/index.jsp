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
	<ul>
		<c:choose>
			<c:when test="${empty account}">
				<li><a href="controller?command=getRegisterForm"><fmt:message key="index.no.loged.registration"/></a></li>
				<li><a href="controller?command=getLoginForm"><fmt:message key="loginFormJsp.login"/></a></li>
			</c:when>
			<c:when test="${not empty account}">
				${account.login}
			</c:when>
		</c:choose>
		<c:if test="${sessionScope.account.role.role == 'driver'}">
			<li><a href="controller?command=getDriverActualOrder&page=1"><fmt:message key="index.driver.confirm.order"/></a></li>
			<li><a href="controller?command=changeAccountState"><fmt:message key="index.driver.change.state"/></a></li>
		</c:if>
		<c:if test="${sessionScope.account.role.role eq 'user'}">
			<li><a href="controller?command=makeOrder">Make Order</a></li>
		</c:if>
		<c:if test="${not empty sessionScope.account}">
			<form action="controller" method="post">
				<input type="hidden" name="command" value="logout" /> <br> 
				<input type="submit" value='<fmt:message key="index.button.logout"/>'/>
			</form>
		</c:if>
		<c:if test="${sessionScope.account.role.role == 'admin'}">
			<form action="controller" method="get">
				<input type="hidden" name="command" value="getConfigPage" /> <br>
				<input type="submit" value='<fmt:message key="index.admin.config"/>' />
			</form>
		</c:if>
		<c:if test="${sessionScope.account.role.role == 'manager'}">
			<form action="controller" method="get">
				<input type="hidden" name="command" value="getStatisticsOrder" /> <br>
				<input type="submit" value='<fmt:message key="index.manager.statistics"/>'/>
			</form>
		</c:if>
	</ul>
	
	
	<form id="settings_form" action="controller" method="get">
		<input type="hidden" name="command" value="updateLocale" />
			<div>
				<p><fmt:message key="index.locale"/></p>
				<select name="localeToSet">
					<c:choose>
						<c:when test="${not empty defaultLocale}">
							<option value="">${defaultLocale}[Default]</option>
						</c:when>
						<c:otherwise>
							<option value=""/>
						</c:otherwise>
					</c:choose>
					<c:forEach var="localeName" items="${locales}">
						<option value="${localeName}">${localeName}</option>							
					</c:forEach>
				</select>
			</div>
		<input type="submit" value='<fmt:message key="index.button.locale.update"/>'><br/>
	</form> 
	
</body>
</html>