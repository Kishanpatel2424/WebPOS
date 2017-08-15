
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
<%@ include file = "home.jsp" %>

  <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>

<style>
* {
    box-sizing: border-box;
}

.scrollit {
    overflow:scroll;
    height:430px;
   	/* background: linear-gradient(to top right, #ffffff 10%, #ffff99 80%); */
   	background: linear-gradient(to top, #ffffff -22%, #ffffcc 132%);
   	style="font-size: 20px";
}

tbody div{
    overflow:scroll;
    height:500px;
}
table { table-layout: fixed; }

.td {
    border: 1px solid black;
    background: linear-gradient(to bottom right, #33ccff 0%, #ffffff 100%);

    font-size: 20px;
    
}
table{
	
 	font-size:16pt; 
 	align:center;

}

input[type=submit] {
    
    cursor:pointer;
    -webkit-border-radius: 5px;
    border-radius: 5px; 
    font-size : 20px;
    width:100%;
    height:100%;
    background: linear-gradient(to bottom right, #33ccff 0%, #ffffff 100%);
   
}

input[type=text]{
	font-size: 25px; 
	width: 30px; 
	height: 30px; 
	border:1px solid black";
}
</style>
</head>

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
	<center>
<form id="MyForm" name="MyForm" action="/InsertDataWebApplication/Cashier" method="POST" style="font-size:18p;">
 <div class="table-responsive">
<TABLE border="1" class="table" CELLSPACING=3 CELLPADDING=6 style="width:90%"> 

	<tr>
		<td style=" color:Black;" class="td" >Qty:
			<input type="text" value="1"  name="Quantity"/>
		</td>
			
		<td style="font-size:30px; background: linear-gradient(to bottom, #ffffff 0%, #c0c0c0 100%); width:80%" align="center" class="td" >Scan Bar Code:<br>
			<input type="text" id="iCode" name=iCode value="" style="width:20%; height:40px" class="form-control" />
			
		</td>
			
		<td class="td">
			<input type="submit" value="Enter" name="Search"/>
		</td>
	</tr>
	<TR> 
		<td class="td" align="center"><input type="submit"  value="Void #" name="Delete" style="color:#FF0000; width:80%; height:50px">
		<input type="text" value="" size="1" name="DeleteIndex" /></td>
		
		<TD class="td2" ROWSPAN=8 valign="top">
		
			<table  border="1" style="width:100%">
				<tr >
					<td style="width:5%; padding:10px">#</td>
					<td style="width:15%; padding:10px">Bar Code</td>
					<td style="width:35%">Item Name</td>
					<td style="width:10%">Price</td>
					<td style="width:10%">Qty</td>
					<td style="width:10%">Tax</td>
					<td style="width:15%">Total</td>
					
					
				</tr>
			</table>
			<div class="scrollit" >
			<table border="1" style="width:100%">
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
					        <tr height="10px"> 
					            <td style="width:5%; padding:5px">${Number}</td>
					            <td style="width:15%">${item.getiCode()}</td>
					            <td style="width:35%">${ItemName}</td>
					            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${item.getiPrice()}" /></td>
					            <td style="width:10%">${Quantity}</td>
					            <td style="width:10%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${(ItemPrice*1.0635 -ItemPrice)}" /></td>
								<td style="width:15%"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${(ItemPrice*1.0635)}" /></td>					            
					        
					        </tr>
			            </c:forEach>
			        </c:otherwise>
				</c:choose>
			</table>
			</div>
		</TD>	
			<td class="td"><input type =submit value="Clear" name="Clear" style=" color:#FF0000"/></td> 
	</TR> 
	<TR> 
		<td class="td">(cell 8)</td>
		<td class="td">(cell 9)</TD>
	</TR>
	 
	<TR> 
		<td class="td">(cell 10)</TD> 
		<td class="td">(cell 11)</TD>
	</TR>  
	
	<TR> 
		<td class="td">(cell 14)</TD> 
		
		<td class="td" style="color:Red"> Change $ ${due}
		</td>
	</TR> 
	<TR> 
		<td class="td"></td>
		<td class="td" >
			<input type="submit" Value="PAY" name="Tender">
		</TD>
	</TR>
<tr>
<div>
	<TABLE class="table" style="font-size:18pt; background-color:black; width:80%" >
	<tr style="width:20%; color:#00FFFF">
		
		<td> Total Non Tax:- </td>
		<td> <fmt:formatNumber type = "currency" pattern = "####.##" value = "${TotalNonTax}" /></td>
		
		
	</tr>
	<tr style="width:20%; color:#00FFFF">
		<td>Tax:-</td>
		<td><fmt:formatNumber type = "currency" pattern = "####.##" value = "${TotalNonTax*1.0635-TotalNonTax}" /></td>
		
	</tr>
	<tr style="width:20%; color:red">
	
		<td style=" font-size:25pt;">Total:-</td>
		<td style=" font-size:25pt; width:150px"><fmt:formatNumber type = "currency" pattern = "####.##" value = "${TotalTax}" /></td>
		
	</tr>
	
	</TABLE>
	</div>
	</tr>
</TABLE>
</div>

</form>
</center>
</div>
</body>
</html>