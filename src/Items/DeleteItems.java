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
			PrintWriter out = response.getWriter();
			Connection MyConn = null;
			response.setContentType("text/html");
			String qryStr = "", qryStr1 = "";
		    Statement stmt = null;
		    ResultSet rs = null;
		    int DeleteActor;
			
				try {
					
					 MyConn = ConnectionManager.getConnection();
					 stmt = MyConn.createStatement();
					 
						if(request.getParameter("iCode") != null){
							 String iCode = request.getParameter("iCode");
							 qryStr = "SELECT * from Item WHERE ItemCode ="+iCode;
							 
							 qryStr1 = "DELETE FROM Item WHERE ItemCode ="+ iCode;
							
						}
				      DeleteActor = stmt.executeUpdate(qryStr1);
			      
				      if(DeleteActor>0){
				    	  
				      	request.setAttribute("Product Deleted","Product Deleted");
						 request.getRequestDispatcher("DeleteItems.jsp").forward(request, response); 
						 //response.sendRedirect("/InsertDataWebApplication/DeleteItems.jsp");
				      }
				//Statement myStmt = MyConn.createStatement();
			     
				
				MyConn.close();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    catch(SQLException e) {
		      out.println("SQLException caught: " + e.getMessage());
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				ConnectionManager.closeConnection(MyConn);
			      // Always close the database connection.
			      try {
			        if (MyConn != null) MyConn.close();
			      }
			      catch (SQLException ignored) { }
			   }
	}

	
		
}
