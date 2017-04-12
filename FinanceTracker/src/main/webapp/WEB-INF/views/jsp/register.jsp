<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<meta charset="utf-8">
	<title>Finance Tracker - Register</title>

	<!-- Google Fonts -->

	<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>
	
	<link rel="stylesheet" href="css/login-animate.css">
	<link rel="stylesheet" href="css/login.css">
	<script src="js/lib/jquery.1.8.3.min.js"></script>
	<script src="js/app/validate.js"></script>
	<script src="js/app/register.js"></script>
	<script src="js/lib/bootstrap.js"></script>
</head>

<body>
	<div class="container">
		<div class="top">
			<h1 id="title" class="hidden"><a href="index.html"><span id="logo">Finance Tracker</span></a></h1>
		</div>
		<div class="alert-register-fail alert-dismissible alert-danger-register">
		</div>
		<div class="alert-register-ok alert-dismissible alert-success-register">
			Registered successfully. Click here to <a href="login.html">login</a>.
		</div>
		<div class="login-box animated fadeInUp register-box">
			<div class="box-header">
				<h2>Register</h2>
			</div>
			<form action="register" method="POST">
				<label for="username">Email</label>
				<br/>
				<input type="text" id="username">
				<br/>
				<label for="firstname">First name</label>
				<br/>
				<input type="text" id="firstname">
				<br/>
				<label for="lastname">Last name</label>
				<br/>
				<input type="text" id="lastname">
				<br/>
				<label for="password">Password</label>
				<br/>
				<input type="password" id="password">
				<br/>
				<label for="confirm-password">Confirm Password</label>
				<br/>
				<input type="password" id="confirm-password">
				<br/>
				<button id="register-btn1" type="submit">Register</button>
				<br/>
			</form>
		</div>
	</div>
</body>

<script>
	$(document).ready(function () {
    	$('#logo').addClass('animated fadeInDown');
    	$("input:text:visible:first").focus();
	});
	$('#username').focus(function() {
		$('label[for="username"]').addClass('selected');
	});
	$('#username').blur(function() {
		$('label[for="username"]').removeClass('selected');
	});
	$('#password').focus(function() {
		$('label[for="password"]').addClass('selected');
	});
	$('#password').blur(function() {
		$('label[for="password"]').removeClass('selected');
	});
	$('#firstname').focus(function() {
		$('label[for="firstname"]').addClass('selected');
	});
	$('#firstname').blur(function() {
		$('label[for="firstname"]').removeClass('selected');
	});
	$('#lastname').focus(function() {
		$('label[for="lastname"]').addClass('selected');
	});
	$('#lastname').blur(function() {
		$('label[for="lastname"]').removeClass('selected');
	});
</script>

</html>