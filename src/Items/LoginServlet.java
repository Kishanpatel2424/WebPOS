package Items;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection MyConn =null;
		Statement myStmt =null;
		ResultSet results = null;
		PreparedStatement ps =null;
		HttpSession session = request.getSession(false);
		// get request parameters for userID and password
		String user = request.getParameter("Username");
		String pwd = request.getParameter("Password");
		String clear =null;

	
		try {
			 MyConn = ConnectionManager.getConnection();
			 //myStmt = MyConn.createStatement();
			 
			 String sqlStatement = "select * from User where user_Name =? and user_Password =?";
			 ps = MyConn.prepareStatement(sqlStatement);
			 ps.setString(1,user);
			 ps.setString(2, pwd);
			 
			 results = ps.executeQuery();
			 
			 if(results.next()){
				 String User_Type = results.getString("User_Type");
			
			session.setAttribute("user", User_Type);
			
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("user", user);
			
			response.addCookie(userName);
			
			//Get the encoded URL string
			System.out.println(User_Type);
			System.out.println("Success");
			String encodedURL = response.encodeRedirectURL("/InsertDataWebApplication/LoginSuccess.jsp");
			response.sendRedirect(encodedURL);
			
		}else{
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			System.out.println("Failed");
		}

	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}//doPost
}