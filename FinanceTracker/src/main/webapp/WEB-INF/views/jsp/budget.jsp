<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form class="form-horizontal" method="post" action="addbudget">
	  <fieldset>
	    <div class="form-group">
	      <label for="inputEmail" class="col-lg-2 control-label">Name</label>
	      <div class="col-lg-10">
	        <input class="form-control" id="inputEmail" placeholder="Name" name="name" type="text">
	      </div>
	    </div>
	    <div class="form-group">
	      <label for="inputPassword" class="col-lg-2 control-label">Amount</label>
	      <div class="col-lg-10">
	        <input class="form-control" id="inputNumber" placeholder="Amount" name="amount" type="number" min="0">
	      </div>
	    </div>
	    <div class="form-group">
	      <div class="col-lg-10 col-lg-offset-2">
	        <button type="reset" class="btn btn-default">Cancel</button>
	        <button type="submit" class="btn btn-primary">Submit</button>
	      </div>
	    </div>
	  </fieldset>
	</form>
</body>
</html>