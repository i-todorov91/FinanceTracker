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

<link
	href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700|Lato:400,100,300,700,900'
	rel='stylesheet' type='text/css'>
<link rel="icon" href="img/favicon.png" type="image/png">
<link rel="stylesheet" href="css/login-animate.css">
<link rel="stylesheet" href="css/login.css">
<script src="js/lib/jquery.1.8.3.min.js"></script>
<script src="js/lib/bootstrap.js"></script>
</head>

<body>
	<div class="container">
		<div class="top">
			<h1 id="title" class="hidden">
				<a href="index.html"><span id="logo">Finance Tracker</span></a>
			</h1>
		</div>
		<c:if test="${sessionScope.logged != null && sessionScope.logged == false && sessionScope.message != null}">
			<div class="alert-register alert-dismissible alert-danger-register">
				<h3><c:out value=" ${sessionScope.message}"></c:out></h3>
			</div>
		</c:if>

		<div class="login-box animated fadeInUp">
			<div class="box-header">
				<h2>Log In</h2>
			</div>
			<form:form action="login" method="post" id="login-form" commandName="userLogin">
				<form:label path="email">Email</form:label>
				<br/>
				<form:input path="email"/> <br />
				<form:label path="password">Password</form:label>
				<br/> 
				<form:password path="password"/>
				<br/>
				<input id="login-btn" type="Submit" value="Sign in">
			</form:form>
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
</script>

</html>