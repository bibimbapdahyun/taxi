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
<div class="conteiner-fluid container-background">
    <div class="col-12 col-sm-6 col-md-3 form-wrapper">
        <div class="form-container">
            <c:if test="${not empty registerMessage}">${registerMessage}</c:if>
            <form action="controller" method="POST">
                <div class="mb-3">
                    <label for="exampleTelephoneNumberReg" class="form-label"><fmt:message key="registerForm.login"/>Login</label>
                    <input type="text" class="form-control" id="exampleTelephoneNumberReg" name="login" placeholder="(066) 665 86 73" required pattern="[0-9]{12}"/><br>
                </div>
                <div class="mb-3">
                    <label for="examplePasswdReg" class="form-label"><fmt:message key="registerForm.password"/>Password</label>
                    <input type="password" class="form-control" id="examplePasswdReg" name="password" required pattern="[A-Za-z0-9]{8,15}"/><br>
                </div>
                <div class="mb-3">
                    <label for="exampleNmaeReg" class="form-label"><fmt:message key="registerForm.name"/>Name</label>
                    <input type="text" class="form-control" id="exampleNmaeReg" name="name" required placeholder="Ivan" pattern="[A-Za-zА-Яа-яЁё]{3,15}"/>
                </div>
                
                <c:forEach var="gender" items="${genders}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="gender" value="${gender.name}" id="flexRadioDefault1" required>
                        <label class="form-check-label" for="flexRadioDefault1">
                            <fmt:message key="registerForm.gender.${gender.name}"/> Gender
                        </label>
                    </div>
                    <!-- <input type="radio" name="gender" value="${gender.name}"/> <fmt:message key="registerForm.gender.${gender.name}"/> -->
                </c:forEach><br>
                
                <input type="hidden" name="command" value="registerUser"/>
                <div class="col-12 form-btn-right">
                    <button type="submit" class="btn btn-primary" ><fmt:message key="registerForm.button.register"/>Register</button>
                </div>
            </form>
            <form class="cancel-form" action="controller" method="GET">
                <input type="hidden" name="command" value="backToIndex">
                <div class="col-12 form-btn">
                    <button type="submit" class="btn btn-primary" ><fmt:message key="cancel.button"/>Back</button>
                </div>
            </form>
            <form class="login-form" action="controller" method="GET">
                <!-- Check command value-->
                <input type="hidden" name="command" value="getLoginForm">
                <div class="col-12 form-btn">
                    <button type="submit" class="btn btn-primary"><fmt:message key="cancel.button"/>Login</button> 
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