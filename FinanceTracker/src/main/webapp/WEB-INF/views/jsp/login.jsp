<%@ page errorPage="error500.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<title>Finance Tracker - Login</title>

<!-- Google Fonts -->

<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>
<link rel="icon" href="img/favicon.png" type="image/png">
<link rel="stylesheet" href="css/login-animate.css">
<link rel="stylesheet" href="css/login.css">
<script src="js/lib/jquery.1.8.3.min.js"></script>
<script src="js/lib/bootstrap.js"></script>
<script type="text/javascript">

// Captcha Script
	function generateCaptcha(){
		 var alpha = new Array('1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', '1', '2', '3', '4', '5', '6', '7', '8', '9');
         var i;
         for (i=0;i<6;i++){
           var a = alpha[Math.floor(Math.random() * alpha.length)];
           var b = alpha[Math.floor(Math.random() * alpha.length)];
           var c = alpha[Math.floor(Math.random() * alpha.length)];
           var d = alpha[Math.floor(Math.random() * alpha.length)];
           var e = alpha[Math.floor(Math.random() * alpha.length)];
           var f = alpha[Math.floor(Math.random() * alpha.length)];
           var g = alpha[Math.floor(Math.random() * alpha.length)];
          }
        var code = a + ' ' + b + ' ' + c + ' ' + d + ' ' + e + ' '+ f + ' ' + g;
		document.getElementById("txtCaptcha").value = code;
		document.getElementById("CaptchaDiv").value = code;
	}
	
	$(document).ready(function() {
		generateCaptcha();
	});
	
	function checkform(theform){
	var why = "";
	
	if(theform.CaptchaInput.value == ""){
	why += "- Please Enter CAPTCHA Code.\n";
	}
	if(theform.CaptchaInput.value != ""){
	if(ValidCaptcha(theform.CaptchaInput.value) == false){
	why += "- The CAPTCHA Code Does Not Match.\n";
	}
	}
	if(why != ""){
	alert(why);
	return false;
	}
	}

	// Validate input against the generated number
	function ValidCaptcha(){
	var str1 = removeSpaces(document.getElementById('txtCaptcha').value);
	var str2 = removeSpaces(document.getElementById('CaptchaInput').value);
	if (str1 == str2){
	return true;
	}else{
		generateCaptcha();
	return false;
	}
	}
	
	// Remove the spaces from the entered and generated code
	function removeSpaces(string){
	return string.split(' ').join('');
	}
</script>
</head>
<style>
	.capbox{
		font-weight: bolder;
		text-align: center;
		vertical-align: middle;
	}
	#CaptchaDiv{
		border: none;
		font-size: 1.4rem;
    	text-align: center;
    	background-color: white;
    	width: 70%;
	}
	#CaptchaInput{
		font-size: 1rem;
	}
</style>
<body>
	
	<div class="container">
		<div class="top">
			<h1 id="title" class="hidden">
				<a href="index.html"><span id="logo">Finance Tracker</span></a>
			</h1>
		</div>
		<c:if test="${sessionScope.logged != null && sessionScope.logged == false && sessionScope.message == true}">
			<div class="alert-register alert-dismissible alert-danger-register">
				<h3>There was an error with your E-Mail/Password combination. Please try again.</h3>
			</div>
		</c:if>

		<div class="login-box animated fadeInUp">
			<div class="box-header">
				<h2>Log In</h2>
			</div>
			<form:form action="login" method="post" id="login-form" commandName="userLogin" onsubmit="return checkform(this);">
				<form:label path="email">Email</form:label>
				<br/>
				<form:input path="email"/> <br/>
				<form:label path="password">Password</form:label>
				<br/> 
				<form:password path="password"/>
				<br/>
				<div class="capbox">
					<input id="CaptchaDiv" disabled/>
					<div class="capbox-inner">
					<label for="txtCaptcha">Type the above captcha:<label><br>
					<input type="hidden" id="txtCaptcha" disabled/>
					<input type="text" name="CaptchaInput" id="CaptchaInput"/>
					</div>
				</div>
				<input id="login-btn" type="Submit" value="Sign in">
			</form:form>
		</div>
	</div>
</body>
</html>