package Items;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CashierR
 */
@WebServlet("/CashierR")
public class CashierR extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	String iCode = null;
	int iQty;  
	double iPrice =0.0;	
	
	Connection MyConn =null;
	Statement myStmt =null;
	PreparedStatement ps;
	ResultSet rs;
	PreparedStatement insertActor;
	ItemBean ItemBean = null;
	Object objCartBean=null;
	
	double amountDue =0.0;
	double amount_Given =0.0;
	double change_Due =0.0;
	String payment_Type=null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CashierR() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = (String) request.getParameter("action");
		String ItemCode= (String) request.getParameter("ItemCode");
		String paymentProcess = (String) request.getParameter("Process");
		
		
		if(ItemCode != null && !ItemCode.equals("")){
			iCode = ItemCode;
			iQty = 1;
			addToCart(request, response);
			response.sendRedirect("/InsertDataWebApplication/Cashier.jsp");
		}
		if(paymentProcess !=null && !paymentProcess.equals("")){
			HttpSession session = request.getSession();
			objCartBean = session.getAttribute("cart");
			ItemBean = (ItemBean) objCartBean ;
			System.out.println(ItemBean.orderTotal+" Due");
			amountDue = ItemBean.orderTotal;
				if(request.getParameter("Card") !=null && !request.getParameter("Card").equals("")){
					amount_Given = Double.parseDouble((String) request.getParameter("Card"));
					payment_Type = "Card";
				}
				if(request.getParameter("Cash") !=null && !request.getParameter("Cash").equals("")){
					amount_Given = Double.parseDouble((String) request.getParameter("Cash"));
					payment_Type = "Cash";
				}
			System.out.println(request.getParameter("Card")+" Card");
			System.out.println(request.getParameter("Cash")+" Cash");
			change_Due = amount_Given-amountDue;
				if(change_Due>=0.0)
					{	System.out.println(amountDue+" due"+change_Due+" due");
						
						ItemBean.setchangeDue(change_Due);
						createInvoice(request,response);
						change_Due=0;
						payment_Type=null;
						clearCart(request,response);
						response.sendRedirect("/InsertDataWebApplication/Cashier.jsp");
					}
				else if(change_Due<0.0)
					response.sendRedirect("/InsertDataWebApplication/PayPage.jsp");
		}
		if(action!=null && !action.equals("")){
					
			if(action.equals("Enter")){
				iCode = request.getParameter("iCode");
				iQty = Integer.parseInt((request.getParameter("Quantity")));
				addToCart(request, response);
			}
			if(action.equals("Clear")){
				clearCart(request,response);
			}
			if(action.equals("Non Tax")){
				System.out.println("NonTax");
				iCode="NonTax";
				iQty = Integer.parseInt((request.getParameter("Quantity")));
				
				addToCart(request, response);
			}
			if(action.equals("Tax Item")){
				System.out.println("TaxItem");
				iCode="Tax";
				iQty = Integer.parseInt((request.getParameter("Quantity")));
				
				addToCart(request, response);
			}
			if(action.equals("Void #")){
				remove(request,response);
			}
			
			response.sendRedirect("/InsertDataWebApplication/Cashier.jsp");	
		}
		
	}
	
	protected void createInvoice(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		objCartBean = session.getAttribute("cart");
		ItemBean = (ItemBean) objCartBean;
		
			
			try {

				MyConn = ConnectionManager.getConnection();
				myStmt = MyConn.createStatement();
				System.out.println(session.getAttribute("user_id"));
				System.out.println(ItemBean.orderTotal+" ChangeDue "+ItemBean.change_Due+" pType "+payment_Type+" Tax "+ItemBean.orderTotalTax);
				
				
				int InvoiceNumber = 0;
				
				Calendar cal = Calendar.getInstance();
				java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
				
				//Statement
				insertActor = MyConn.prepareStatement("INSERT INTO Invoice(InvoiceTotal,ChangeDue,PaymentType,Tax,Date,user_Id,Status) VALUES(?,?,?,?,?,?,?)");
				insertActor.setDouble(1,ItemBean.orderTotal);
				insertActor.setDouble(2, ItemBean.change_Due);
				insertActor.setString(3,payment_Type);
				insertActor.setDouble(4, ItemBean.orderTotalTax);
				insertActor.setTimestamp(5, timestamp);
				insertActor.setInt(6,(Integer)session.getAttribute("user_id"));
				insertActor.setString(7,"Open");
				
				//System.out.println(ItemBean.orderTotalTax+" Payment Type");
				int result =insertActor.executeUpdate();
					if(result>=0){
						System.out.println("Invoice Created");
					}
				insertActor = MyConn.prepareStatement("SELECT MAX(InvoiceNumber) from Invoice");
				ResultSet rs = insertActor.executeQuery();
				while(rs.next()){
					InvoiceNumber = rs.getInt(1);
					
				}
				System.out.println(InvoiceNumber +" Inv #");
				session.setAttribute("maxInvNum", InvoiceNumber);
				session.setAttribute("timestamp", timestamp);
				System.out.println("Now Creating Inv # Item Details");
				
				for(int i=0;i<ItemBean.getCartItems().size();i++){
					
					insertActor = MyConn.prepareStatement("INSERT INTO InvoiceDetail(InvoiceNumber, ItemCode, ItemName,ItemPrice,Quantity,Tax,ItemTotal,Date,Status,Cashier_Name) VALUES (?,?,?,?,?,?,?,?,?)");
					
					insertActor.setInt(1, InvoiceNumber);
					insertActor.setString(2, ItemBean.getCartItem(i).getiCode());
					insertActor.setString(3, ItemBean.getCartItem(i).getiName());
					insertActor.setDouble(4, ItemBean.getCartItem(i).getiPrice());
					insertActor.setDouble(5, ItemBean.getCartItem(i).getiQty());
					insertActor.setDouble(6, ItemBean.getCartItem(i).getTax());
					insertActor.setDouble(7, ItemBean.getCartItem(i).getTotalTax());
					insertActor.setTimestamp(8, timestamp);
					insertActor.setString(9, "Open");
					insertActor.setString(10, (String)session.getAttribute("Cashier_Name"));
					
					//System.out.println("Item add to InvoideDetail with Inv# "+InvoiceNumber+" Item"+ItemBean.getCartItem(i).getiName()+" Qty"+ItemBean.getCartItem(i).getiQty()+" TTl"+ItemBean.getCartItem(i).getTotalTax()+" tax"+ItemBean.getCartItem(i).getTax());
					result =insertActor.executeUpdate();
					System.out.println(result+" Execute result");
					
					PreparedStatement ps = MyConn.prepareStatement("Update Item set QuantityOnHand =QuantityOnHand-? where ItemCode =?");
					ps.setDouble(1, ItemBean.getCartItem(i).getiQty());
					ps.setString(2, ItemBean.getCartItem(i).getiCode());
					
					ps.executeUpdate();
				}
				
				
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	protected void clearCart(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		objCartBean = session.getAttribute("cart");
		ItemBean = (ItemBean) objCartBean;
		
		ItemBean.clearCartItem();
	}
	
	protected void remove(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		objCartBean = session.getAttribute("cart");
		ItemBean = (ItemBean) objCartBean ;
		  
		int index = (Integer.parseInt((String)request.getParameter("DeleteIndex")));
		System.out.println("index # is "+ index);
		
		if(index >0 && index <= ItemBean.getCartItems().size()){
				ItemBean.remove(index);
			}
		
	}
	
	@SuppressWarnings("null")
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		
		boolean itemFound = true;// for checking the correct value
		 
		
		String iName = null;
		String Check = null;
		
		
		
		
		System.out.println(iCode+" is");
		objCartBean = session.getAttribute("cart");
		int dep =0;
		
			try {

					MyConn = ConnectionManager.getConnection();
				
				 myStmt = MyConn.createStatement();
				
				//myStmt = MyConn.createStatement();
				 Check = ("SELECT COUNT(ItemCode) FROM Item WHERE ItemCode =?");
				  
				   ps = MyConn.prepareStatement(Check);
				  ps.setString(1, iCode);
				  ResultSet results = ps.executeQuery();
				  
					 
					while(results.next()){
				        	if(results.getInt(1)<=0){
				        		itemFound = false;
				        		request.setAttribute("NotExist", "NotExist");
				        		
				        	}
				        	else
				        		itemFound =true;
					}
			if(itemFound == true){
			Check = ("select * From Item where ItemCode =?");
			
			//Statement
			ps = MyConn.prepareStatement(Check);
			ps.setString(1, iCode);
			rs = ps.executeQuery();
				while(rs.next()){
						
						iName= rs.getString("ItemName");
						iPrice=rs.getDouble("ItemPrice");
						dep = rs.getInt("Deposit");
						if(iCode.equals("NonTax")){
							if(!request.getParameter("nonTaxItemValue").equals(""))
								iPrice = Double.parseDouble(request.getParameter("nonTaxItemValue"));
							else
								iPrice = rs.getDouble("ItemPrice");
						}
						if(iCode.equals("Tax")){
							if(!request.getParameter("taxItemValue").equals(""))
								iPrice = Double.parseDouble(request.getParameter("taxItemValue"));
							else
								iPrice = rs.getDouble("ItemPrice");
						}
					}
				
				if(iCode.equals("NonTax"))
					iCode = "NonTax";
					
					
									 
									  if(objCartBean!=null) {
										  ItemBean = (ItemBean) objCartBean ;
										  //ItemBean.getCartItem(0);
										  
										  session.setAttribute("cart", ItemBean);
									  } else {
										  ItemBean = new ItemBean();
										  ItemBean.setchangeDue(0);
										  session.setAttribute("cart", ItemBean);
									   
									  }
									  
									  ItemBean.addCartItem(iCode, iName, iPrice, iQty);
/**************************DEPOSIT ONLY CODE**************************/					
					if(dep >0.0){
						iCode = "Bottle Deposit";
						iPrice = 0.05;
						iName = "Bottle Deposit";
						iQty = dep;
						System.out.println("Deposit inserted "+iCode+" "+" "+iPrice+" "+iQty);
						
					
					objCartBean = session.getAttribute("cart");
					if(objCartBean!=null) {
						  ItemBean = (ItemBean) objCartBean ;
						  //ItemBean.getCartItem(0);
						  
						  session.setAttribute("cart", ItemBean);
					  } else {
						  ItemBean = new ItemBean();
						  ItemBean.setchangeDue(0);
						  session.setAttribute("cart", ItemBean);
					   
					  }
					  dep=0;
					  ItemBean.addCartItem(iCode, iName, iPrice, iQty);	
					}
			/*ItemBean cartItem= (ItemBean) session.getAttribute("cart");
					System.out.println(cartItem.getTotalItemCount()+" size");*/
									  for(ItemsDescription b : ItemBean.getCartItems()){
										 // System.out.println(b.getiName()+" Name"+" "+b.getiPrice()+" Price");
									  }
									  
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		 }
	
	

}
