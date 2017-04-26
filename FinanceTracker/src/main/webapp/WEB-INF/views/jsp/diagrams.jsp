<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="../../dist/css/skins/_all-skins.min.css">
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
	    <h3>Total income: ${sessionScope.selectedBudget.getTotalIncome()}</h3>
	    <h3>Total expense: ${sessionScope.selectedBudget.getTotalExpense()}</h3>
	    <h3>Current balance: ${sessionScope.selectedBudget.getBalance()}</h3>
	  </div>
	  <canvas id="pieChart" style="height: 377px; width: 755px;" height="377" width="755"></canvas>
	</div>
	<script async="" src="//www.google-analytics.com/analytics.js"></script>
	<script src="../../plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="../../plugins/chartjs/Chart.min.js"></script>
	<script src="../../dist/js/app.min.js"></script>
	<script src="../../dist/js/app.min.js"></script>
	<script src="../../dist/js/demo.js"></script>	
</body>
</html>