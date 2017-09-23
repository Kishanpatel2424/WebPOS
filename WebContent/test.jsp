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
#mySidenav a {
    position: absolute;
    left: -80px;
    transition: 0.3s;
    padding: 15px;
    width: 20%;
    text-decoration: none;
    font-size: 20px;
    color: white;
    border-radius: 0 5px 5px 0;
}

#mySidenav a:hover {
    left: 0;
}

#Add {
    bottom: 20px;
    
}

#register {
    bottom: 80px;
   
}

#Delete {
    top: 140px;
    
}

#Update {
    top: 200px;
   
}
#Report{
	top:260px;
}
</style>
		<body>
		
		
		<div id="mySidenav" class="sidenav">
			
        		<a href="/InsertDataWebApplication/AddItem.jsp" id="Add" class="btn btn-success" >Add Items</a>
        		<a href="/InsertDataWebApplication/Cashier.jsp" id="register" class="btn btn-primary" >Register</a>
        		<a href="/InsertDataWebApplication/DeleteItems.jsp" id="Delete" class="btn btn-danger" >Delete Items</a>
        		<a href="/InsertDataWebApplication/Update.jsp" id="Update" class="btn btn-warning" >Update Items</a>
				<a href="/InsertDataWebApplication/Reports.jsp" id="Report" class="btn btn-warning" >Reports</a>
				
				<form action="<%=response.encodeURL("LogoutServlet") %>" method="post">
					<input type="submit"class="btn btn-danger btn btn-primary btn-m" Value="LogOut" style="width:100%;">
				</form>
			
		</div>
		
		</body>
</html> 