<%@ page errorPage="error500.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ft.model.DAO.UserDAO"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en" class="no-js">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
 
  <link rel="icon" href="img/favicon.png" type="image/png">
  <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
  <link href="css/style.css" rel="stylesheet" type="text/css">
  <link href="css/responsive.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="css/sidebar/style.css"> <!-- Resource style -->


  <!-- =======================================================
      Theme Name: Butterfly
      Theme URL: https://bootstrapmade.com/butterfly-free-bootstrap-theme/
      Author: BootstrapMade
      Author URL: https://bootstrapmade.com
  ======================================================= -->

  <script type="text/javascript" src="js/sidebar/jquery-2.1.4.js"></script>
  <script type="text/javascript" src="js/lib/bootstrap.js"></script>
  <script type="text/javascript" src="js/sidebar/modernizr.js"></script> <!-- Modernizr -->
  <title>Finance Tracker</title>
  <style>
  	.cd-nav{
  		margin-top: 24px;
	}
  </style>
</head>
<body>
<%
	double res = UserDAO.getInstance().getAllUsers().get((String) session.getAttribute("username")).getBudgetsSum();
%>
  <c:set var="totalSum" value="<%= res %>"></c:set>
  <header class="cd-main-header"> 
    <div class="container">
      <div class="header_section">
        <a href="login/viewdiagrams" class="logo"><img src="img/logob.jpg" alt="Logo"></a>
        <a href="javascript:void(0)" class="cd-nav-trigger"><span></span></a>

        <nav class="cd-nav">
          <ul class="cd-top-nav">
          	<span><strong>Total sum:</strong> <c:out value="${totalSum}"></c:out></span>
            <li><a href="login/accountinformation">Account information</a></li>
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
          	<a href="login/removebudget">Remove budget</a>
        </li>
        <li class="sidebar">
          	<a href="login/filterdate">Date filter</a>
        </li>
        <li class="sidebar">
          	<a href="login/viewdiagrams">View diagrams</a>
        </li>
    </nav>
	
    <section id="top_content" class="top_cont_outer">
      <div class="container">
        <div class="top_content">
          <div class="row">
          	<c:import url="${sessionScope.url}"></c:import>
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
<script type="text/javascript" src="js/sidebar/jquery.menu-aim.js"></script>
<script type="text/javascript" src="js/sidebar/main.js"></script> <!-- Resource jQuery -->
</body>
</html>