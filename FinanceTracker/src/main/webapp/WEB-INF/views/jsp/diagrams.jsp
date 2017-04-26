<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/bootstrap.css">
<title>Insert title here</title>
</head>
<style>
	.panel{
		width: 400px;
		height: 200px;
	}
	#chartIncomes, #chartExpenses{
		display: inline-block;
	}
</style>
<script type="text/javascript">
window.onload = function () {
	var incomes = new CanvasJS.Chart("chartIncomes",
	{
		title:{
			text: "Incomes"
		},
		data: [
		{
			type: "pie",
			dataPoints: [
				{ y: 4181563},
				{ y: 2175498},
				{ y: 3125844},
				{ y: 1176121},
				{ y: 1727161},
				{ y: 4303364},
				{ y: 1717786}
			]
		}
		]
	});
	incomes.render();
	
	var expenses = new CanvasJS.Chart("chartExpenses",
			{
				title:{
					text: "Expenses"
				},
				data: [
				{
					type: "pie",
					dataPoints: [
						{ y: 4181563},
						{ y: 2175498},
						{ y: 3125844},
						{ y: 1176121},
						{ y: 1727161},
						{ y: 4303364},
						{ y: 1717786}
					]
				}
				]
			});
			expenses.render();
}
	</script>
	<script src="js/chart/canvasjs.min.js"></script>
<body>
	<div class="panel panel-info">
	  <div class="panel-heading">
	    <h3 class="panel-title">${sessionScope.selectedBudget.getName()}</h3>
	  </div>
	  <div class="panel-body">
	    <h4>Total income: ${sessionScope.selectedBudget.getTotalIncome()}</h4>
	    <h4>Total expense: ${sessionScope.selectedBudget.getTotalExpense()}</h4>
	    <h4>Current balance: ${sessionScope.selectedBudget.getBalance()}</h4>
	  </div>
	<div id="chartIncomes" style="height: 400px; width: 400px;"></div>	
	<div id="chartExpenses" style="height: 400px; width: 400px;"></div>
	</div>
</body>
</html>