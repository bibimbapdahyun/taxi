<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Configuration</title>
</head>
<body>
<form action="controller" method="get">
	<input type="hidden" name="command" value="registerAccount">
	<input type="submit" value='<fmt:message key="configuration.page.create.account"/>'>
</form>
<form action="controller" method="get">
	<input type="hidden" name="command" value="getStatistics">
	<input type="hidden" name="page" value="1">
	<input type="submit" value='<fmt:message key="configuration.page.get.statistics"/>'>
</form>
<hr>
<form action="controller" method="get">
		<input type="hidden" name="command" value="backToIndex">
		<input type="submit" value='<fmt:message key="receipt.button.back.to.index"/>'>
</form>
</body>
</html>