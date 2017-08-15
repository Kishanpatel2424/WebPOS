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
	String succ = (String)request.getAttribute("Product Deleted");
	if(succ!= null){
		%>
		 <script type="text/javascript">
	    var msg = "<%=succ%>";
	    alert(msg);
	</script><%
	}
%>
<%
	if(request.getAttribute("iName")!=null)
	{
		%>
		<h3><%= request.getAttribute("iName")%></h3>
		<%
	}
%>

<div class="container">

<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
		<form name="MyForm" action="/InsertDataWebApplication/DeleteItems" method="GET" role="form">
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