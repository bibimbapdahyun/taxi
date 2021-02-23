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
<title>Register Form</title>
</head>
<body>
${errorMessage}
<div class="conteiner-fluid container-background">
    <div class="col-12 col-sm-6 col-md-3 form-wrapper">
        <div class="car-form-container">
            <form action="controller" method="post">
                <div class="mb-3">
                    <label for="exampleTelephoneNumberReg" class="form-label"><fmt:message key="validate.order.account.login"/></label>
                    <input type="text" class="form-control" id="exampleTelephoneNumberReg" name="login" placeholder="(066) 665 86 73" required pattern="[0-9]{12}"/>
                </div>
                <div class="mb-3">
                    <label for="examplePasswdReg" class="form-label"><fmt:message key="register.manager.form.password"/></label>
                    <input type="password" class="form-control" id="examplePasswdReg" name="password" required pattern=".{3,15}"/>
                </div>
                <div class="mb-3">
                    <label for="exampleMailReg" class="form-label"><fmt:message key="register.manager.form.mail"/></label>
                    <input id="exampleMailReg" class="form-control" type="email" name="mail" placeholder="something@gmail.com"/> 
                </div>
                <div class="mb-3">
                    <label for="exampleNmaeReg" class="form-label"><fmt:message key="register.manager.form.name"/></label>
                    <input type="text" class="form-control" id="exampleNmaeReg" name="name" required placeholder="Ivan" pattern="[A-ZА-Я][a-zа-я]+"/>
                </div>
                <div class="mb-3">
                    <label for="exampleSurNmaeReg" class="form-label"><fmt:message key="register.manager.form.surname"/></label>
                    <input type="text" class="form-control" id="exampleSurNmaeReg" name="surname" placeholder="Ivanov" pattern="[A-ZА-Я][a-zа-я]+"/> 
                </div>
                
                <c:forEach var="gender" items="${genders}">
                    <div class="form-check-inline">
                        <input class="form-check-input" type="radio" name="g" value="${gender.name}" id="flexRadioDefault1" required>
                        <label class="form-check-label" for="flexRadioDefault1">
                            <fmt:message key="registerForm.gender.${gender.name}"/>
                        </label>
                    </div>
                </c:forEach>
                <input type="hidden" name="command" value="createAccount"/>
                <div class="col-12 form-btn-right shift-top">
                    <button class="btn btn-primary" type="submit"><fmt:message key="configuration.page.create.account"/></button>
                </div>
            </form>
            <form class="cancel-form" action="controller" method="get">
                <input type="hidden" name="command" value="backToIndex"/>
                <div class="col-12 form-btn-28">
                    <button class="btn btn-primary" type="submit"><fmt:message key="receipt.button.back.to.index"/></button>
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

