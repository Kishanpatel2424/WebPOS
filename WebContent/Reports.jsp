<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %> 
<%@ page import="java.lang.*" %> 
<%@ page import="java.util.Date" %>
<%@ page import="com.mysql.*" %>
<%@ page import="java.sql.*" %>
<%@ include file = "home.jsp" %>
<%@ page import="Items.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">


  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  

<title>Reports</title>

</head>
<%
String userName = null;
//allow access only if session exists
if(session.getAttribute("user") == null){
	response.sendRedirect("/InsertDataWebApplication/index.jsp");
}else userName = (String) session.getAttribute("user");
String sessionID = null;

%>
<%
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = null;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/test", "root", "root");
                //conn = DriverManager.getConnection("jdbc:mysql://node23485-onlinepos.njs.jelastic.vps-host.net /test","root","BPNivr47456");
                Statement stmt = null;
                stmt = conn.createStatement();
%>
<body>

<div id="content">


    <center><H1><p>Sale Report </p></H1></center>
    
    <table class="table"style="width:100%">
    	 <thead>
            <tr style="COLOR:#FF2300;font-size: 20px; font-Type:Bold">
            	<th>#</th>
            	<th>Invoice Number</th>
            	<th>Items</th>
            	<th>Items Total</th>
                <th>Tax</th>
                <th>Total</th>
                <th>Date/Time</th>
                <th>Cashier</th>
                
            </tr>
        </thead>
    <div class="table-responsive" class="ex1">
    <%          
                String query = "SELECT InvoiceDetail.InvoiceNumber,count(*)as Items, sum((ItemPrice*Quantity)-Tax) as ItemsTotal, sum(Tax) as Tax, sum((ItemPrice*Quantity)+Tax) as Total, Date,Cashier_Name  FROM InvoiceDetail WHERE Status ='Open' GROUP BY(InvoiceNumber)";
                ResultSet rs = null;
                rs = stmt.executeQuery(query);
                int num=0;
                while(rs.next()){
                	num++;
                	int invNum = rs.getInt("InvoiceDetail.InvoiceNumber");
                	int items = rs.getInt("Items");
    				double ItemsTotal = rs.getDouble("ItemsTotal");
    				double Tax 	= rs.getDouble("Tax");
    				double Total = rs.getDouble("Total");
    				String Cashier = rs.getString("Cashier_Name");
    				java.sql.Timestamp date = rs.getTimestamp("Date");
    %>
    <tr>
    	<td><%=num %></td>
    	<td><%=invNum%></td>
    	<td><%=items %></td>
    	<td><%=ItemsTotal %></td>
    	<td><%=Tax %></td>
    	<td><%=Total %></td>
    	<td><%=date %></td>
    	<td><%=Cashier %></td>
    </tr>
    <%} 
    	query ="SELECT sum((ItemPrice*Quantity)-Tax) as TotalNonTax, sum(Tax) as Tax, sum((ItemPrice*Quantity)+Tax) as Total FROM InvoiceDetail WHERE Status ='Open'";
   		rs = stmt.executeQuery(query);
   		
   		while(rs.next()){
   		double nonTax = rs.getDouble("TotalNonTax");
   		double Tax = rs.getDouble("Tax");
   		double Total = rs.getDouble("Total");
   		
    %>
    <tr style="COLOR:#FF2300;font-size: 20px">
    	<td>Total</td>
    	<td><%=num%></td>
    	<td></td>
    	<td><%=nonTax %></td>
    	<td><%=Tax %></td>
    	<td><%=Total %></td>
    	<td></td>
    </tr>	
    
    <%} %>
    </div>
    </table>
    <br>
    
	<h3><table class="table"style="width:50%">
        <thead>
            <tr style="COLOR:#FF2300">
            	<th></th>
            	<th>Transections</th>
            	<th>Payment Type</th>
                <th>Total</th>
                <th>Tax</th>
                
            </tr>
        </thead>
    <div class="table-responsive" class="ex1">
	
      <%          
                 query = "SELECT COUNT(PaymentType) as Transections,PaymentType,SUM(InvoiceTotal) as Total, SUM(Tax) as TaxCollected "+
                		"FROM Invoice WHERE Status='Open' "+
                		"Group BY PaymentType";
                 
                rs = stmt.executeQuery(query);
                
                while(rs.next()){
                	int count = rs.getInt("Transections");
                	String paymentType = rs.getString("PaymentType");
    				double Total = rs.getDouble("Total");
    				double Tax 	= rs.getDouble("TaxCollected");
    %><tr>
    	<td>*</td>
    	<td><%=count %></td>
    	<td><%=paymentType %></td>
    	<td><%=Total %></td>
    	<td><%=Tax %></td>
    </tr>
    <%} 
    	query = "SELECT count(InvoiceNumber) as Customers,SUM(InvoiceTotal) as Total, SUM(Tax) as TaxCollected FROM Invoice WHERE Status='open'";
    	rs = stmt.executeQuery(query);
    	while(rs.next()){
    		int Customers = rs.getInt("Customers");
    		double TotalSale = rs.getDouble("Total");
    		double TotalTax = rs.getDouble("TaxCollected");
    	
    %>
    <tr style="COLOR:#FF2300;font-size: 25px">
	    <td>Total</td>
	    <td><%=Customers %></td>
	    <td></td>
	    <td><%=TotalSale%></td>
	    <td><%=TotalTax %></td>
    </tr>
    <%} %>
    </div>	
    </table></h3>
</div>
</body>
</html>
