<%@ page errorPage="error500.jsp" %>
<%@page import="com.ft.model.budget.flows.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256" import="java.io.IOException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	h3, select{
		display: inline-block;
	}
</style>
</head>
<body>
	<c:if test="${sessionScope.budgets.isEmpty()}">
		<h1>You have no budgets!</h1>
	</c:if> 
	<c:if test="${!sessionScope.budgets.isEmpty()}"> 
		<div style="height: 1150px; overflow-y: scroll;">
			<form action="login/removebudget" method="post">
				<h3>Select budget: </h3>
				<select name="budgetName">
					<c:forEach var="budget" items="${sessionScope.budgets}">
						<option>${budget.key}</option>
					</c:forEach>
				</select> 
				<input type="submit" value="Remove budget"/>
			</form>
		</div>
	</c:if>
</body>
</html>