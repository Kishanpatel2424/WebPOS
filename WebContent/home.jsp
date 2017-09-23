<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
.sidenav {
    height: 100%;
    width: 0;
    position: fixed;
    z-index: 1;
    top: 0;
    left: 0;
    background-color: White;
    overflow-x: hidden;
    transition: 0.5s;
    padding-top: 5%;
}

.sidenav a {
    padding: 8px 8px 8px 50px;
    text-decoration: none;
    font-size: 25px;
    color: BLACK/* #818181 */;
    display: block;
    transition: 0.3s;
}

.sidenav a:hover {
    color: red/* #f1f1f1 */;
}

.sidenav .closebtn {
    position: absolute;
    top: 0;
    right: 25px;
    font-size: 36px;
    margin-left: 50px;
}

#main {
    transition: margin-left .5s;
    padding: 1%;
}

@media screen and (max-height: 450px) {
  .sidenav {padding-top: 15px;}
  .sidenav a {font-size: 18px;}
}

</style>
</head>
<body>

<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
  <a href="/InsertDataWebApplication/AddItem.jsp" id="Add" >Add Item</a>
  <a href="/InsertDataWebApplication/Cashier.jsp" id="register" >Register</a>
  <a href="/InsertDataWebApplication/DeleteItems.jsp" id="Delete" >Delete Items</a>
  <a href="/InsertDataWebApplication/Update.jsp" id="Update" >UpdateItems</a>
  <a href="/InsertDataWebApplication/Reports.jsp" id="Report" >Reports</a>
  <a><form action="<%=response.encodeURL("LogoutServlet") %>" method="post">
					<input type="submit"class="btn btn-danger btn btn-primary btn-L" Value="LogOut" style="width:100%;">
				</form></a>
</div>
 
<div id="main"style="background-color:#015773"><!-- Heading background color Blue:- #7c4dff -->

  <span style="font-size:30px;color:SILVER;cursor:pointer; display:inline;" onclick="openNav()">&#9776; Menu</span>
  
  <span style="font-size:30px;color:SILVER; display: block; margin: 0px auto; text-align: right;display:inline;float:Right">
  Online POS</span>
</div>

<script>
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
}
</script>
     
</body>
</html> 