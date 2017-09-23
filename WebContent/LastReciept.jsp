<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>Reciept </title>
  
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<%@ page import="com.mysql.*" %>
<%@ page import="java.sql.*" %>

<body>

<div class="container-center">
	
	
	<%-- <a href ="/InsertDataWebApplication/Invoice_${maxInvNum}.pdf" target="_blank">Open inv#${maxInvNum}</a> --%>
	<!DOCTYPE html>

<%double tax =0, total=0;
String pt =null;

%>

<div style="margin:5% 40% 0% 33%;float:left; width:500px; box-shadow:0 0 3px #aaa; height:auto;color:#666;">
   <div style="width:100%; padding:10px; float:left; background:#1ca8dd; color:#fff; font-size:30px; text-align:center;">
	
   </div>
    <div style="width:100%; padding:0px 0px;border-bottom:1px solid rgba(0,0,0,0.2);float:left;">
        <div style="width:30%; float:left;padding:10px;">
           
            <span style="font-size:14px;float:left; width:100%;">
                Online POS
            </span>
			 <span style="font-size:14px;float:left;width:100%;">
                1 Infinite Ave
                LA, 233321
            </span>
			<span style="font-size:14px;float:left;width:100%;">
			Onlinepos@opos.com
			</span>
        </div>
		
        <div style="width:40%; color:RED; float:right;padding:">
            <span style="font-size:14px;float:right;  padding:10px; text-align:right;">
                <b>Date : <%=session.getAttribute("timestamp")%></b>
            </span>
			<span style="font-size:14px;float:right;  padding:10px; text-align:right;">
               <b>Invoice# :<%=session.getAttribute("maxInvNum")%></b>
            </span>
        </div>
    </div>
    <table class="table" id="item" >
        <thead>
            <tr>
            	<th>#</th>
            	<th>Code</th>
                <th>Name</th>
                <th>Qty</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
<%
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = null;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/test", "root", "root");
                //conn = DriverManager.getConnection("jdbc:mysql://node23485-onlinepos.njs.jelastic.vps-host.net/test","root","BPNivr47456");
                Statement stmt = null;
                stmt = conn.createStatement();
                
                String query = "SELECT *  FROM InvoiceDetail INNER JOIN Invoice  on InvoiceDetail.InvoiceNumber = Invoice.InvoiceNumber "+
                		"WHERE InvoiceDetail.InvoiceNumber = (SELECT max(InvoiceNumber) FROM Invoice)";
                ResultSet rs = null;
                rs = stmt.executeQuery(query);
                int num =0;
                
                while(rs.next()){
                	num++;
                
            %>
    
    
    <div style="width:100%; padding:0px; float:left;">
     <tr>
          <%
                    String code = rs.getString("ItemCode");
                    String name = rs.getString("ItemName");
                    double Qty = rs.getDouble("Quantity");
                    double salary = rs.getDouble("ItemPrice");
                    pt = rs.getString("Invoice.PaymentType");
                    
                    tax+= rs.getDouble("Tax");
                    total= rs.getDouble("Invoice.InvoiceTotal");
                    
                    
                %>
                <td><%=num %></td>
                <td><%=code %></td>
                <td><%=name %></td>
                
                <td><%=Qty %></td>
                <td><%=salary %></td>
        </tr>
    </div>
    
<%} %>
<div style="width:100%; padding:0px; float:right;">
     <tr>
     	<th><h3> Subtotal Tax=</h3></th>
     	<th><h3><%=tax %></h3></th>
     </tr>
     <tr>
     	<th><h3> Subtotal Total=</h3></th>
     	<th><h3><%=total %></h3></th>
    </tr>
     <tr>
     	<th><h3> Payment Type=</h3></th>
     	<th><h3><%=pt %></h3></th>
    </tr>
    </div>
    </tbody>
    </table>

    
    
    
    
    
    
    
    
    
    
    
    
    
    

</div>
</body>
</html>