package Items;

import java.util.ArrayList;

public class ItemBean {
	private ArrayList<ItemsDescription> itemList = new ArrayList<ItemsDescription>();
	private double orderTotal =0;
	
	public int getTotalItemCount(){
		return itemList.size();
	}
	
	public void deleteCartItem(int strItemIndex) {
		  int iItemIndex = 0;
		  try {
		   iItemIndex = strItemIndex;
		   itemList.remove(iItemIndex - 1);
		   getTotalItemCount();
		  } catch(NumberFormatException nfe) {
		   System.out.println("Error while deleting cart item: "+nfe.getMessage());
		   nfe.printStackTrace();
		  }
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
			    addCartItem(cartItem);
			   }
			    
			  } catch (NumberFormatException nfe) {
			   System.out.println("Error while parsing from String to primitive types: "+nfe.getMessage());
			   nfe.printStackTrace();
			  }
	}
	public void remove(int a){
		
			this.itemList.remove(a-1);
		
	}
	public void clearCartItem(){
		this.itemList.clear();
	}
	public void addCartItem(ItemsDescription cartItem) {
		itemList.add(cartItem);
		 }
	public ItemsDescription getCartItem(int iItemIndex) {
		ItemsDescription cartItem = null;
		  if(itemList.size()>iItemIndex) {
		   cartItem = (ItemsDescription) itemList.get(iItemIndex);
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
