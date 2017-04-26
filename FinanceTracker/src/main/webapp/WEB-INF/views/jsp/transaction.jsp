<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    
  <script>
  $(document).ready(function() {
    $("#datepicker").datepicker();
  });
  </script>
<body>
	<form class="form-horizontal" method="post" action="login/addtransaction">
	  <fieldset>
	    <div class="form-group">
	      <label for="inputEmail" class="col-lg-2 control-label">Type</label>
	      <div class="col-lg-10">
	      	<select name="type">
		      	<c:forEach var="item" items="${sessionScope.types}">
		      		<option>${item}</option>
		      	</c:forEach>
		    </select>
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="inputPassword" class="col-lg-2 control-label">Quantity</label>
	      <div class="col-lg-10">
	        <input class="form-control" id="inputNumber" placeholder="Quantity" name="quantity" type="number" min="0">
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="inputPassword" class="col-lg-2 control-label">Date</label>
	      <div class="col-lg-10">
                <input id="datepicker" />
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="inputEmail" class="col-lg-2 control-label">Category</label>
	      <div class="col-lg-10">
	      	<select name="category">
		      	<c:forEach var="item" items="${sessionScope.categories}">
		      		<option>${item.getName()}</option>
		      	</c:forEach>
		    </select>
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