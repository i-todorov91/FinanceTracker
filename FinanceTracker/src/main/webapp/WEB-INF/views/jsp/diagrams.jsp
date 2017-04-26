<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style>
	.panel{
		width: 400px;
		height: 200px;
	}
</style>
<body>
	<div class="panel panel-info">
	  <div class="panel-heading">
	    <h3 class="panel-title">${sessionScope.selectedBudget.getName()}</h3>
	  </div>
	  <div class="panel-body">
	    <h3>Total income: </h3>
	    <h3>Total expense: </h3>
	    <h3>Current balance: ${sessionScope.selectedBudget.getBalance()}</h3>
	  </div>
	</div>
</body>
</html>