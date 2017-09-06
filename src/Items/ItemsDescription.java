package Items;

public class ItemsDescription {
	
	public String iCode;
	public String iName;
	public double iPrice;
	public double iQty;
	public double Tax;
	public double ItemTotal;
	public double total; 
	public double totalTax;
	

	public void setiCode(String ItemCode){
		iCode = ItemCode;
	}
	public String getiCode(){
		return iCode;
	}
	public void setiName(String ItemName){
		iName = ItemName;
	}
	public String getiName(){
		return iName;
	}
	public void setiPrice(double ItemPrice){
		iPrice = ItemPrice;
	}
	public double getiPrice(){
		return iPrice;
	}
	public void setiQty(double Quantity){
		iQty = Quantity;
	}
	public double getiQty(){
		return iQty;
	}
	//Returns tax on each item
	
	public void setTax(String iCode, double iPrice, double iQty){
		if(iCode == "NonTax" || iCode == "Bottle Deposit" || iCode == "Return Deposit"){
			this.ItemTotal =0;
			System.out.println(this.ItemTotal+" From Item Desc");
		}
		else{
			this.ItemTotal =this.Tax-(iQty*iPrice);
			System.out.println(this.ItemTotal+" From Item Desc");
		}
		
	}
	public double getTax(){
		
		return ItemTotal;
	}
	//Returns total+tax on each item
	public void setItemTotalTax(String iCode, double iPrice, double iQty){
		if(iCode == "NonTax" || iCode == "Bottle Deposit" || iCode == "Return Deposit"){
			Tax = Math.round((iQty*iPrice)*100.0);
			Tax = Tax/100;
		}
		else{
			Tax = Math.round((iQty*iPrice)*1.0635*100);
			Tax = Tax/100;
		}
	
	}
	public double getItemTotalTax(){
		return Tax;
	}
	public void setTotal(double calcTotal){
		total = calcTotal;
		
	}
	public double getTotal(){
		//System.out.println(this.total+" From Items class");
		
		return this.total;
	}
	public void setTotalTax(double calcTotalTax){
		totalTax = calcTotalTax;
		
	}
	public double getTotalTax(){
		//System.out.println(this.totalTax+" From Items class tax");
		
		return this.totalTax;
	}
	

}
