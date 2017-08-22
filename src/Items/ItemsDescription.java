package Items;

public class ItemsDescription {
	
	public String iCode;
	public String iName;
	public double iPrice;
	public double iQty;
	public double Tax;
	public double ItemTotal;
	public double total; 
	

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
	//Returns total+tax on each item
	public void setTax(double iPrice, double iQty){
		ItemTotal =Tax-(iQty*iPrice);
		
	}
	public double getTax(){
		
		return ItemTotal;
	}
	//Returns tax on each item
	public void setItemTotalTax(double iPrice, double iQty){
		
		Tax = Math.round((iQty*iPrice)*1.0635*100);
		Tax = Tax/100;
	
	}
	public double getItemTotalTax(){
		return Tax;
	}
	public void setTotal(double calcTotal){
		total = calcTotal;
		
	}
	public double getTotal(){
		System.out.println(this.total+" From Items class");
		
		return this.total;
	}
	
	

}
