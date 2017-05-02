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
<body>
	<c:if test="${sessionScope.budgets.isEmpty()}">
		<h1>You have no budgets!</h1>
	</c:if>
	
	<c:if test="${!sessionScope.budgets.isEmpty()}">
		<form action="login/createpdf" method="get">
			<input type="hidden" name="type" value="Account"/>
			<input type="submit" value="Export as pdf">
		</form>
		<div style="height: 1150px; overflow-y: scroll;">
			<table class="table table-striped table-hover">
				<tr>
					<th>#</th>
					<th>Name</th>
					<th>Incomes(lv)</th>
					<th>Expenses(lv)</th>
					<th>Balance(lv)</th>
				</tr>
				<c:set var="number" value="1"></c:set>
				<c:forEach items="${sessionScope.budgets}" var="data">
					<tr>
						<td>${number}</td>
						<td>${data.key}</td>
						<td>${data.value.getTotalIncome()}</td> 
						<td>${data.value.getTotalExpense()}</td>
						<td>${data.value.getBalance()}</td>
						<c:set var="number" value="${number+1}"></c:set>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:if>
</body>
</html>