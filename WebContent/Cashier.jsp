<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %> 
<%@ page import="java.lang.*" %> 
<%@ page import="java.util.Date" %>
<%@ page import="Items.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES">
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%@ include file = "home.jsp" %>

  <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  

<title>Register</title>
<link href="<c:url value="/css/Reg.css"/>" rel="stylesheet">
</head>
<style>

</style>
<body onload="document.MyForm.iCode.focus();">
<%
String userName = null;
//allow access only if session exists
if(session.getAttribute("user") == null){
	response.sendRedirect("/InsertDataWebApplication/index.jsp");
}else userName = (String) session.getAttribute("user");
String sessionID = null;



ItemBean ItemBean =null;
double total=0;
double change_Due=0;

Object objCartBean = session.getAttribute("cart");
if(objCartBean !=null){
	 ItemBean = (ItemBean) objCartBean ;
	 total = ItemBean.orderTotal;
	 change_Due=ItemBean.change_Due;
	
}

%>
<%
 
String succ = (String)request.getAttribute("Paid");
String NotExist = (String)request.getAttribute("NotExist");
	String Empty = (String)request.getAttribute("BarCodeEmpty");
	if(succ!= null){
		%>
		 <script type="text/javascript">
	    var msg = "<%=succ%>";
	    alert(msg);
	</script><%
	}
	if(NotExist!=null){
		%>
			 <script type="text/javascript">
		    var msg = "Item Not Exist";
		    alert(msg);
		</script><%
		}
	if(Empty!=null){
		%>
			 <script type="text/javascript">
		    var msg = "Enter Barcode";
		    alert(msg);
		</script><%
		}
%>

<div class="container-center">
	<form id="MyForm" name="MyForm" action="/InsertDataWebApplication/CashierR" method="POST" >
	<div class="table-responsive" class="ex1">
	<table class="table" id="item" >
		<tr>
			<th style="width:5%">
			Qty<input type="number" id="Quantity" name="Quantity" value="1" class="form-control"/>
			</th>
			<th style="width:25%;">
				Scan Bar Code:-<input type="submit" value="Enter" name="action" style=" visibility: hidden; height:1px"/>
				<input type="text" id="iCode" name=iCode value="" size="10" placeholder="Scan BarCode" class="form-control"/>	
			</th>
			
			<th style="width:10%;">	
				
				<input type="submit"  value="Void #" name="action" class="form-control">
				<input type="text" id="Quantity" name="DeleteIndex" value="" placeholder="Void Index #" class="form-control"/>
			</th>
			<th style="width:10%;">	
				Clear List:-
				<input type ="submit" value="Clear" name="action" class="form-control"/>
			</th>
			<th style="width:10%;">
			
				Total $  
				<fmt:formatNumber type="number" maxFractionDigits="2" value="<%=total%>" />
				<br>
				<a href="/InsertDataWebApplication/PayPage.jsp"><input type="button" Value="Pay" name="Pay" class="form-control"></a>
			</th>
			
			<th style="width:10%;">
				Change Due $ <br>
				 <%=change_Due %>
				 
			</th>
			<th style="width:10%;">
				Print Reciept<br>
				<a href ="/InsertDataWebApplication/Invoice_${maxInvNum}.pdf" target="_blank"><input type="button" Value="Reciept" name="Reciept" class="form-control"></a>
				
			</th>
		</tr>
		</table>
	</div>
		 <div class="table-responsive">
		 	<table class="table" id="item" >
		 		<tr>
						<th style="width:4%">#</th>
						<th style="width:14%">Bar Code</th>
						<th style="width:46%">Item Name</th>
						<th style="width:10%">Price</th>
						<th style="width:6%">Qty</th>
						<th style="width:10%">Tax</th>
						<th style="width:10%">Total</th>
						
				</tr>
			</table>
		 </div>
				<tr>
				<div class="scrollit" >
						<table class="table" id="item">
							
						        <%
						        int number =0;
						        	if(objCartBean !=null){
						       	 		ItemBean = (ItemBean) objCartBean ;	 
						        	for(ItemsDescription b : ItemBean.getCartItems()){
										number++;
										//System.out.println(b.getiCode()+" Price "+b.getiName()+" Name "+b.getiPrice()+" Price");
						        		//System.out.println(b.getiName()+" Name"+" "+b.getiPrice()+" Price");
									  //}
						        %>
								     
								        <%-- Not Needed 
								        <c:set var="ItemName" value="${item.getiName()}"/> --%>
								        <%-- <c:set var="Quantity" value="${item.getiQty()}"/> --%>
								        <%-- <c:set var="ItemPrice" value="${item.getiQty()*item.getiPrice()}"/> --%>
								        <%-- <c:set var="TotalNonTax" value="${TotalNonTax+ItemPrice}"/> Not Needed --%>
								        
								         
								        <tr> 
								        
								            <td style="width:4%"><%=number%></td>
								            <td style="width:14%"><%=b.getiCode() %></td>
								            <td style="width:46%"><%=b.getiName() %></td>
								            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "<%=b.getiPrice() %>" /></td>
								            <td style="width:6%"><%=b.getiQty() %></td>
								            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "<%=b.getTax() %>" /></td>
											<td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "<%=b.getItemTotalTax() %>" /></td>					            
								        
								        </tr>
						            
						        <%}
						        	
						        	}%>
						        
							
						</table>
					</div>
				</tr>
		 	<div class="table-responsive" class="ex1">
	<table class="table" id="item" >
		<tr>
			<th style="width:15%;">
				<input type="submit" Value="Non Tax" name="action" id="nonTaxItem" class="form-control">
				<input type="number" step="0.01" id="nonTaxItem" name="nonTaxItemValue" value="" placeholder="Price $" class="form-control"/>
			</th>
			<th style="width:15%;">
				<input type="submit" Value="Tax Item"name="action"  class="form-control">
				<input type="number" step="0.01" id="taxItem" name="taxItemValue" value="" placeholder="Price $" class="form-control"/>
			</th>
			
			<th style="width:15%;">
				<!-- <button class="form-control"><a href="/InsertDataWebApplication/SearchList.jsp">Search Item</a></button> -->
				Search Item:-<br>
				
      				<a href="/InsertDataWebApplication/SearchList.jsp"><input type="button" Value="Search Item" name="SearchItem" class="form-control">
      				</a>
   				 
			</th>
			<th style="width:10%;">
				Sale Reciepts:-
				
			</th>
			<th style="width:10%;">
				<input type="submit" Value="Discount %"name="action" class="form-control">
				<input type="number" id="Discount" name="Discount" value="" placeholder="Discount %" class="form-control"/>
			</th>
			<th style="width:10%;">
				Refund:-
				<input type="text" id="Quantity" name="DeleteIndex" value="" placeholder="Price $" class="form-control"/>
			</th>
			
			<th style="width:10%;">
				Bottle Refund:-
				<input type="text" id="Quantity" name="DeleteIndex" value="" placeholder="Qty" class="form-control"/>
			</th>
			
		</tr>
		</table>
	</div>
	</form>
	
</div>

</body>
</html>