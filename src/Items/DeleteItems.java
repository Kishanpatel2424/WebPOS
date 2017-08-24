package Items;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
 * Servlet implementation class DeleteItems
 */
@WebServlet("/DeleteItems")
public class DeleteItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteItems() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		 /*final String JDBC_DRIVER="com.mysql.jdbc.Driver";
			final String DB_URL="jdbc:mysql://localhost:8889/test";
			
			final String USER="root";
			final String PASS ="root";
			*/
			
			
			
	}
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
		 Connection MyConn = null;
			PreparedStatement ps=null;
			
			String qryStr = "", qryStr1 = "";
		    
		    ResultSet rs = null;
		    int DeleteActor;
			
				try {
					
					 MyConn = ConnectionManager.getConnection();
					 
					 
						if(request.getParameter("iCode") != null){
							 String iCode = request.getParameter("iCode");
							 
							 qryStr = "DELETE FROM Item WHERE ItemCode =?";
							 ps = MyConn.prepareStatement(qryStr);
							 ps.setString(1, iCode);
						     int result = ps.executeUpdate();
						     System.out.println(result+" results");

						    	  if(result ==1){
						    		 System.out.println("Barcode Fount "+iCode);
						    		 request.setAttribute("Product Deleted","Product Deleted");
						    		 request.getRequestDispatcher("/DeleteItems.jsp").forward(request, response);
						    	 }
						    	  else if(result == 0 || result != 1){
						    		  System.out.println("Barcode not Found "+iCode);
							    		 request.setAttribute("Product Not Found","Product Not Found");
							    		 request.getRequestDispatcher("/DeleteItems.jsp").forward(request, response); 
						    	  }
						 
						}			
				//Statement myStmt = MyConn.createStatement();
			     
				
				MyConn.close();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

	
		
}
