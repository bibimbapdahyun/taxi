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
<title>Info Order</title>
</head>
<body>
    <div class="conteiner-fluid container-background">
        <div class="col-12 col-sm-6 col-md-3 form-wrapper">
            <div class="validate-form-container">
                <c:choose>
                    <%-- <c:when test="${order.places >= 10 }">
                        ${placesMessage}
                        <form action="controller" method="POST">
                            <br /> <input type="hidden" name="command"
                            value="checkOutBigOrder"> <input type="submit"
                            value="Submit">
                        </form>
                    </c:when> --%>
                    <c:when test="${not empty ccp}">
                        <table class="table table-info">
                            <tr>
                                <th><fmt:message key="validate.order.account.login"/></th>
                                <td>${order.account.login}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="receipt.start=Start"/></th>
                                <td>${order.start}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="receipt.finish=Finish"/></th>
                                <td>${order.finish}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="receipt.places=Places"/></th>
                                <td>${order.places}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="validate.order.number"/></th>
                                <td>${order.type}</td>
                            </tr>
                            <tr><td>${message}</td></tr>
                        </table>
                        <c:forEach var="count" items="${ccp}">
                                <table class="table table-striped">
                                <tr>
                                    <td>
                                        <div>
                                            <input type="radio" name="count" value="${count.type.type}">
                                        </div>    
                                    </td>
                                    <th>${count.type.name}</th>
                                </tr>
                                <tr>
                                    <th><fmt:message key="validate.order.car.count"/></th>
                                    <td>${count.cars.size()}</td>
                                </tr>
                                <tr>
                                    <th><fmt:message key="receipt.price"/></th>
                                    <td>${count.price}</td>
                                </tr>
                            </table>
                        </c:forEach>
                    </c:when>
                    <c:when test="${empty Car && empty cpp}">
                        ${carsNotFound}
                    </c:when>
                    <c:when test="${not empty Car}">
                        <table class="table table-info">
                            <tr>
                                <th><fmt:message key="receipt.start"/></th>
                                <td>${order.start}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="receipt.finish"/></th>
                                <td>${order.finish}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="receipt.places"/></th>
                                <td>${order.places}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="statistics.format.order.type"/></th>
                                <td>${order.type.type}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="statistics.format.order.cars.number"/></th>
                                <td>${Car.carNumber}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="statistics.format.order.cars.mark"/></th>
                                <td>${Car.mark}</td>
                            </tr>
                            <tr>
                                <th><fmt:message key="receipt.price"/></th>
                                <td>${order.price}</td>
                            </tr>
                        </table>
                    </c:when>
                </c:choose>
                <form class="cancel-form" action="controller" method="post">
                    <input type="hidden" name=command value="createOrder"> 
                    <div class="form-btn-right">
                        <button class="btn btn-primary" type="submit"><fmt:message key="orderForm.button.set.order"/></button>
                    </div>
                </form>
                <form class="cancel-form" action="controller" method="post">
                    <input type="hidden" name="command" value="cancelOrder"> 
                    <div class="col-12 form-btn-top-53" >
                        <button class="btn btn-primary" type="submit"><fmt:message key="cancel.button"/></button>
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