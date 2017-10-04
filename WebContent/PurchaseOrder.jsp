 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.mysql.*" %>
<%@ page import="java.sql.*" %>
<%@ include file = "home.jsp" %>
<%@ page import="Items.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Purchase Order</title>

</head>

<body>

<%

ItemBean VendorBean =null;

Object objVendorBean = session.getAttribute("Vendor_List");
if(objVendorBean !=null){
	VendorBean = (ItemBean) objVendorBean ;
	
}
%>
<center><h1>Purchase Order</h1></center>
<div class="container">
<div class="row">
				
				<form name="MyForm" action="/InsertDataWebApplication/PurchaseOrder" method="POST" role="form">
				<table class="table" id="item">
				<tr>
				<th style="width:20%">
				<div class="form-group" style="font-size:20px"> Choose Vendor</div>
				</th>
				<th style="width:40%">
				<div class="col-xs-12 col-sm-12 col-md-12">
				
				 <%
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = null;
                conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/test", "root", "root");
                //conn = DriverManager.getConnection("jdbc:mysql://node23485-onlinepos.njs.jelastic.vps-host.net /test","root","BPNivr47456");
                Statement stmt = null;
                stmt = conn.createStatement();
                String SearchList =request.getParameter("SearchList");
                String query = "SELECT Vendor_Name FROM Vendors";
                ResultSet rs = null;
                rs = stmt.executeQuery(query);
                int num =0;
                %>
               
				
						<select name=Vendor_Name class="form-control input-lg" placeholder="Vendors" tabindex="4">
							<%
		                while(rs.next()){
		                	num++;
		                
		            		%>
									<option value="<%=rs.getString("Vendor_Name") %>"><%=rs.getString("Vendor_Name") %></option>
						<%} 
						
		                %>
						</select>
						</div>
						
						</th>
				<th style="width:40%">
				<div class="col-xs-12 col-sm-4 col-md-4">
				<input type="submit" value="Select" name="Select" class="btn btn-primary btn-block btn-lg"></div>
				</th>
				</tr>
				</table>
				<table class="table" id="item">
				<tr style="font-size:20px; color:red">
					<td style="width:4%">#</td>
					<td style="width:14%">Bar Code</td>
					<td style="width:40%">Item Name</td>
					<td style="width:10%">Item Cost</td>
					<td style="width:10%">Min Qty</td>
					<td style="width:15%">Qty On Hand</td>
					
				</tr>
				<tr>
				<%
						        int number =0;
						        	if(objVendorBean !=null){
						        		VendorBean = (ItemBean) objVendorBean ;	 
						        	for(ItemsDescription b : VendorBean.getVendorItemList()){
										number++;
										System.out.println(b.getiName()+" "+b.getiCost()+" "+b.getminQty()+" "+b.getminQty());
										//System.out.println(b.getiCode()+" Price "+b.getiName()+" Name "+b.getiPrice()+" Price");
						        		//System.out.println(b.getiName()+" Name"+" "+b.getiPrice()+" Price");
									  //}
				%>
				<div class="inner_table">      
								         
								        <tr> 
								        
								            <td style="width:4%"><%=number%></td>
								            <td style="width:14%"><%=b.getiCode() %></td>
								            <td style="width:46%"><%=b.getiName() %></td>
								            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "<%=b.getiCost() %>" /></td>
								            <td style="width:6%"><%=b.getminQty()%></td>
								            <td style="width:10%"><%=b.getQtyOnHand() %></td>
																            
								        
								        </tr>
						            </div>
						        <%}
						        	
						        	}%>
				</tr>
				</table>
				</form>
				
			
			</div>
			</div>
</body>
</html>