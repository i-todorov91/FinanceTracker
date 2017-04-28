<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
  <script type="text/javascript"> 
  $(document).ready(function() {
    $("#datepicker").datepicker();
    $("#datepicker1").datepicker();
  });
  </script>
<body>
	<form action="login/filterdate" method="post">
		<input type="date" id="datepicker1" name="from" placeholder="from"/>
		<input type="date" id="datepicker" name="to" placeholder="to"/>
		<input type="submit" value="Go"/>
	</form>
	<c:if test="${sessionScope.filteredData != null}">
		<br>
		<c:forEach var="cashflow" items="${sessionScope.filteredData}">
			<h4>Type: ${cashflow.getType().toString()}, Quantity: ${cashflow.getQuantity()} Leva, Description: ${cashflow.getDescription()}</h4>
		</c:forEach>
	</c:if>
</body>
</html>