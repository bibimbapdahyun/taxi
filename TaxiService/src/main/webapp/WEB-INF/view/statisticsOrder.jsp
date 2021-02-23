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
<link rel="stylesheet" href="style/login.css">
<title>Statistics</title>
</head>
<body>
	<div class="form-inline">
		<c:if test="${not empty errorMessage}">
			<fmt:message key="statistics.error.message"/>${errorMessage}
		</c:if>
		<form action="controller" method="get">
			<input type="hidden" name="command" value="getStatistics" />
			<input type="hidden" name="page" value="1"/>
			<div class="col-12">
				<button class="btn btn-primary" type="submit"><fmt:message key="index.manager.statistics"/></button>
			</div>
		</form>
		<form action="controller" method="get">
			<div class="col-12">
				<div class="form-check form-check-inline">
					<label class="form-check-label" for="up1"><fmt:message key="statistics.up"/></label>
					<input type="radio" name="sort" value="up" id="up1" class="form-check-input" checked/>
				</div>
				<div class="form-check form-check-inline">
					<label class="form-check-label" for="down1"></label>
					<input class="form-check-input" id="down1" type="radio" name="sort" value="down"><fmt:message key="statistics.down"/>
				</div>
				<input type="hidden" name="page" value="1"/>
				<input type="hidden" name="command" value="sortByDate"/>
				<button class="btn btn-primary" type="submit"><fmt:message key="statistics.sort.by.date"/></button>
			</div>
		</form>
		<form action="controller" method="get">
			<div class="col-12">
				<div class="form-check form-check-inline">
					<label class="form-check-label" for="up2"><fmt:message key="statistics.up"/></label>
					<input type="radio" name="sort" value="up" id="up2" class="form-check-input" checked/>
				</div>
				<div class="form-check form-check-inline">
					<label class="form-check-label" for="down2"></label>
					<input class="form-check-input" id="down2" type="radio" name="sort" value="down"><fmt:message key="statistics.down"/>
				</div>
				<input type="hidden" name="page" value="1"/>
				<input type="hidden" name="command" value="sortByPrice" /> 
				<button class="btn btn-primary" type="submit"><fmt:message key="statistics.sort.by.price"/></button>
			</div>
		</form>
		<form action="controller" method="get">
			<div class="mb-3 input-group">
				<div class="input-group-prepend">
					<button class="btn btn-primary" type="submit"><fmt:message key="statistics.filter.by.date"/></button>
				</div>
				<div class="col-10">
					<label for="data" class="col-2">
					<input class="form-control" id="data" type="date" name="startDate" required/>
				</div>
			</div>
			<input type="hidden" name="command" value="filterByDate" /> 
			<input type="hidden" name="page" value="1"/>
		</form>
		<form action="controller" method="get">
			<div class="mb-3 input-group">
				<div class="input-group-prepend">
					<button class="btn btn-primary" type="submit"><fmt:message key="statistics.filter.by.account"/></button>
				</div>
				<div class="col-10">
					<label for="login" class="col-2">
					<input class="form-control" id="login" type="text" name="login" required/>
				</div>
			</div>
			<input type="hidden" name="command" value="filterByAccount" /> 
			<input type="hidden" name="page" value="1"/>
		</form>
	</div>
	<c:if test="${not empty orders}">
		<table class="table table-striped">
			<thead>
				<th></th>
				<th><fmt:message key="validate.order.number"/></th>
				<th><fmt:message key="orderForm.start"/></th>
				<th><fmt:message key="orderForm.finish"/></th>
				<th><fmt:message key="statistics.format.order.date"/></th>
				<th><fmt:message key="receipt.price"/></th>
				<th><fmt:message key="validate.order.account.login"/></th>
				<th><fmt:message key="orderForm.places"/></th>
				<th><fmt:message key="statistics.format.order.state"/></th>
				<th><fmt:message key="statistics.format.order.type"/></th>
			</thead>
			<tbody>
				<c:forEach var="order" items="${orders}">
					<tr>
						<th></th>
						<td>${order.id}</td>
						<td>${order.start}</td>
						<td>${order.finish}</td>
						<td>${order.data}</td>
						<td>${order.price}</td>
						<td>${order.account.login}</td>
						<td>${order.places}</td>
						<td>${order.tState.name}</td>
						<td>${order.type.type}</td>
					</tr>
					<tr>
						<th><fmt:message key="statistics.cars"></fmt:message> </th>
						<c:forEach var="c" items="${order.car}">
							<td>${c.carNumber}</td>
							<td>${c.mark}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>

	<nav aria-label="Page naviagation example">
		<ul class="pagination">
		<c:choose>
			<c:when test="${page > 1}">
					<li class="page-item">
						<a class="page-link" href="controller?command=${cmd}&page=${page - 1}" aria-label="Previous">
							<span aria-hidden="true">&laquo</span>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item disabled">
						<a class="page-link" href="controller?command=${cmd}&page=${page - 1}" aria-label="Previous">
							<span aria-hidden="true">&laquo</span>
						</a>
					</li>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${page < pageCount}">
					<li class="page-item">
						<a class="page-link" href="controller?command=${cmd}&page=${page + 1}" aria-label="Previous">
							<span aria-hidden="true">&raquo</span>
						</a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="page-item disabled">
						<a class="page-link" href="controller?command=${cmd}&page=${page + 1}" aria-label="Previous">
							<span aria-hidden="true">&raquo</span>
						</a>
					</li>
				</c:otherwise>
			</c:choose>
		</ul>    
	</nav>
	<!-- <c:choose>
		<c:when test="${page > 1}">
			<a href="controller?command=${cmd}&page=${page - 1}"><fmt:message key="statistics.page.prev"/></a>
		</c:when>
		<c:otherwise>
			<fmt:message key="statistics.page.prev"/>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${page < pageCount}">
			<a href="controller?command=${cmd}&page=${page + 1}"><fmt:message key="statistics.page.next"/></a>
		</c:when>
		<c:otherwise>
			<fmt:message key="statistics.page.next"/>
		</c:otherwise>
	</c:choose> -->

	<form action="controller" method="post">
		<input type="hidden" name="command" value="backToIndex">
		<div class="col-12">
			<button class="btn btn-primary" type="submit"><fmt:message key="receipt.button.back.to.index"/></button>
		</div>
	</form>
	
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