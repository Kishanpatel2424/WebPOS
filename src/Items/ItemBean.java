package Items;

import java.util.ArrayList;

public class ItemBean {
	private ArrayList<ItemsDescription> itemList = new ArrayList<ItemsDescription>();
	public double orderTotal, orderTotalTax =0.0;
	public double change_Due =0;
	private double itemTotalRemove, itemTotalRemoveTax =0.0;
	private double dummy=0;
	public int getTotalItemCount(){
		return itemList.size();
	}
	public void setchangeDue(double changeDue){
		dummy = Math.round((changeDue*1.0)*100);
		changeDue = dummy/100.0;
		this.change_Due = changeDue;
		System.out.println("Change Due "+change_Due);
	}
	public double getChangeDue(){
		return this.change_Due;
	}
	
	public void addCartItem(String ItemCode, String ItemName,
			double ItemPrice, int Quantity) {
			 
			  
			  ItemsDescription cartItem = new ItemsDescription();
			  try {
			   
			   if(Quantity>0) {
			    
			    cartItem.setiCode(ItemCode);
			    cartItem.setiName(ItemName);
			    cartItem.setiPrice(ItemPrice);
			    cartItem.setiQty(Quantity);
			    cartItem.setItemTotalTax(ItemCode, ItemPrice, Quantity);
			    cartItem.setTax(ItemCode, ItemPrice, Quantity);
			    Total(cartItem.getItemTotalTax());
			    TotalTax(cartItem.getTax());
			    addCartItem(cartItem);
			   }
			    orderTotal += cartItem.getItemTotalTax();
			    orderTotalTax += cartItem.getTax();
			    System.out.println(orderTotal+" from addtocart + total tax"+orderTotalTax);
			  } catch (NumberFormatException nfe) {
			   System.out.println("Error while parsing from String to primitive types: "+nfe.getMessage());
			   nfe.printStackTrace();
			  }
	}
	
	public void addCartItem(ItemsDescription cartItem) {
		itemList.add(cartItem);
	}
	
	
	public void remove(int a){
		itemTotalRemove=itemList.get(a-1).getItemTotalTax();
		itemTotalRemoveTax=itemList.get(a-1).getTax();
		System.out.println(itemTotalRemove+" to be removed + tax to remove is "+itemTotalRemoveTax);
		this.itemList.remove(a-1);
		getOrderTotal();
	}
	
	public void clearCartItem(){
		this.itemList.clear();
		this.orderTotal =0;
		this.orderTotalTax=0;
		
	}
	
	
	
	public ItemsDescription getCartItem(int iItemIndex) {
		ItemsDescription cartItem = null;
		  if(itemList.size()>=iItemIndex) {
		   cartItem = (ItemsDescription) itemList.get(iItemIndex);
		   System.out.println(cartItem.iCode+" Code Name is "+cartItem.iName);
		  }
		  return cartItem;
		 }
	public ArrayList<ItemsDescription> getCartItems() {
		//System.out.println(itemList);  
		return itemList;
		 }
	 public void setCartItems(ArrayList<ItemsDescription> alCartItems) {
		  this.itemList = alCartItems;
		 }
	 public double getOrderTotal() {
		 
		 System.out.println(itemList.size()+" size is "+itemTotalRemove);
		 for(int i=0;i<this.itemList.size();i++){
			 orderTotal += itemList.get(i).getTotalTax(); 
			 orderTotalTax += itemList.get(i).getTax();
			
		 }
		 
			 orderTotal = orderTotal-itemTotalRemove;
			 orderTotalTax = orderTotalTax-itemTotalRemoveTax;
		 
		 itemTotalRemove=0;
		 itemTotalRemoveTax=0;
		 System.out.println(orderTotalTax+" tax from Bean removed value");
		 
		 return this.orderTotal;
		 }

		 
	static double Totalsum = 0;
	public static double Total (double iPrice){
		
		
		if(iPrice!=-1){
			Totalsum+=iPrice;
			
		}
			else if(iPrice>=-1 && iPrice<0)
			{
				Totalsum=0;
				//System.out.println(Total);
				return Totalsum=0;
			}
	
	return Totalsum;
}
static double TotalsumTax = 0;
public static double TotalTax (double tax){
	
	
	if(tax!=-1){
		TotalsumTax+=tax;
		
	}
		else if(tax>=-1 && tax<0)
		{
			TotalsumTax=0;
			//System.out.println(Total);
			return TotalsumTax=0;
		}

return TotalsumTax;
}
}
