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
<title>Car Form</title>
</head>
<body>
<div class="conteiner-fluid">
    <div class="col-12 col-sm-6 col-md-3 form-wrapper">
        <div class="car-form-container">
            <form action="controller" method="post">
                <div class="mb-3">
                    <label for="exampleInputCarNumber2" class="form-label"><fmt:message key="car.form.number"/>Number</label>
                    <input class="form-control" id="exampleInputCarNumber2" type="text" name="number" placeholder="AA0000BB">
                </div>
                <div class="mb-3">
                    <fmt:message key="car.form.mark"/>
                    <label for="exampleInputCarMark2" class="form-label"><fmt:message key="car.form.mark"/>Mark</label>
                    <input class="form-control" id="exampleInputCarMark2" type="text" name="mark" placeholder="Dewo Lanos">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPlaces2" class="form-label"><fmt:message key="car.form.places"/>Places</label>
                    <input class="form-control" id="exampleInputPlaces2" type="number" name="place" placeholder="places">
                </div>
                <c:forEach var="type" items="${carTypes}">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" id="flexRadio1" type="radio" name="type" value="${type.type}">
                        <label class="form-check-label" for="flexRadio1">
                            <fmt:message key="car.form.type.${type.type}"/> type
                        </label>
                    </div>
                </c:forEach>
                <input type="hidden" name="command" value="registerCar">
                <div class="col-12 form-btn-right register-car-btn">
                    <button type="submit" class="btn btn-primary"><fmt:message key="car.form.button.create"/>Create</button>
                </div>
            </form>
            <form class="cancel-form" action="controller" method="post">
                <input type="hidden" name="command" value="backToIndex">
                <div class="col-12 form-btn">
                    <button type="submit" class="btn btn-primary"><fmt:message key="receipt.button.back.to.index"/>Cacnel</button>
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