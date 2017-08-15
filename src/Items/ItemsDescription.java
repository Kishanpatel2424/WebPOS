package Items;

public class ItemsDescription {
	
	public String iCode;
	public String iName;
	public double iPrice;
	public double iQty;
	public double Tax;
	public double ItemTotal;
	 
	

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
	
	public void setTax(double Tax){
		Tax = Tax;
	}
	public double getTax(){
		return Tax;
	}
	public void setItemTotal(double ItemTotal){
		ItemTotal = ItemTotal;
	}
	public double getItemTotal(){
		return ItemTotal;
	}

}
