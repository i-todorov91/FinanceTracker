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
	#chartIncomes, #chartExpenses{
		display: inline-block;
	}
</style>
<script type="text/javascript">	
window.onload = function () {
	<% 
		if(session.getAttribute("selecteBudget") != null){
			Budget budget = (Budget) session.getAttribute("selectedBudget");
	%>
		var incomes = new CanvasJS.Chart("chartIncomes",
		{
			theme: "theme2",
			title:{
				text: "Incomes"
			},
			data: [
			{
				type: "pie",
				showInLegend: true,
				toolTipContent: "{y} - #percent %",
				legendText: "{indexLabel}",
				dataPoints: [
					<% 
						for(CashFlow i : budget.getIncomes()){
					%>
						{ y: <%= i.getQuantity() %>, indexLabel: "<%= i.getDescription() %>"},
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
					title:{
						text: "Expenses"
					},
					data: [
					{
						type: "pie",
						showInLegend: true,
						toolTipContent: "{y} - #percent %",
						legendText: "{indexLabel}",
						dataPoints: [
							<% 
								for(CashFlow i : budget.getExpenses()){
							%>
								{ y: <%= i.getQuantity() %>, indexLabel: "<%= i.getDescription() %>"},
							<%
								}
							%>
						]
					]
				});
				expenses.render();
				
		var cashflow = new CanvasJS.Chart("monthlyCashflow", {
			title: {
				text: "Monthly Cashflow"
			},
			data: [{
				type: "column",
				dataPoints: [
				<% 
				if(session.getAttribute("selecteBudget") != null){
						for(int i = 1; i <= 12; i++){
					%>
						{ x: <%= i %>, y: <%= budget.getIncomeForMonth(i) %>},
					<%
						}
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
		<% }%>
}
	</script>
	<script src="js/chart/canvasjs.min.js"></script>
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
		<div id="chartIncomes" style="height: 500px; width: 500px;"></div>	
		<div id="chartExpenses" style="height: 500px; width: 500px;"></div>
		<div id="monthlyCashflow" style="height: 500px; width: 1000px;"></div>
		</c:if>
</body>
</html>