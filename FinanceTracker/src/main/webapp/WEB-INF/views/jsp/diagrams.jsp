<%@ page errorPage="error500.jsp" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ft.model.budget.flows.Category"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.ft.model.budget.flows.CashFlow"%>
<%@page import="com.ft.model.budget.flows.Income"%>
<%@page import="com.ft.model.budget.Budget"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256" import="java.io.IOException"%>
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
	#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
		width: 450px;
		height: 450px;
		display: inline-block;
	}
	.row{
		width: 1500px;
	}
	
	#monthlyCashflow{
		width: 1000px;
		height: 500px;
	}
	
	@media only screen and (max-width: 400px) {
		#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
			display: block;
		}
		
		.panel{
			width: 250px;
			height: 250px;
		}
		
		#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
			width: 250px;
			height: 250px;
		}
		
		#monthlyCashflow{
			width: 250px;
			height: 250px;
		}
		
	}
	@media only screen and (max-width: 430px) {
		#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
			display: block;
		}
		
		.panel{
			width: 250px;
			height: 250px;
		}
		
		#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
			width: 250px;
			height: 250px;
		}
		
		#monthlyCashflow{
			width: 250px;
			height: 250px;
		}
		
	}
	@media only screen and (min-width: 431px) and (max-width: 767px) {
		#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
			display: block;
		}
		
		.panel{
			width: 450px;
			height: 200px;  
		}
		
		#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
			width: 470px;
			height: 350px;
			margin-bottom: 10px;
		}
		
		#monthlyCashflow{
			width: 470px;
			height: 350px;
		}
		
	}
	@media only screen and (min-width: 768px) and (max-width: 1300px) {
		.panel{
			margin-left: 50px;   
		}
		#chartIncomes, #chartExpenses, #chartIncomeVsExpense{
			display: block;
			margin-bottom: 10px;
		}
		#monthlyCashflow{
			width: 700px;
			height: 350px;
		}
	}
</style>
<script type="text/javascript">	
window.onload = function () {
	<% if(session.getAttribute("selectedBudget") != null){
		Budget budget = (Budget) session.getAttribute("selectedBudget");
		NumberFormat formatter = new DecimalFormat("#.00");
	%>
	var incomes = new CanvasJS.Chart("chartIncomes",
	{
		theme: "theme2",
		title: {
			text: "Income Categories"
		},
		exportFileName: "income-categories",
		exportEnabled: true,
		animationEnabled: true,
		legend: {
			verticalAlign: "bottom",
			horizontalAlign: "center"
		},
		data: [
		{
			type: "pie",
			showInLegend: true,
			toolTipContent: "{legendText}: <strong>{y}%</strong>",
			indexLabel: "{label} {y}%",
			dataPoints: [
			<% 
				double allIncomes = budget.getTotalIncome();
				for(Entry<Category, Double> i : budget.getIncomesCategory().entrySet()){
			%>
				{ y: <%= formatter.format((i.getValue() / allIncomes) * 100) %>, legendText: '<%= i.getKey().getName() %>', indexLabel: "<%= i.getKey().getName() %>"},
			<%
				}
				
			%>
			]
		}
		]
	});

	incomes.render();
	
	var expenses = new CanvasJS.Chart("chartExpenses",
		{
			theme: "theme2",
			title: {
				text: "Expense Categories"
			},
			exportFileName: "expense-categories",
			exportEnabled: true,
			animationEnabled: true,
			legend: {
				verticalAlign: "bottom",
				horizontalAlign: "center"
			},
			data: [
			{
				type: "pie",
				showInLegend: true,
				toolTipContent: "{legendText}: <strong>{y}%</strong>",
				indexLabel: "{label} {y}%",
				dataPoints: [
				<% 
					double allExpenses = budget.getTotalExpense();
					for(Entry<Category, Double> i : budget.getExpenseCategory().entrySet()){
				%>
				{ y: <%= formatter.format((i.getValue() / allExpenses) * 100) %>, legendText: '<%= i.getKey().getName() %>', indexLabel: "<%= i.getKey().getName() %>"},
				<%
					}
					
				%>
				]
			}
			]
		});
		expenses.render();
	
	var incomeVsExpense = new CanvasJS.Chart("chartIncomeVsExpense",
			{
				theme: "theme2",
				title: {
					text: "Income/Expense"
				},
				exportFileName: "income-expense",
				exportEnabled: true,
				animationEnabled: true,
				legend: {
					verticalAlign: "bottom",
					horizontalAlign: "center"
				},
				data: [
				{
					type: "pie",
					showInLegend: true,
					toolTipContent: "{legendText}: <strong>{y}%</strong>",
					indexLabel: "{label} {y}%",
					dataPoints: [
						<% 
							double totalIncome = budget.getTotalIncome();
							double totalExpense = budget.getTotalExpense();
							double all = totalIncome + totalExpense;
							double incomeResult = (all == 0) ? 0 : (totalIncome / all) * 100;
							double expenseResult = (all == 0) ? 0 : (totalExpense / all) * 100;
						%>
							{ y: <%= formatter.format(incomeResult) %>, legendText: "Incomes", indexLabel: "Incomes"},
							{ y: <%= formatter.format(expenseResult) %>, legendText: "Expenses", indexLabel: "Expenses"},
					]
				}
				]
			});

	incomeVsExpense.render();
			
	var cashflow = new CanvasJS.Chart("monthlyCashflow", {
		title: {
			text: "Monthly Cashflow"
		},
		exportEnabled: true,
		axisY: { 
			includeZero: true,
			title: "Money (Leva)"
		},
		axisX: {
			includeZero: false,
			interval: 1,
			title: "Months"
			},
		data: [{
			type: "column",
			dataPoints: [
			<% 
					for(int i = 1; i <= 12; i++){
				%>
					{ x: <%= i %> , y: <%= budget.getIncomeForMonth(i) %>},
				<%
					}
			%>
			]
		}, {
			type: "column",
			dataPoints: [
			<% 
					for(int i = 1; i <= 12; i++){
				%>
					{ x: <%= i %>, y: <%= budget.getExpenseForMonth(i) %>},
				<%
					}
			%>
			]
		}]
	});
	cashflow.render();
	
<% } %>
}
	</script>
	<script type="text/javascript" src="js/chart/canvasjs.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://code.jquery.com/ui/1.12.0-beta.1/jquery-ui.min.js"></script>
<body>  
	  <c:if test="${sessionScope.selectedBudget == null}">
	  	<h3>No budget's added</h3>
	  </c:if>
	  
	  <c:if test="${sessionScope.selectedBudget != null}">
		<div class="panel panel-info">
		  <div class="panel-heading">
		    <h3 class="panel-title">${sessionScope.selectedBudget.getName()}</h3>
		  </div>
		  <div class="panel-body">
		    <h4>Total income: ${sessionScope.selectedBudget.getTotalIncome()} Leva</h4>
		    <h4>Total expense: ${sessionScope.selectedBudget.getTotalExpense()} Leva</h4>
		    <h4>Current balance: ${sessionScope.selectedBudget.getBalance()} Leva</h4>
		  </div>
		</div>
		<form action="login/createpdf" method="get">
			<input type="hidden" name="type" value="Budget"/>
			<input type="submit" value="Export as pdf">
		</form>
		<div id="chartIncomes"></div>	
		<div id="chartExpenses"></div>
		<div id="chartIncomeVsExpense"></div>
		<div id="monthlyCashflow"></div>
	  </c:if>
</body>
</html>
