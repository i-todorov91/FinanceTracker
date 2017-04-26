<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en" class="no-js">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>
  <link rel="icon" href="img/favicon.png" type="image/png">
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
        <li class="has-children">
        
          <c:if test="${sessionScope.budgets.isEmpty()}">
          	<a href="#">No budgets</a>
          </c:if>
          <c:if test="${sessionScope.budgets.isEmpty() == false}">
          	<c:set var="selected" scope="session" value="${selectedBudget.getName()}"></c:set>
          	<a href="#">${selected}</a>
          </c:if>
			<ul>
				<c:forEach items="${sessionScope.budgets.entrySet()}" var="item">
					<c:if test="${!selected.equals(item.key)}">
				    	<li>
				    		<form id="${item.key}" action="login/changebudget" method="post">
				    			<input name="clicked" type="hidden" value="${item.key}"/>
				    			<a href="#" onclick="document.getElementById('${item.key}').submit()">${item.key}</a>
				    		</form>
				    	</li>
					</c:if>
				</c:forEach>
			</ul>
		</li>
       	<li class="sidebar">
       		<a href="login/addbudget">Add budget</a>
       	</li>
        <li class="sidebar">
          	<a href="login/addtransaction">Add transaction</a>
        </li>
        <li class="sidebar">
          	<a href="login/viewdiagrams">View diagrams</a>
        </li>
    </nav>

    <section id="top_content" class="top_cont_outer">
      <div class="container">
        <div class="top_content">
          <div class="row">
          
          <c:if test="${sessionScope.addtransaction == true}">
          		<%@ include file="transaction.jsp" %>
          </c:if>
          
          <c:if test="${sessionScope.addbudget == true}">
          		<%@ include file="budget.jsp" %>
          </c:if>
          
          <c:if test="${sessionScope.contact == true}">
			    <%@ include file="contact.jsp" %>
          </c:if>
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