
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<body onLoad="displayResult()">
	
<%@ include file = "home.jsp" %>
<br>
</br>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete Item</title>
</head>
<body>
<center><h1>Delete Item</h1></center>

<%
String userName = null;
//allow access only if session exists
if(session.getAttribute("user") == null || session.getAttribute("user").equals("Cashier")){
	response.sendRedirect("/InsertDataWebApplication/index.jsp");
}else userName = (String) session.getAttribute("user");
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("user")) userName = cookie.getValue();
}
}
%>

<%
	String succ = (String)request.getAttribute("Product Deleted");
	String NotFound = (String)request.getAttribute("Product Not Found");
	if(succ!= null){
		%>
		 <script type="text/javascript">
	    var msg = "<%=succ%>";
	    alert(msg);
	</script><%
	}
	
	if(NotFound!= null){
		%>
		 <script type="text/javascript">
	    var msg = "<%=NotFound%>";
	    alert(msg);
	</script><%
	}
%>

<div class="container">

<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
		<form name="MyForm" action="/InsertDataWebApplication/DeleteItems" method="POST" role="form">
			<h2>Delete an Item </h2>
			<hr class="colorgraph">
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group">Scan BarCode
                        <input type="text" name="iCode" class="form-control input-lg" placeholder="Barcode" tabindex="1">
					</div>
				</div>
				</div>
				<hr class="colorgraph">
			<div class="row">
				<div class="col-xs-12 col-md-6"><input type="submit" value="Delete" name="Deletesubmit" class="btn btn-primary btn-block btn-lg" tabindex="7"></div>
				<div class="col-xs-12 col-md-6"><input type="reset" value="Clear" name="clear" class="btn btn-success btn-block btn-lg"/></div>
			</div>
				</form>
				</div>
				</div>
				</div>
        
</body>
</html>