package Items;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PurchaseOrder
 */
@WebServlet("/PurchaseOrder")
public class PurchaseOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	Connection MyConn=null;
	PreparedStatement ps;
	String query=null;
	ItemBean VendorBean = null;//Saving all vendor Items
	Object objVendorBean =null;
	ResultSet rs;
	String vendor_Name =null;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		vendor_Name = (String)request.getParameter("Vendor_Name");
		
		if(vendor_Name != null && !vendor_Name.equals("")){
			//clearVendorItem(request, response);	
			findVendorItems(request, response);
			
		}
		request.getRequestDispatcher("/PurchaseOrder.jsp").forward(request, response);
	
	}
	protected void clearVendorItem(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		objVendorBean = session.getAttribute("Vendor_List");
		VendorBean = (ItemBean)objVendorBean;
		
		VendorBean.clearVendorItem();
	}
	protected void findVendorItems(HttpServletRequest request, HttpServletResponse response){
		
		HttpSession session = request.getSession();
		if(vendor_Name !=request.getParameter("Vendor_Name")){
			VendorBean.clearVendorItem();
		}
		
		
		
		vendor_Name = request.getParameter("Vendor_Name");
		
		//VendorBean.clearVendorItem();
		
		try{
			MyConn = ConnectionManager.getConnection();
			
			query = ("SELECT * FROM Item WHERE Vendor_Name =? ORDER BY Item.ItemName ASC");
			ps = MyConn.prepareStatement(query);
			ps.setString(1, vendor_Name);
			
			
			rs = ps.executeQuery();
				
			objVendorBean = session.getAttribute("Vendor_List");
			System.out.println(vendor_Name);
			while(rs.next()){
				
				String iCode = rs.getString("ItemCode");
				String iName = rs.getString("ItemName");
				double iCost = rs.getDouble("ItemCost");
				int QtyOnHand = rs.getInt("QuantityOnHand");
				int minQty = rs.getInt("Min_Qty");
				
				//System.out.println(iCode+" "+iName+" "+iCost+" "+QtyOnHand);
			
				//System.out.println(iName+" "+iCost);
				if(objVendorBean!=null) {
					  VendorBean = (ItemBean) objVendorBean ;
					  //ItemBean.getCartItem(0);
					  
					  session.setAttribute("Vendor_List", VendorBean);
				  } else {
					  VendorBean = new ItemBean();
					  
					  session.setAttribute("Vendor_List", VendorBean);
				   
				  }
				//Create a method in ItemBean that accepts iName, iCost, QuantityOnHand, Min_Qty, Department
				VendorBean.VendorItem(iCode,iName, iCost, QtyOnHand, minQty);
				 
			}
			for(ItemsDescription b:VendorBean.getVendorItemList()){
				
				
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

}
