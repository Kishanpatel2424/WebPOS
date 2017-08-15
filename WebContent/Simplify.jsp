<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="https://www.simplify.com/commerce/v1/simplify.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 
<center>  
    <div class="container">
<form id="simplify-payment-form" class="form-horizontal" action="Payment" method="POST">
    <div class="form-group">
        <label class="control-label col-sm-2">Credit Card Number: </label>
        <div class="col-sm-10">
        <input id="cc-number" type="text" maxlength="20" autocomplete="off" value="" autofocus />
        </div>
    </div>
    <div class="form-group">
        <label class="control-label col-sm-2">CVC: </label>
        <div class="col-sm-10">
        <input id="cc-cvc" type="text" maxlength="4" autocomplete="off" value=""/>
        </div>
    </div>
     <div class="form-group">
        <label class="control-label col-sm-2">Amount: </label>
        <div class="col-sm-10">
        <input type="number" step="0.01" name="amount" maxlength="300" autocomplete="off" value="${TotalTax}"required value=""/>
        </div>
    </div>
    
    <div class="form-group">
        <label class="control-label col-sm-2">Expiry Date: </label><div class="col-sm-10">
        <select id="cc-exp-month">
            <option value="01">Jan</option>
            <option value="02">Feb</option>
            <option value="03">Mar</option>
            <option value="04">Apr</option>
            <option value="05">May</option>
            <option value="06">Jun</option>
            <option value="07">Jul</option>
            <option value="08">Aug</option>
            <option value="09">Sep</option>
            <option value="10">Oct</option>
            <option value="11">Nov</option>
            <option value="12">Dec</option>
        </select>
        <select id="cc-exp-year">
           
            <option value="17">2017</option>
            <option value="18">2018</option>
            <option value="19">2019</option>
            <option value="20">2020</option>
            <option value="21">2021</option>
            <option value="22">2022</option>
            <option value="23">2023</option>
            <option value="24">2024</option>
            <option value="25">2025</option>
        </select>
        </div>
    </div> <div class="form-group">
   <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
         <button id="process-payment-btn" type="submit" >Card Checkout</button>
					<br /> <br /> <br />  <a href="Refund"><button id="process-payment-btn" type="button" >Refund Amount</button></a>
    </div>
  </div>
    
    </div>
</form>
</div>
</center>
<script>
function simplifyResponseHandler(data) {
    var $paymentForm = $("#simplify-payment-form");
    // Remove all previous errors
    $(".error").remove();
    // Check for errors
    if (data.error) {
    	console.log('in error');
        // Show any validation errors
        if (data.error.code == "validation") {
            var fieldErrors = data.error.fieldErrors,
                fieldErrorsLength = fieldErrors.length,
                errorList = "";
            for (var i = 0; i < fieldErrorsLength; i++) {
                errorList += "<div class='error'>Field: '" + fieldErrors[i].field +
                             "' is invalid - " + fieldErrors[i].message + "</div>";
            }
            // Display the errors
            $paymentForm.after(errorList);
        }
        // Re-enable the submit button
        $("#process-payment-btn").removeAttr("disabled");
    } else {
    	console.log('in else part');
        // The token contains id, last4, and card type
        var token = data["id"];
        // Insert the token into the form so it gets submitted to the server
        $paymentForm.append("<input type='hidden' name='simplifyToken' value='" + token + "' />");
        // Submit the form to the server
        $paymentForm.get(0).submit();
    }
}

	$(document).ready(function() {
		$("#simplify-payment-form").on("submit", function() {
			// Disable the submit button
			$("#process-payment-btn").attr("disabled", "disabled");
			// Generate a card token & handle the response
			SimplifyCommerce.generateToken({
				key : "sbpb_YTEwN2U0MWQtMWZlZS00NjczLThkYjAtMmUwNDJmZWQ0N2Uz",
				card : {
					number : $("#cc-number").val(),
					cvc : $("#cc-cvc").val(),
					expMonth : $("#cc-exp-month").val(),
					expYear : $("#cc-exp-year").val()
				}
			}, simplifyResponseHandler);
			// Prevent the form from submitting
			return false;
		});
	});
</script>