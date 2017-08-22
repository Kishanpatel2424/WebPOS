<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file = "home.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control"      content="no-cache"> 
<title>Search List</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
</head>
<body>
<H1>Search an Item</H1>
<form id="MyForm" name="MyForm" action="" method="POST" >
<div class="input-group">
    <div class="input-group-btn search-panel">
		<!-- <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
		<span id="search_concept">Name</span> <span class="caret"></span>
		</button>
		<ul class="dropdown-menu" role="menu">
			<li><a href="#id"><i class="fa fa-angle-double-right"></i> ID</a></li>
			<li><a href="#name"><i class="fa fa-angle-double-right"></i> Name</a></li>
			<li><a href="#description"><i class="fa fa-angle-double-right"></i> Description</a></li>
		</ul> -->
	</div><H4>
	<input type="hidden" name="search_param" value="name" id="search_param">		 
	<input type="text" name="SearchList" placeholder="Search.." id="search_key" value="">
	<input type="submit" class="btn btn-info" type="submit" name="Find" value="Search"></H4>
</div>
</form>

<%@ page import="com.mysql.*" %>
<%@ page import="java.sql.*" %>

<html>
<body>
<div id="content">


    <H3><p>Search Resealts </p></H3>

    <div class="table-responsive" class="ex1">
	<table class="table" id="item" >
        <thead>
            <tr>
            	<th>#</th>
            	<th>Choose</th>
                <th>ItemCode</th>
                <th>Name</th>
                <th>Department</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>


            <%
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = null;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/test", "root", "root");
                Statement stmt = null;
                stmt = conn.createStatement();
                String SearchList =request.getParameter("SearchList");
                String query = "SELECT * FROM Item where ItemName LIKE '%" + SearchList + "%' OR Department Like '%"+SearchList+"%'";
                ResultSet rs = null;
                rs = stmt.executeQuery(query);
                int num =0;
                while(rs.next()){
                	num++;
            %>
            <tr>
                <%
                    String code = rs.getString("ItemCode");
                    String name = rs.getString("ItemName");
                    String company = rs.getString("Department");
                    double salary = rs.getDouble("ItemPrice");
                    
                %><form id="MyForm" name="MyForm" action="/InsertDataWebApplication/Cashier" method="POST" >
                <td><%=num %></td>
                <td><input type="submit" value =<%=code %> name="ItemCode"> &nbsp;</td>
                <td><%=code %></td>
                <td><%=name %></td>
                <td><%=company %></td>
                <td><%=salary %></td>
                
                </form>
            </tr>               

            <%      
                }
            %>

        </tbody>
    </table>
    </div>
</div>
</body>
</html>
</body>
</html>