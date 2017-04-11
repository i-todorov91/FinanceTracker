$(document).ready(function() {	
	$("#register-btn").click(function (){
		var email = document.getElementById("username").value;
		var firstName = document.getElementById("firstname").value;
		var secondName = document.getElementById("lastname").value;
		var password = document.getElementById("password").value;
		var confirmPassword = document.getElementById("confirm-password").value;
		var validEmail = validateEmail(email);
		var validPassword = validatePassword(password);
		var validConfirmPassword = validatePassword(confirmPassword);
		var validFirstName = validateString(firstName);
		var validSecondName = validateString(secondName);
		
		// check if the data is correct
		var valid = validEmail && validPassword && validFirstName && validSecondName && password == confirmPassword;
		
		if(valid){
			$("#username").css("background-color", "white");
			$("#password").css("background-color", "white");
			$("#confirm-password").css("background-color", "white");
			$("#firstname").css("background-color", "white");
			$("#secondname").css("background-color", "white");
			$.ajax({
				url: 'register',
				type: 'POST',
				data: {email: email, password: password, confirmPassword: confirmPassword, firstName: firstName, secondName: secondName},
				success: function(result){
					var obj = $.parseJSON(result);
					if(obj['register'] == "redirect"){
						// redirect to home page
						window.location.href="http://localhost:8080/FinanceTracker/";
					}
					else if(obj['register'] == true){
						$(".alert-register-ok").css("visibility", "visible");
						$(".alert-register-fail").css("visibility", "hidden");
					}
					else if(obj['register'] == "invalid"){
						$(".alert-register-fail").html("Some of the fields contains incorrect data!");
						$(".alert-register-fail").css("visibility", "visible");
						$(".alert-register-ok").css("visibility", "hidden");
					}
					else{
						$(".alert-register-fail").html("User with this email already exists");
						$(".alert-register-fail").css("visibility", "visible");
						$(".alert-register-ok").css("visibility", "hidden");
					}
				}
			});
		}
		else{
			// set email field to pink if error otherwise set it to white
			if(!validEmail){
				$("#username").css("background-color", "pink");
			}
			else{
				$("#username").css("background-color", "white");
			}
			
			// set password field to pink if error otherwise set it to white
			if(!validPassword){
				$("#password").css("background-color", "pink");
			}
			else{
				$("#password").css("background-color", "white");
			}
			
			// set confirm-password field to pink if error otherwise set it to white
			if(!validConfirmPassword){
				$("#confirm-password").css("background-color", "pink");
			}
			else{
				$("#confirm-password").css("background-color", "white");
			}
			
			// set firstname field to pink if error otherwise set it to white
			if(!validFirstName){
				$("#firstname").css("background-color", "pink");
			}
			else{
				$("#firstname").css("background-color", "white");
			}
			
			// set secondname field to pink if error otherwise set it to white
			if(!validSecondName){
				$("#lastname").css("background-color", "pink");
			}
			else{
				$("#lastname").css("background-color", "white");
			}
			
			// tell the user the passwords do not match
			if(password != confirmPassword){
				$(".alert-register-fail").html("Passwords do not match!");
				$(".alert-register-fail").css("visibility", "visible");
			}
		}
		
	});
	
});