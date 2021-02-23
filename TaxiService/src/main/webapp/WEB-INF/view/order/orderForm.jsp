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
<title>Create Order</title>
</head>
<body>
<div class="container-fluid container-background">
    <div class="col-12 col-sm-6 col-md-3 form-wrapper">
        <div class="car-form-container">

            <form action="controller" method="POST">
                <div class="mb-3">
                    <label for="exampleOrderStart" class="form-label"><fmt:message key="orderForm.start"/></label>
                    <input class="form-control" id="exampleOrderStart" type="text" name="from" placeholder="Start address" required pattern=".{5, 30}"/>
                </div>
                <div class="mb-3">
                    <label for="exampleOrderFinish" class="form-label"><fmt:message key="orderForm.finish"/></label>
                    <input class="form-control" id="exampleOrderFinish"  type="text" name="to" placeholder="End address" required pattern=".{5, 30}"/>
                </div>
                <div class="mb-3">
                    <label for="exampleOrderPlaces" class="form-label"><fmt:message key="orderForm.places"/></label>
                    <input id="exampleOrderPlaces" class="form-control" type="number" name="places" placeholder="Count of places" required/>
                </div>
                
                <c:forEach var="type" items="${carTypes}">
                    <div class="form-check form-check-inline">
                        <input id="radioType" class="form-check-input" type="radio" name="type" value="${type.type}" required checked/> 
                        <label class="form-check-label" for="radioType">
                            <fmt:message key="orderForm.car.type.${type.type}"/>
                        </label>
                    </div>
                </c:forEach>
                <input type="hidden" name="command" value="setOrder"/>
                <div class="col-12 form-btn-right shift-top">
                    <button class="btn btn-primary" type="submit"><fmt:message key="orderForm.button.set.order"/></button>
                </div>
            </form>
            <form class="cancel-form" action="controller" method="post">
                <input type="hidden" name="command" value="backToIndex">
                <div class="col-12 form-btn-28">
                    <button type="submit" class="btn btn-primary"><fmt:message key="receipt.button.back.to.index"/></button>
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