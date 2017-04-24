<!doctype html>
<html lang="en" class="no-js">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>
  <link rel="icon" href="jsp/favicon.png" type="image/png">
  <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
  <link href="css/style.css" rel="stylesheet" type="text/css">
  <link href="css/responsive.css" rel="stylesheet" type="text/css">
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
  <script src="js/sidebar/modernizr.js"></script> <!-- Modernizr -->
    
  <title>Finance Tracker</title>
</head>
<style>
.cd-top-nav a:hover{
	color:#428bca;
}
</style>
<body>
  <header class="cd-main-header" id="header_outer"> 
    <div class="container">
      <div class="header_section">
        <a href="javascript:void(0)" class="logo"><img src="img/logob.jpg" alt="Logo"></a>
        <a href="javascript:void(0)" class="cd-nav-trigger">Menu<span></span></a>

        <nav class="cd-nav">
          <ul class="cd-top-nav">
            <li><a href="login/contact">Contact us</a></li>
            <li><a href="logout">Logout</a></li>
          </ul>
        </nav>
      </div>
    </div>
  </header> <!-- .cd-main-header -->

  <main class="cd-main-content">
    <nav class="cd-side-nav">
      <ul>
       	<li class="sidebar">
       		<a href="#">Add budget</a>
       	</li>
        <li class="sidebar">
          	<a href="#">Add transaction</a>
        </li>
    </nav>

    <section id="top_content" class="top_cont_outer">
      <div class="container">
        <div class="top_content">
          <div class="row">
            <div class="form">
                <div id="sendmessage">Your message has been sent. Thank you!</div>
                <div id="errormessage"></div>
                <form action="index.html" method="post" role="form" class="contactForm">
                    <div class="form-group">
                        <input type="text" name="name" class="form-control input-text" id="name" placeholder="Your Name" data-rule="minlen:4" data-msg="Please enter at least 4 chars" />
                        <div class="validation"></div>
                    </div>
                    <div class="form-group">
                        <input type="email" class="form-control input-text" name="email" id="email" placeholder="Your Email" data-rule="email" data-msg="Please enter a valid email" />
                        <div class="validation"></div>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control input-text" name="subject" id="subject" placeholder="Subject" data-rule="minlen:4" data-msg="Please enter at least 8 chars of subject" />
                        <div class="validation"></div>
                    </div>
                    <div class="form-group">
                        <textarea class="form-control" name="message" rows="5" data-rule="required" data-msg="Please write something for us" placeholder="Message"></textarea>
                        <div class="validation"></div>
                    </div>
                  
                  <button type="submit" class="btn input-btn">SEND MESSAGE</button>
                </form>
          </div>
          </div>
      </div>
    </div>
  </section>
  </main> <!-- .cd-main-content -->
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