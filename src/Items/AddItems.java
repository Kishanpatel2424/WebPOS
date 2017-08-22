package Items;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Servlet implementation class AddItems
 */
@WebServlet("/AddItems")
public class AddItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectionManager cm = new ConnectionManager();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItems() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// output an HTML page
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.getWriter().append("Served at: AddItems.Java").append(request.getContextPath());
		System.out.println("On AddItem Page");
		Connection MyConn=null;
		PreparedStatement insertActor= null;
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String title="Database connect";
		pw.println(getServletInfo());
		
		boolean submitButtonPressed = request.getParameter("Addsubmit") != null;
		boolean AddItem = true;
		//boolean deleteSubmitButton 	= request.getParameter("dSubmit") != null;
		String iCode=null;
		String iName="";
		double iDeposit=0.0;
		double iCost=0.0;
		String Price;
		double iPrice=0.0;
		
		String Department="";
		int OtyOnHand=1;

		
		int result;
		if(submitButtonPressed){
			
			if(request.getParameter("iCode") == null ||request.getParameter("iCode")=="" ) {
				request.setAttribute("Enter Product BarCode","BarCode");
				request.getRequestDispatcher("/AddItem.jsp").forward(request, response);
			}
			
			if(request.getParameter("iCode") != null){
				 iCode =request.getParameter("iCode").trim();
				 iName = request.getParameter("iName");
				 Department = request.getParameter("Department");
				 Price = (request.getParameter("iPrice"));
				 	if(Price =="" || Price==null)
				 		iPrice=0.0;
				 	else	
				 		iPrice = Double.parseDouble(Price);
				// OtyOnHand = Integer.parseInt(request.getParameter("OtyOnHand"));
				 if(request.getParameter("OtyOnHand") =="" || request.getParameter("OtyOnHand")==null)
					 	OtyOnHand = 0;
				 	else	
				 		OtyOnHand = Integer.parseInt(request.getParameter("OtyOnHand"));
				 Price =(request.getParameter("iCost"));
				 
				 if(Price =="" || Price==null)
					 	iCost=0.0;
				 	else	
				 		iCost = Double.parseDouble(Price);
			}
			
			
			String Tax = request.getParameter("Tax");
			
		
			try{
				
				//add class/method which returns CONNECTION object
				
				
				
				MyConn = ConnectionManager.getConnection();
				Statement myStmt = MyConn.createStatement();
				String Check = ("SELECT COUNT(ItemCode) FROM Item WHERE ItemCode ="+iCode);
		         
		         ResultSet rs = myStmt.executeQuery(Check);
		         
		        while(rs.next()){
		        	if(rs.getInt(1)>0){
		        		System.out.println("Item Already Exist with BarCode "+iCode);
					request.setAttribute("Exist","Exist");
		        	 request.getRequestDispatcher("/AddItem.jsp").forward(request, response);
		        	 AddItem = false;
		        	}
		        	
				}
				if(AddItem == true){
					System.out.println("Item Inserted");
		         insertActor = MyConn.prepareStatement("INSERT INTO Item(ItemCode, ItemName,ItemCost,ItemPrice,Department,QuantityOnHand) VALUES(?,?,?,?,?,?)");
		         insertActor.setString(1,iCode);
		         insertActor.setString(2, iName);
		         insertActor.setDouble(3, iCost);
		         insertActor.setDouble(4, iPrice);
		         insertActor.setString(5, Department);
		         insertActor.setInt(6, OtyOnHand);
		         
		         result =insertActor.executeUpdate();
		         //int rs = myStmt.executeUpdate(sql);
		         pw.print("Completed");
		          
		          
		          if(result>0){
		        	 request.setAttribute("Successfull","Successfull");
		        	 request.getRequestDispatcher("/AddItem.jsp").forward(request, response); 
		        	 //response.sendRedirect("/InsertDataWebApplication/AddItem.jsp");
		          }
		          else{
		        	  
		        	  System.out.println("Item Not Inserted");
		        	  request.setAttribute("ErrorCode","Item Not found");
			        	 request.getRequestDispatcher("/AddItem.jsp").forward(request, response);
		          }
		          insertActor.close();
				}
		          
		       // Clean-up environment
		          
		         
		          MyConn.close();
			}
			catch (Exception exc){
				exc.printStackTrace();  
			}
			
		}
	}

}
