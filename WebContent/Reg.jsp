<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %> 
<%@ page import="java.lang.*" %> 
<%@ page import="java.util.Date" %>
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
	<form id="MyForm" name="MyForm" action="/InsertDataWebApplication/Cashier" method="POST" >
	<div class="table-responsive" class="ex1">
	<table class="table" id="item" >
		<tr>
			<th style="width:5%">
			Qty<input type="text" id="Quantity" name="Quantity" value="1" class="form-control"/>
			</th>
			<th style="width:25%;">
				Scan Bar Code:-<input type="submit" value="Enter" name="Search" style=" visibility: hidden; height:1px"/>
				<input type="text" id="iCode" name=iCode value="" size="10" placeholder="Scan BarCode" class="form-control"/>	
			</th>
			
			<th style="width:10%;">	
				
				<input type="submit"  value="Void #" name="Delete" class="form-control">
				<input type="text" id="Quantity" name="DeleteIndex" value="" placeholder="Void Index #" class="form-control"/>
			</th>
			<th style="width:10%;">	
				Clear List:-
				<input type ="submit" value="Clear" name="Clear" class="form-control"/>
			</th>
			<th style="width:10%;">
			
				Total $  
				<fmt:formatNumber type="number" maxFractionDigits="2" value="${Totalsum}" />
				<input type="submit" Value="PAY" name="Tender" class="form-control">
			</th>
			
			<th style="width:10%;">
				Change Due $ <br>
				 ${due} 
				 
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
							
							<c:choose>
							 	 <c:when test="${empty itemList}">
						         <c:set var="TotalNonTax" value="${0}" /> 
  
					         
					        </c:when>
					        
						        <c:otherwise>
								     <c:forEach var="item" items="${itemList}">
								        <c:set var="Number" value="${Number+1}"></c:set>
								        <%-- Not Needed 
								        <c:set var="ItemName" value="${item.getiName()}"/> --%>
								        <%-- <c:set var="Quantity" value="${item.getiQty()}"/> --%>
								        <%-- <c:set var="ItemPrice" value="${item.getiQty()*item.getiPrice()}"/> --%>
								        <%-- <c:set var="TotalNonTax" value="${TotalNonTax+ItemPrice}"/> Not Needed --%>
								        
								         
								        <tr> 
								            <td style="width:4%">${Number}</td>
								            <td style="width:14%">${item.getiCode()}</td>
								            <td style="width:46%">${item.getiName()}</td>
								            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${item.getiPrice()}" /></td>
								            <td style="width:6%">${item.getiQty()}</td>
								            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${item.getTax()}" /></td>
											<td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${item.getItemTotalTax()}" /></td>					            
								        
								        </tr>
						            </c:forEach> 
						        </c:otherwise>
						        
							</c:choose>
						</table>
					</div>
				</tr>
		 	<div class="table-responsive" class="ex1">
	<table class="table" id="item" >
		<tr>
			<th style="width:15%;">
				<input type="submit" Value="Non Tax" name="nonTaxItem" id="nonTaxItem" class="form-control">
				<input type="number" step="0.01" id="nonTaxItem" name="nonTaxItemValue" value="" placeholder="Price $" class="form-control"/>
			</th>
			<th style="width:15%;">
				<input type="submit" Value="Tax Item"name="taxItem"  class="form-control">
				<input type="number" step="0.01" id="taxItem" name="taxItemValue" value="" placeholder="Price $" class="form-control"/>
			</th>
			
			<th style="width:15%;">
				<!-- <button class="form-control"><a href="/InsertDataWebApplication/SearchList.jsp">Search Item</a></button> -->
				Search Item:-<br>
				<button type="button" class=" form-control">
      				<span class="glyphicon glyphicon-search"></span><a href="/InsertDataWebApplication/SearchList.jsp"> Search
   				 </button>
			</th>
			<th style="width:10%;">
				Sale Reciepts:-
				
			</th>
			<th style="width:10%;">
				<input type="submit" Value="Discount %"name="Discount" class="form-control">
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