$(document).ready(function() {
		$("#register-btn").click(function(){
			var email = document.getElementById("register-email").value;
			var password = document.getElementById("register-password").value;
			var first = document.getElementById("register-firstName").value;
			var second = document.getElementById("register-secondName").value;
			$.ajax({
				url: 'register',
				type: 'POST',
				data: {email: email, firstName: first, secondName: second, password: password},
				success: function(result){
					$("#div1").append("Registered successfuly: " + result);
				},
				error: function(result){
					$("#div1").append("Sorry could not register: " + result);
				}
			});
		});
		
		$("#login-btn").click(function (){
			var email = document.getElementById("login-email").value;
			var password = document.getElementById("login-password").value;
			$.ajax({
				url: 'login',
				type: 'POST',
				data: {email: email, password: password},
				success: function(result){
					$("#div2").append("Login successfuly: " + result);
				},
				error: function(result){
					$("#div2").append("Sorry could not login: " + result);
				}
			});
		});
		
		$("#add-budget-btn").click(function (){
			var name = document.getElementById("budget-name").value;
			var balance = document.getElementById("budget-balance").value;
			$.ajax({
				url: 'addbudget',
				type: 'POST',
				data: {name: name, balance: balance},
				success: function(result){
					$("#div3").append("Budget added successfuly: " + result);
				},
				error: function(result){
					$("#div3").append("Sorry could not add budget: " + result);
				}
			});
		});
	});