<%@page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256" import="java.io.IOException"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, maximum-scale=1">

<title>Homepage</title>
<link rel="icon" href="favicon.png" type="image/png">
<link href="css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/linecons.css" rel="stylesheet" type="text/css">
<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="css/responsive.css" rel="stylesheet" type="text/css">
<link href="css/animate.css" rel="stylesheet" type="text/css">

<link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/sidebar/reset.css"> <!-- CSS reset -->
<link rel="stylesheet" href="css/sidebar/style.css"> <!-- Resource style -->
  	

<link href='https://fonts.googleapis.com/css?family=Lato:400,900,700,700italic,400italic,300italic,300,100italic,100,900italic' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Dosis:400,500,700,800,600,300,200' rel='stylesheet' type='text/css'>

<!-- =======================================================
    Theme Name: Butterfly
    Theme URL: https://bootstrapmade.com/butterfly-free-bootstrap-theme/
    Author: BootstrapMade
    Author URL: https://bootstrapmade.com
======================================================= -->

<script type="text/javascript" src="js/lib/jquery.1.8.3.min.js"></script>
<script type="text/javascript" src="js/lib/bootstrap.js"></script>
<script type="text/javascript" src="js/lib/jquery-scrolltofixed.js"></script>
<script type="text/javascript" src="js/lib/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/lib/jquery.isotope.js"></script>
<script type="text/javascript" src="js/lib/wow.js"></script>
<script type="text/javascript" src="js/lib/classie.js"></script>
<script src="js/sidebar/modernizr.js"></script> <!-- Modernizr -->
<script type="text/javascript">
	$(document).ready(function(e) {
        $('.res-nav_click').click(function(){
		$('ul.toggle').slideToggle(600)	
			});	
			
	$(document).ready(function() {
$(window).bind('scroll', function() {
         if ($(window).scrollTop() > 0) {
             $('#header_outer').addClass('fixed');
         }
         else {
             $('#header_outer').removeClass('fixed');
         }
    });
	
	  });
	  

		    });	
function resizeText() {
	var preferredWidth = 767;
	var displayWidth = window.innerWidth;
	var percentage = displayWidth / preferredWidth;
	var fontsizetitle = 25;
	var newFontSizeTitle = Math.floor(fontsizetitle * percentage);
	$(".divclass").css("font-size", newFontSizeTitle)
}
</script>
</head>
<body>
<c:if test="${sessionScope.logged == null || sessionScope.logged == false}">
	<c:redirect url="index.html"></c:redirect>
</c:if>
<!--Header_section-->
<header id="header_outer">
  <div class="container">
    <div class="header_section">
      <div class="logo"><a href="javascript:void(0)"><img src="img/logob.jpg" alt=""></a></div>
      <nav class="nav" id="nav">
      	<ul class="toggle">
          <li><a id="main-contact" href="javascript:void(0)">Contact us</a></li>
          <li><a id="main-logout" href="logout">Logout</a></li>
        </ul>
        <ul class="">
          <li><a id="main-contact" href="javascript:void(0)">Contact us</a></li>
          <li><a id="main-logout" href="logout">Logout</a></li>
        </ul>
      </nav>
      <a class="res-nav_click animated wobble wow"  href="javascript:void(0)"><i class="fa-bars"></i></a> </div>
  </div>
</header>
<!--Header_section--> 

<!--Top_content-->
<section id="top_content" class="top_cont_outer">
	<nav class="cd-side-nav">
		<ul>
			<li class="has-children overview">
				<a href="#0">Overview</a>
				
				<ul>
					<li><a href="#0">All Data</a></li>
					<li><a href="#0">Category 1</a></li>
					<li><a href="#0">Category 2</a></li>
				</ul>
			</li>
			<li class="has-children notifications active">
				<a href="#0">Notifications<span class="count">3</span></a>
				
				<ul>
					<li><a href="#0">All Notifications</a></li>
					<li><a href="#0">Friends</a></li>
					<li><a href="#0">Other</a></li>
				</ul>
			</li>
		</ul>

		<ul>
			<li class="cd-label">Secondary</li>
			<li class="has-children bookmarks">
				<a href="#0">Bookmarks</a>
				
				<ul>
					<li><a href="#0">All Bookmarks</a></li>
					<li><a href="#0">Edit Bookmark</a></li>
					<li><a href="#0">Import Bookmark</a></li>
				</ul>
			</li>
			<li class="has-children images">
				<a href="#0">Images</a>
				
				<ul>
					<li><a href="#0">All Images</a></li>
					<li><a href="#0">Edit Image</a></li>
				</ul>
			</li>

			<li class="has-children users">
				<a href="#0">Users</a>
				
				<ul>
					<li><a href="#0">All Users</a></li>
					<li><a href="#0">Edit User</a></li>
					<li><a href="#0">Add User</a></li>
				</ul>
			</li>
		</ul>

		<ul>
			<li class="cd-label">Action</li>
			<li class="action-btn"><a href="#0">+ Button</a></li>
		</ul>
	</nav>
    <div class="container">
      <div class="top_content">
        <div class="row">
          <div class="col-lg-5 col-sm-7">
			TUk shte slagame nqkwi neshta
          </div>
        </div>
      </div>
    </div>
</section>

<footer class="footer_section">
 <div class="container">
   <div class="footer_bottom"> 
       <span>© Finance Tracker 2017</span> 
       <div class="credits">
           <!-- 
               All the links in the footer should remain intact. 
               You can delete the links only if you purchased the pro version.
               Licensing information: https://bootstrapmade.com/license/
               Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/buy/?theme=Butterfly
           -->
           <a href="https://bootstrapmade.com/free-business-bootstrap-themes-website-templates/">Business Bootstrap Themes</a> by <a href="https://bootstrapmade.com/">BootstrapMade</a>
       </div>
   </div>
 </div>
</footer>
<script src="js/sidebar/jquery-2.1.4.js"></script>
<script src="js/sidebar/jquery.menu-aim.js"></script>
<script src="js/sidebar/main.js"></script> <!-- Resource jQuery -->
</body>
</html>