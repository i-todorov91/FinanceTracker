<%@page errorPage="error.jsp"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256" import="java.io.IOException"%>
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
<script type="text/javascript" src="js/app/main.js"></script>
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

<!--Header_section-->
<header id="header_outer">
  <div class="container">
    <div class="header_section">
      <div class="logo"><a href="javascript:void(0)"><img src="img/logob.jpg" alt=""></a></div>
      <nav class="nav" id="nav">
        <ul class="toggle">
          <li><a id="main-contact" href="javascript:void(0)">Contact us</a></li>
          <li><a id="main-logout" onclick="logout()" href="javascript:void(0)">Logout</a></li>
        </ul>
        <ul class="">
          <li><a id="main-contact" href="javascript:void(0)">Contact us</a></li>
          <li><a id="main-logout" onclick="logout()" href="javascript:void(0)">Logout</a></li>
        </ul>
      </nav>
      <a class="res-nav_click animated wobble wow"  href="javascript:void(0)"><i class="fa-bars"></i></a> </div>
  </div>
</header>
<!--Header_section--> 

  <div class="container">
    <div class="footer_bottom"> 
        <span>Â© Finance Tracker 2017</span> 
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
</body>
</html>