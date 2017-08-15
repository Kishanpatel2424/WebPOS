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
				<input type="text" id="iCode" name=iCode value="" size="10" placeholder="Scan BarCode"  class="form-control"/>	
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
				Total $  ${TotalTax}
				<input type="submit" Value="PAY" name="Tender" class="form-control">
			</th>
			
			<th style="width:10%;">
				Change Due $ <br>
				 ${due} 
				 
			</th>
			<th style="width:10%;">
				Print Reciept<br>
				<a href ="/InsertDataWebApplication/Invoice_1.pdf" target="_blank"><input type="button" Value="Reciept" name="Reciept" class="form-control"></a>
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
						         <c:set var="Tax" value="${0}"/>
						         <c:set var="Total" value="${0}"/>
						         <c:set var="Number" value="${0}"/>
					         
					        </c:when>
						        <c:otherwise>
								    <c:forEach var="item" items="${itemList}">
								        <c:set var="Number" value="${Number+1}"></c:set>
								        <c:set var="ItemName" value="${item.getiName()}"/>
								        <c:set var="Quantity" value="${item.getiQty()}"/>
								        <c:set var="ItemPrice" value="${Quantity*item.getiPrice()}"/>
								        <c:set var="TotalNonTax" value="${TotalNonTax+ItemPrice}"/> 
								        
								         
								        <tr> 
								            <td style="width:4%">${Number}</td>
								            <td style="width:14%">${item.getiCode()}</td>
								            <td style="width:46%">${ItemName}</td>
								            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${item.getiPrice()}" /></td>
								            <td style="width:6%">${Quantity}</td>
								            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${(ItemPrice*1.0635 -ItemPrice)}" /></td>
											<td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${(ItemPrice*1.0635)}" /></td>					            
								        
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
				Item NonTax:-
				<input type="number" step="0.01" id="NonTax" name="DeleteIndex" value="" placeholder="Price $" class="form-control"/>
			</th>
			<th style="width:15%;">
				Item Tax:-
				<input type="number" step="0.01" id="Tax" name="DeleteIndex" value="" placeholder="Price $" class="form-control"/>
			</th>
			
			<th style="width:15%;">
				<button class="form-control"><a href="/InsertDataWebApplication/SearchList.jsp">Search Item</a></button>
			</th>
			<th style="width:15%;">
				Sale Reciepts:-
				
			</th>
			<th style="width:10%;">
				Refund:-
				<input type="text" id="Quantity" name="DeleteIndex" value="" placeholder="Price $" class="form-control"/>
			</th>
			
			<th style="width:10%;">
				Bottle Refund:-
				<input type="text" id="Quantity" name="DeleteIndex" value="" placeholder="Price $" class="form-control"/>
			</th>
			
		</tr>
		</table>
	</div>
	</form>
	
</div>
</body>
</html>