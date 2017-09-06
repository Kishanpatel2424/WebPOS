<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<style>
a{
width:10%;
font-size:20px;
}
</style>
		<body>
		
		<nav class="navbar navbar-default">
		<div align="center">
			
  			<div>
  			
        		<a href="/InsertDataWebApplication/AddItem.jsp" id="Add" class="btn btn-success" >Add Items</a>
        		<a href="/InsertDataWebApplication/Reg.jsp" id="register" class="btn btn-primary">Register</a>
        		<a href="/InsertDataWebApplication/DeleteItems.jsp" id="Delete" class="btn btn-danger" >Delete Items</a>
        		<a href="/InsertDataWebApplication/Update.jsp" id="Update" class="btn btn-warning" >Update Items</a>
				<a href="/InsertDataWebApplication/Reports.jsp" id="Report" class="btn btn-warning" >Reports</a>
				<a class="btn btn-danger"><form action="<%=response.encodeURL("LogoutServlet") %>" method="post">
				<input type="submit"class="btn btn-danger btn btn-primary btn-xs" value="LogOut" ></form></a>
			</div>

		</div>
		</nav>
		</body>
</html> 