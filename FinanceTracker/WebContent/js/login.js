$(document).ready(function() {	
	$("#login-btn").click(function (){
		var email = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		var validEmail = validateEmail(email);
		var validPassword = validatePassword(password);
		
		if(validEmail && validPassword){
			$("#username").css("background-color", "white");
			$("#password").css("background-color", "white");
			$.ajax({
				url: 'login',
				type: 'POST',
				data: {email: email, password: password},
				success: function(result){
					var obj = $.parseJSON(result);
					if(obj['login'] == "redirect"){
						// redirect to home page
						window.location.href="http://localhost:8080/FinanceTracker/";
					}
					else if(obj['login'] == true){
						window.location.href="http://localhost:8080/FinanceTracker/main.html";
					}
					else{
						$(".alert").css("visibility", "visible");
					}
				}
			});
		}
		else{
			if(!validEmail){
				$("#username").css("background-color", "pink");
			}
			else{
				$("#username").css("background-color", "white");
			}
			if(!validPassword){
				$("#password").css("background-color", "pink");
			}
			else{
				$("#password").css("background-color", "white");
			}
		}
		
	});
	
});