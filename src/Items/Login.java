package Items;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	
		
		Connection MyConn =null;
		Statement myStmt =null;
		ResultSet results = null;
		PreparedStatement ps =null;
		
		boolean submit 	= request.getParameter("submit") != null;
		
		if(submit){
			if((request.getParameter("Username")!=null || request.getParameter("Username")!="") &&
					(request.getParameter("Password")!=null || request.getParameter("Password")!="")){
				
				String Username = request.getParameter("Username");
				String Password = request.getParameter("Password");
				
				try {
					 MyConn = ConnectionManager.getConnection();
					 myStmt = MyConn.createStatement();
					 
					 String sqlStatement = "select * from User where user_Name =? and user_Password =?";
					 ps = MyConn.prepareStatement(sqlStatement);
					 ps.setString(1,Username);
					 ps.setString(2, Password);
					 
					 results = ps.executeQuery();
					 
					 if(results.next()){
						 String user =results.getString("user_Type");
						 System.out.println(user);
						 request.getSession().setAttribute("user", user);
						 request.getRequestDispatcher("/home.jsp").forward(request, response);
						 
					 }
					 else
					 { 
						 request.getRequestDispatcher("/index.jsp").forward(request, response);
					 }
					 
					 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
