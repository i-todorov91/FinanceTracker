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
</head>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<body>
	  <fieldset>
	      	<form class="form-horizontal" id="typeForm" action="login/changetype" method="post">
			   <div class="form-group">
			     <label for="inputEmail" class="col-lg-2 control-label">Type</label>
			     <div class="col-lg-10">
		      	<select id="select" name="type" onchange='submit()'>
		      	<option> ${sessionScope.selectedType}</option> 
			      	<c:forEach var="item" items="${sessionScope.types}">
			      		<c:if test="${!sessionScope.selectedType.equals(item.toString())}">
			      			<option>${item.toString()}</option>
			      		</c:if>
			      	</c:forEach>
			    </select>
		      </div>
		    </div>
			</form>
		<form class="form-horizontal" method="post" action="login/addtransaction">
	    <div class="form-group">
	      <label for="inputPassword" class="col-lg-2 control-label">Quantity</label>
	      <div class="col-lg-10">
	        <input class="form-control" id="inputNumber" placeholder="Quantity" name="quantity" type="number" min="0">
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="inputPassword" class="col-lg-2 control-label">Description</label>
	      <div class="col-lg-10">
	        <input class="form-control" id="inputText" placeholder="Description" name="description" type="text">
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="inputPassword" class="col-lg-2 control-label">Date</label>
	      <div class="col-lg-10">
                <input id="datepicker" name="date"/>
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="inputEmail" class="col-lg-2 control-label">Category</label>
	      <div class="col-lg-10">
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
			<a href="login/addcategory" class="btn btn-primary">New category</a>
	      </div>
	    </div>
	    <div class="form-group">
	      <div class="col-lg-10 col-lg-offset-2">
	        <button type="reset" class="btn btn-default">Cancel</button>
	        <button type="submit" class="btn btn-primary">Submit</button>
	      </div>
	    </div>
	  </fieldset>
	</form>
</body>
</html>