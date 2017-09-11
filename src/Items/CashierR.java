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
	
	ItemBean ItemBean = null;
	Object objCartBean=null;
		
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
		System.out.println(ItemCode);
		if(ItemCode != null && !ItemCode.equals("")){
			iCode = ItemCode;
			iQty = 1;
			addToCart(request, response);
			response.sendRedirect("/InsertDataWebApplication/Cashier.jsp");
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
	
	protected void clearCart(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		objCartBean = session.getAttribute("cart");
		ItemBean = (ItemBean) objCartBean ;
		
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
										  
										  session.setAttribute("cart", ItemBean);
									  } else {
										  ItemBean = new ItemBean();
										  
									   session.setAttribute("cart", ItemBean);
									   
									  }
									  
									  ItemBean.addCartItem(iCode, iName, iPrice, iQty);
									  
									  
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
