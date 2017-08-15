<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://www.simplify.com/commerce/v1/simplify.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<div class="container" style="margin-top: 30px">
<form id="simplify-payment-form" class="form-horizontal" action="Refund" method="POST">
    <div class="form-group">
        <label class="control-label col-sm-2">Amount: </label>
        <div class="col-sm-10">
        <input id="amount" step="0.01" name="amount" type="number" maxlength="20" required="required" autocomplete="off" value="" autofocus />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2">Payment Id: </label>
        <div class="col-sm-10">
        <input id="paymentId" name="paymentId" type="text"  required="required" maxlength="20" autocomplete="off" value=""/>
        </div>
    </div>
     
   
     <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
         <button id="process-payment-btn" type="submit" >Refund</button>
		
    </div>
  </div>
    
    </form>
    </div>