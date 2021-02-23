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

<link rel="stylesheet" href="../css/login.css">
<title>Insert title here</title>
</head>
<body>
    <div class="container-fluid container-background">
        <div class="col-12 col-sm-6 col-md-3 form-wrapper">
            <div class="form-container">
                <c:choose>
                    <c:when test="${not empty order}">
                        <table class="table table-striped">
                            <thead>
                                <th><fmt:message key="validate.order.number"/></th>
                                <th><fmt:message key="receipt.start"/></th>
                                <th><fmt:message key="receipt.finish"/></th>
                                <th><fmt:message key="statistics.format.order.date"/></th>
                                <th><fmt:message key="receipt.price"/></th>
                                <th><fmt:message key="statistics.format.order.state"/></th>
                                <th><fmt:message key="statistics.format.order.state"/>Action</th>
                            </thead>
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.start}</td>
                                <td>${order.finish}</td>
                                <td>${order.data}</td>
                                <td>${order.price}</td>
                                <td>${order.tState.name}</td>
                                <td>
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="finishOrder" /> 
                                        <div class="form-btn">
                                            <button class="btn btn-primary" type="submit"><fmt:message key="driver.finish.order"/>Finish</button>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                        </table>
                        
                    </c:when>
                    <c:when test="${not empty errorMessage}">
                        ${errorMessage}
                    </c:when>
                </c:choose>
                <c:if test="${not empty orders}">
                    <table class="table talbe-striped">
                        <thead>
                            <th><fmt:message key="validate.order.number"/>1</th>
                            <th><fmt:message key="receipt.start"/>2</th>
                            <th><fmt:message key="receipt.finish"/>3</th>
                            <th><fmt:message key="statistics.format.order.date"/>4</th>
                            <th><fmt:message key="receipt.price"/>5</th>
                            <th><fmt:message key="validate.order.account.login"/>6</th>
                            <th><fmt:message key="receipt.places"/>7</th>
                            <th><fmt:message key="statistics.format.order.state"/>8</th>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td>${order.id}</td>
                                    <td>${order.start}</td>
                                    <td>${order.finish}</td>
                                    <td>${order.data}</td>
                                    <td>${order.price}</td>
                                    <td>${order.account.login}</td>
                                    <td>${order.places}</td>
                                    <td>${order.tState.name}</td>
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
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="backToIndex" />
                    <div class="form-btn">
                        <button class="btn btn-primary" type="submit"><fmt:message key="receipt.button.back.to.index"/>Cancel</button>
                    </div>
                </form>
        </div>
    </div>
</div>

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