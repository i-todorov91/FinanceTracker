$(document).ready(function() {	
	$("#register-btn").click(function (){
		var email = document.getElementById("username").value;
		var firstName = document.getElementById("firstname").value;
		var secondName = document.getElementById("lastname").value;
		var password = document.getElementById("password").value;
		var validEmail = validateEmail(email);
		var validPassword = validatePassword(password);
		var validFirstName = validateString(firstName);
		var validSecondName = validateString(secondName);
		
		if(validEmail && validPassword && validFirstName && validSecondName){
			$("#username").css("background-color", "white");
			$("#password").css("background-color", "white");
			$("#firstname").css("background-color", "white");
			$("#secondname").css("background-color", "white");
			$.ajax({
				url: 'register',
				type: 'POST',
				data: {email: email, password: password, firstName: firstName, secondName: secondName},
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
					else if(obj['register'] == "invalid email"){
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
			if(!validFirstName){
				$("#firstname").css("background-color", "pink");
			}
			else{
				$("#firstname").css("background-color", "white");
			}
			if(!validSecondName){
				$("#lastname").css("background-color", "pink");
			}
			else{
				$("#lastname").css("background-color", "white");
			}
		}
		
	});
	
});