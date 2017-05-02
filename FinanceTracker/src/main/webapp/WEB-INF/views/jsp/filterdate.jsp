<%@ page errorPage="error500.jsp" %>
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
  <style>
  	form{
  		display: inline-block;
  	}
  </style>
<body>
	<form id="typeForm" action="login/changetype" method="post">
		<select id="select" name="type" onchange='submit()'>
      		<option> ${sessionScope.selectedType}</option> 
	      	<c:forEach var="item" items="${sessionScope.types}">
	      		<c:if test="${!sessionScope.selectedType.equals(item.toString())}">
	      			<option>${item.toString()}</option>
	      		</c:if>
	      	</c:forEach> 
	    </select> 
	</form>
  	<form action="login/filterdate" method="post">
		<select name="category">
	      	<c:forEach var="item" items="${sessionScope.categories}">
	      		<option>${item.getName()}</option>
	      	</c:forEach>
	      	<c:if test="${sessionScope.selectedType.equals(sessionScope.types[0].toString())}">
	      		<c:forEach var="item" items="${sessionScope.incomeCategories}">
	      			<option>${item.getName()}</option>
	      		</c:forEach>
	      	</c:if>
	      	<c:if test="${sessionScope.selectedType.equals(sessionScope.types[1].toString())}">
	      		<c:forEach var="item" items="${sessionScope.expenseCategories}">
	      			<option>${item.getName()}</option>
	      		</c:forEach>
	      	</c:if>
	    </select>
		<label for="allCategories">All categories</label>
		<input type="checkbox" id="allCategories" name="allCategories"/>
		<input type="date" id="datepicker1" name="from" placeholder="from"/>
		<input type="date" id="datepicker" name="to" placeholder="to"/>
		<input type="submit" value="Go"/>
	</form>
	<br><br>
	<c:if test="${sessionScope.filteredData != null}">
		<form action="login/viewpdf" method="get">
			<input type="hidden" name="type" value="Cashflow"/>
			<input type="submit" value="Export as pdf">
		</form>
		<div style="height: 1150px; overflow-y: scroll;">
			<table class="table table-striped table-hover">
				<tr>
					<th>#</th>
					<th>Type</th>
					<th>Category</th>
					<th>Quantity(lv)</th>
					<th>Description</th>
					<th>Date</th>
				</tr>
				<c:set var="number" value="1"></c:set>    	
				<c:forEach items="${sessionScope.filteredData}" var="data">
					<tr>
						<td>${number}</td>
						<td>${data.type.toString()}</td>
						<td>${data.category.name}</td>
						<td>${data.quantity}</td>
						<td>${data.description}</td>
						<td>${data.date}</td>
						<c:set var="number" value="${number+1}"></c:set>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
</body>
</html>