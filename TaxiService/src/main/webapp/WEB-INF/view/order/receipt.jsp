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

<link rel="stylesheet"  href="../css/login.css">
<title>Receipt</title>
</head>
<body>
    <div class="conteiner-fluid container-background">
        <div class="col-12 col-sm-6 col-md-3 form-wrapper">
            <div class="form-container">
                <h3>Receipt {order.id} 5</h3>
                <div class="table-form">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Value</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <th scope="row"><fmt:message key="receipt.start"/>start</th>
                            <td>${order.start}</td>
                        </tr>
                        <tr>
                            <th scope="row"><fmt:message key="receipt.finish"/>finish</th>
                            <td> ${order.finish}</td>
                        </tr>
                        <tr>
                            <th scope="row"><fmt:message key="receipt.places"/>places</th>
                            <td> ${order.places}</td>
                        </tr>
                        <tr>
                            <th scope="row"><fmt:message key="receipt.price"/>price</th>
                            <td> ${order.price} 50$</td>
                        </tr>
                        <tr>
                            <th scope="row">type</th>
                            <td><fmt:message key="receipt.car.type.${order.type.type}"/></td></tr>
                        <tr>
                            <th scope="row"><fmt:message key="receipt.time"/>time</th>
                            <td>${time} m</td></tr>
                        </tr>
                        <c:choose>
                            <c:when test="${not empty Car && empty cars}">
                                <tr>   
                                    <th scope="row">number</th>
                                    <td>${Car.carNumber}</td>
                                </tr>
                                <tr>
                                    <th scope="row">mark</th> 
                                    <td>${Car.mark}</td>
                                </tr>
                            </c:when>
                            <c:when test="${empty Car && not empty cars}">
                                <c:forEach var="car" items="${cars}">
                                    <tr>
                                        <th scope="row">Car number</th>
                                        <td>${Car.carNumber}</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">Mark</th>
                                        <td>${Car.mark}</td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                    </tbody>
                </table>    
                <form class="cancel-form" action="controller" method="post">
                    <input type="hidden" name="command" value="backToIndex"/>
                    <div class="col-12 form-btn-10">
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