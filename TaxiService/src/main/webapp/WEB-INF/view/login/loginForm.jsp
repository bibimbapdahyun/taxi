<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" 
        rel="stylesheet" 
        integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" 
        crossorigin="anonymous">
    
    <link rel="stylesheet" href="style/login.css">
    
</head>
<body>
<div class="container-fluid container-background">
    <div class="col-12 col-sm-6 col-md-3 form-wrapper">
        <div class="form-container">
            <c:if test="${not empty registerMessage}">${registerMessage}</c:if>
            <c:if test="${not empty loginMessage}">${loginMessage}</c:if>
            <form class="login-form" action="controller" method="POST">
                <input type="hidden" name="command" value="login">
                <div class="mb-3">
                    <label for="exampleInputTelephonNumber" class="form-label"><fmt:message key="loginFormJsp.login"/></label>
                    <input type="text" class="form-control" name="login" id="exampleInputTelephonNumber" required pattern="[0-9]{12}" placeholder="38 111 111 11 11">
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1" class="form-label"><fmt:message key="loginFormJsp.password"/></label>
                    <input type="password" class="form-control" name="password" id="exampleInputPassword1" required pattern=".{3,15}">
                </div>
                <div class="form-btn">
                    <button type="submit" class="btn btn-primary"><fmt:message key="loginFormJsp.login"/></button>
                </div>
            </form>
            <form class="cancel-form" action="controller" method="get">
                <input type="hidden" name="command" value="getIndexJsp"/>
                <div class="form-btn">
                    <button class="btn btn-primary" type="submit"><fmt:message key="cancel.button"/></button>
                </div>
            </form>
            <div class="form-btn-right">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="getRegisterForm"/>
                    <div class="col-12">
                        <button  class="btn btn-primary" type="submit"><fmt:message key="loginFormJsp.button.register"/></button>
                    </div>
                </form>
            </div>
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
</body>
</html>