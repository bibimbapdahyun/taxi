<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" 
    rel="stylesheet" 
    integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" 
    crossorigin="anonymous">
    
<link rel="stylesheet" href="style/login.css" type="text/css">
<title>Insert title here</title>
</head>
<body>
	<div class="container-fluid container-background">
		<div class="col-12 col-sm-6 col-md-3 form-wrapper">
			<div class="form-container">
				<c:choose>
					<c:when test="${empty account}">
						<form action="controller" method="GET">
							<input type="hidden" name="command" value="getRegisterForm">
							<div class="col-12">
								<button class="btn btn-primary" type="submit"><fmt:message key="index.no.loged.registration"/></button>
							</div>
						</form>	
						<form action="controller" method="GET">
							<input type="hidden" name="command" value="getLoginForm">
							<div class="col-12">
								<button class="btn btn-primary" type="submit"><fmt:message key="loginFormJsp.login"/></button>
							</div>
						</form>	
					</c:when>
					<c:when test="${not empty account}">
						${account.login}
					</c:when>
				</c:choose>
				<c:if test="${sessionScope.account.role.role == 'driver'}">
					<form action="controller" method="GET">
						<input type="hidden" name="command" value="getDriverActualOrder">
						<input type="hidden" name="page" value="1">
						<div class="col-12">
							<button class="btn btn-primary" type="submit"><fmt:message key="index.driver.confirm.order"/></button>
						</div>
					</form>	
					<form action="controller" method="GET">
						<input type="hidden" name="command" value="changeAccountState">
						<div class="col-12">
							<button class="btn btn-primary" type="submit"><fmt:message key="index.driver.change.state"/></button>
						</div>
					</form>	
				</c:if>
				<c:if test="${not empty sessionScope.account}">
					<form action="controller" method="GET">
						<input type="hidden" name="command" value="logout">
						<div class="col-12">
							<button class="btn btn-primary" type="submit"><fmt:message key="index.button.logout"/></button>
						</div>
					</form>
				</c:if>
				<c:if test="${sessionScope.account.role.role eq 'user'}">
					<form action="controller" method="GET">
						<input type="hidden" name="command" value="makeOrder">
						<div class="col-12">
							<button class="btn btn-primary" type="submit"><fmt:message key="index.make.order"/></button>
						</div>
					</form>	
				</c:if>
				<c:if test="${sessionScope.account.role.role == 'admin'}">
					<form action="controller" method="GET">
						<input type="hidden" name="command" value="getConfigPage">
						<div class="col-12">
							<button class="btn btn-primary" type="submit"><fmt:message key="index.admin.config"/></button>
						</div>
					</form>	
				</c:if>
				<c:if test="${sessionScope.account.role.role == 'manager'}">
					<form action="controller" method="GET">
						<input type="hidden" name="command" value="getStatisticsOrder">
						<div class="col-12">
							<button class="btn btn-primary" type="submit"><fmt:message key="index.manager.statistics"/></button>
						</div>
					</form>	
				</c:if>
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
					<div class="col-12">
						<button class="btn btn-primary" type="submit"><fmt:message key="index.button.locale.update"/></button><br/>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%-- <p><fmt:message key="index.locale"/>Locale</p>
	<div class="btn-group">
		<button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
			Locale
		</button>
		<ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
			<c:forEach var="localeName" items="${locales}">
				<a class="dropdown-item" href="controller?command=updateLocale&localeName=${localeName}">${localeName}Locale</a>
			</c:forEach>
		</ul> 
	</div> --%>
	

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" 
    integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" 
    crossorigin="anonymous"></script>
    
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.6.0/dist/umd/popper.min.js" 
    integrity="sha384-KsvD1yqQ1/1+IA7gi3P0tyJcT3vR+NdBTt13hSJ2lnve8agRGXTTyNaBYmCR/Nwi" 
    crossorigin="anonymous"></script>
    
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.min.js"
    integrity="sha384-nsg8ua9HAw1y0W1btsyWgBklPnCUAFLuTMS2G72MMONqmOymq585AcH49TLBQObG" 
    crossorigin="anonymous"></script>
</body>
</html>