$(document).ready(function() {
	function validateEmail(email){
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	    return re.test(email);
	}
	
	function validatePassword(password){
		var re = /(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%\\*^&+=]).{8,}/;
	    return re.test(password);
	}
	
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
					if(obj['redirect'] == true){
						// redirect to home page
						window.location.href="http://localhost:8080/FinanceTracker/";
					}
					else if(obj['login'] == true){
						// redirect to logged in page
						// TODO
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