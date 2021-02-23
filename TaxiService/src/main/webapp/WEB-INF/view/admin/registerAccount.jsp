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
<div class="conteiner-fluid">
    <div class="col-12 col-sm-6 col-md-3 form-wrapper">
        <div class="car-form-container">
            <div class="btn-distance">
                <fmt:message key="register.account.choose.role"/>
                <form action="controller" method="post">
                    <c:forEach var="role" items="${accountRoles}">
                        <input class="form-check-input radio-font-size" type="radio" name="role" value="${role.role}" id="radioRole1" required/>
                        <label class="radio-font-size form-check-label" for="radioRole1">
                            <fmt:message key="register.account.role.${role.role}"/>Role
                        </label>
                    </c:forEach>
                
                    <input type="hidden" name="command" value="getRegisterAccountForm"/>
                    
                    <div class="mb-3 form-btn-right register-car-btn">
                        <button class="btn btn-primary " type="submit"><fmt:message key="register.account.button.next"/>Register</button>
                    </div>
                </form>
                <form class="cancel-form" action="controller" method="GET">
                    <input type="hidden" name="command" value="backToIndex">
                    <div class="col-12 form-btn width-btn">
                        <button type="submit" class="btn btn-primary" ><fmt:message key="cancel.button"/>Back</button>
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
</html>