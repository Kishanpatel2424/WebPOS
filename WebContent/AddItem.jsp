
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

<title>Add Item</title>

</head>
<body onload="ClearForm()">
<center><h1>Add Item</h1></center>

<%
	String Message = (String)session.getAttribute("Message");
	
	if(Message == ("Successfull"))	{
		%>
		 <script type="text/javascript">
	    var msg = "<%=Message%>";
	    alert(msg);
	</script><%
	}
	else if(Message == "Exist"){
	%>
		 <script type="text/javascript">
	    var msg = "Item Already Exist";
	    alert(msg);
	</script><%
	}
%>
<%
String userName = null;
//allow access only if session exists
if(session.getAttribute("user") == null){
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


<script type="text/javascript"> 
  function tabE(obj,e){ 
   var e=(typeof event!='undefined')?window.event:e;// IE : Moz 
   if(e.keyCode==13){ 
     var ele = document.forms[0].elements; 
     for(var i=0;i<ele.length;i++){ 
       var q=(i==ele.length-1)?0:i+1;// if last element : if any other 
       if(obj==ele[i]){ele[q].focus();break} 
     } 
  return false; 
   } 
  } </script>
  <script type="text/javascript">
  function sum() {
	     var txtFirstNumberValue = document.getElementById('iCCost').value;
	     var txtSecondNumberValue = document.getElementById('iCQ').value;
	     var result = (parseFloat(txtFirstNumberValue) / parseFloat(txtSecondNumberValue)).toFixed(2);
	     if (!isNaN(result)) {
	    	 if(document.getElementById('iCCost').value!=null)
	    		 {
	    		 document.getElementById('iCost').value = result;
	    		 }
	    	 else
	         	document.getElementById('iCost').value = result;
	     }
	     
	     var iCost = document.getElementById('iCost').value;
	     var iPrice = document.getElementById('iPrice').value;
	     var result1 = (parseFloat(iPrice-iCost) / parseFloat(iPrice));
	     result1	=	(result1*100.0).toFixed(2);
	     
	     if (!isNaN(result1)) {
	    	 if(document.getElementById('iCost').value!=null)
	    		 {
	    		 document.getElementById('GP').value = result1;
	    		 }
	    	 else
	         	document.getElementById('GP').value = result1;
	     }
	     
	 }
	
  function sumGP() {
	     
	 }

</script>

<body>
	

<div class="container">

<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
		<form name="MyForm" action="/InsertDataWebApplication/AddItems" method="POST" role="form">
			<h2>Add a New Item </h2>
			<hr class="colorgraph">
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group">Scan BarCode
                        <input type="text" name="iCode" value=""  onkeypress="return tabE(this,event)" class="form-control input-lg" placeholder="Barcode" tabindex="1">
					</div>
				</div>
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group">Min Quantity
                        <input type="number" name="Min_Qty" value="0"  class="form-control input-lg" placeholder="Min Qty Allowed" tabindex="2">
					</div>
				</div>
			</div>
			<div class ="row">
				<div class="col-xs-12 col-sm-9 col-md-9">
					<div class="form-group">Item Name
						<input type="text" name="iName" value="" class="form-control input-lg" placeholder="Product Name" tabindex="3">
					</div>
				</div>
				<div class="col-xs-12 col-sm-3 col-md-3">
					<div class="form-group">Deposit Qty.
						<input type="number" name="Deposit" value="" class="form-control input-lg" placeholder="Qty." tabindex="3">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group"> Choose Vendor
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
				</div>
				
				<div class="col-xs-12 col-sm-6 col-md-6">
					<div class="form-group"> Choose Department
					<select name="Department" class="form-control input-lg" placeholder="Departement" tabindex="4">
						<%
						query = "SELECT Department_Name FROM Departements";
		                rs = null;
		                rs = stmt.executeQuery(query);
		                num =0;
                while(rs.next()){
                	num++;
                
            %>
							<option value="<%=rs.getString("Department_Name") %>"><%=rs.getString("Department_Name") %></option>
				<%} 
					%>
					</select>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-xs-12 col-sm-4 col-md-4">
					<div class="form-group">Item Cost
						<input type="number" step=0.01 id="iCost" name="iCost"  class="form-control input-lg" placeholder="Item Cost" tabindex="5">
					</div>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4">
					<div class="form-group">Case Cost
						<input type="number" step=0.01 id="iCCost" name="iCCost" onkeyup="sum()" class="form-control input-lg" placeholder="Case Cost" tabindex="6">
					</div>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4">
					<div class="form-group">Case Qty
						<input type="number" id="iCQ" name="iCQ" onkeyup="sum()" class="form-control input-lg" placeholder="Case Qty" tabindex="7">
					</div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-xs-12 col-sm-4 col-md-4">
					<div class="form-group">Sell Price
						<input type="number" step=0.01 id="iPrice" name="iPrice"  onKeyUp="sum()" class="form-control input-lg" placeholder="Item Sell Price" tabindex="8">
					</div>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4">
					<div class="form-group">On Hand Qty
						<input type="number" id="OtyOnHand" name="OtyOnHand" onkeyup="" class="form-control input-lg" placeholder="Qty On Hand" tabindex="9">
					</div>
				</div>
				<div class="col-xs-12 col-sm-4 col-md-4">
					<div class="form-group">GP Margin
						<input type="number" step=0.01 id="GP" name="GP" onkeyup="" class="form-control input-lg" placeholder="Gross Profit" tabindex="10">
					</div>
				</div>
			</div>
			
			<hr class="colorgraph">
			<div class="row">
				<div class="col-xs-12 col-md-6"><input type="submit" value="ADD" name="Addsubmit" class="btn btn-primary btn-block btn-lg"></div>
				<div class="col-xs-12 col-md-6"><input type="reset" value="Clear" name="clear" class="btn btn-success btn-block btn-lg"/></div>
			</div>
		</form>
		</div>
	</div>
</div>
</body>
</html>