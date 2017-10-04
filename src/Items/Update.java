package Items;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection MyConn=null;
		PreparedStatement UpdateActor, ps = null;
		ResultSet rs = null;
		Statement myStmt=null;
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		
		boolean submitButtonPressed = request.getParameter("search") != null;
		boolean updateButtonPressed = request.getParameter("Update") != null;
		boolean Update=false;
		
		String iCode="", OldiCode = null;
		String iName="";
		double iCost=0.0;
		double iPrice=0.0;
		int QuantityOnHand=0; 
		String Department ="";
		int Min_Qty =0;
		String Vendor_Name=null;
		
		
			if(submitButtonPressed){
				if(request.getParameter("iCode") != null && request.getParameter("iCode") != ""){
					 iCode =request.getParameter("iCode").trim();
					 System.out.println("iCode "+iCode);
					 OldiCode=iCode;
					 Update=true;
				}
				else if(request.getParameter("iCode") == "" ||request.getParameter("iCode") == null ){
					request.setAttribute("BarCodeEmpty","BarCodeEmpty");
					request.getRequestDispatcher("Update.jsp").forward(request, response);
				}
				
			}
			
		if(Update == true){
			try{
				
				//add class/method which returns CONNECTION object

				MyConn = ConnectionManager.getConnection();
				 myStmt = MyConn.createStatement();
				String sqlStatement = ("SELECT COUNT(ItemCode) FROM Item WHERE ItemCode =?");
				 ps = MyConn.prepareStatement(sqlStatement);
			      ps.setString(1, iCode);
			       
			       rs = ps.executeQuery();
				while(rs.next()){
			        	if(rs.getInt(1)<=0){
			        		System.out.println("Item Dose not Exist with BarCode "+iCode);
			        		request.setAttribute("NotExist","NotExist");
			        		request.getRequestDispatcher("Update.jsp").forward(request, response);
			        	// AddItem = false;
			        	}
			        sqlStatement = ("SELECT * FROM Item WHERE ItemCode =?");
			       ps = MyConn.prepareStatement(sqlStatement);
			       ps.setString(1, iCode);
			       
			       rs = ps.executeQuery();
			        if(rs.next()){
			        		System.out.println("Item Found "+iCode);
			        		
			        		iCode = rs.getString("ItemCode");
			        		iName = rs.getString("ItemName");
			        		iCost = rs.getDouble("ItemCost");
			        		iPrice = rs.getDouble("ItemPrice");
			        		Department = rs.getString("Department");
			        		
			        		QuantityOnHand = rs.getInt("QuantityOnHand");
			        		Min_Qty = rs.getInt("Min_Qty");
			        		Vendor_Name = rs.getString("Vendor_Name");
			        		
			        		System.out.println(iCode+" "+iName+" "+iCost+" "+iPrice);
			        		request.setAttribute("iCode",iCode);
			        		request.setAttribute("iName",iName);
			        		request.setAttribute("iCost",iCost);
			        		request.setAttribute("iPrice",iPrice);
			        		request.setAttribute("Department",Department);
			        		request.setAttribute("QuantityOnHand",QuantityOnHand);
			        		request.setAttribute("ItemCode", OldiCode);
			        		request.setAttribute("Min_Qty", Min_Qty);
			        		request.setAttribute("Vendor_Name", Vendor_Name);
			        		request.getRequestDispatcher("Update.jsp").forward(request, response);
			        		
			        	}
			        	
					}
			}
			catch (Exception exc){
				exc.printStackTrace();  
			}
			finally  {
				ConnectionManager.closeConnection(MyConn);
			}
		}
		
		if(updateButtonPressed){
			if(request.getParameter("iCode") != null && request.getParameter("iCode") != ""){
				 iCode =request.getParameter("iCode").trim();
				 System.out.println("iCode "+iCode);
				 Update=true;
			}
			else if(request.getParameter("iCode") == "" ||request.getParameter("iCode") == null ){
				request.setAttribute("BarCodeEmpty","BarCodeEmpty");
				//request.getRequestDispatcher("Update.jsp").forward(request, response);
			}
			
		
		if(Update == true){
			
			int result;
			try{
				MyConn = ConnectionManager.getConnection();
				 myStmt = MyConn.createStatement();
				 
				 UpdateActor = MyConn.prepareStatement("UPDATE Item set ItemCode=?, ItemName=?, ItemCost=?, ItemPrice=?, Department=?, QuantityOnHand=?, deposit=?, Vendor_Name=?, Min_Qty=? where ItemCode=?");
				 
				 if(request.getParameter("iCode") != null){
					 iCode =request.getParameter("iCode").trim();
				}
				
				if(request.getParameter("iName") != null){
					  iName = request.getParameter("iName");
				}
				if(request.getParameter("Department").toString() != "")
					Department = request.getParameter("Department");
				
				
				if(request.getParameter("iPrice") != null){
					String Price = (request.getParameter("iPrice"));
					if(Price == "")
						System.out.println("Price is "+Price);
					else
					  iPrice = Double.parseDouble(Price);
				}
				if(request.getParameter("OtyOnHand")!= null && request.getParameter("OtyOnHand")!=""){
					QuantityOnHand = Integer.parseInt(request.getParameter("OtyOnHand"));
				}
				
				if(request.getParameter("iCost") != null){
					 String Price =(request.getParameter("iCost"));
					
					  iCost = Double.parseDouble(Price);
				}
				System.out.println(request.getParameter("Vendor_Name"));
				if(!request.getParameter("Vendor_Name").equals(null) && !request.getParameter("Vendor_Name").equals("")){
					Vendor_Name = request.getParameter("Vendor_Name");
				}
				if(!request.getParameter("Min_Qty").equals(null)){
					Min_Qty = Integer.parseInt(request.getParameter("Min_Qty"));
				}
				
				UpdateActor.setString(10, iCode);
				UpdateActor.setString(1, iCode);
        		UpdateActor.setString(2, iName);
        		UpdateActor.setDouble(3, iCost);
        		UpdateActor.setDouble(4, iPrice);
        		UpdateActor.setString(5, Department);
        		UpdateActor.setInt(6, QuantityOnHand);
        		UpdateActor.setDouble(7, 0);
        		UpdateActor.setString(8, Vendor_Name);
        		UpdateActor.setInt(9, Min_Qty);
        		System.out.println(iCode+" "+iName+" "+iCost+" "+iPrice+" "+Department+" "+QuantityOnHand);
        		
        		result = UpdateActor.executeUpdate();
        		System.out.println("Result "+result);
        		 if(result>0){
		        	 request.setAttribute("Successfull","Successfull");
		        	 request.getRequestDispatcher("/Update.jsp").forward(request, response); 
		        	 
		          }
		          else{
		        	  System.out.println("Item Not Inserted");
		          }
        		 UpdateActor.close();
				
			}
			catch (Exception exc){
				exc.printStackTrace();  
			}
			finally  {
				ConnectionManager.closeConnection(MyConn);
			}
		}
		         
		}
		
	}

}
