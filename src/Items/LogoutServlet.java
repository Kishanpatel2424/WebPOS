package Items;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
    	Connection MyConn =null;
		
		PreparedStatement ps =null;
		int id = 0;
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("JSESSIONID")){
    			
    			System.out.println("LogOut JSESSIONID");
    			
    			
    		}
    		cookie.setMaxAge(0);
    		response.addCookie(cookie);
    	}
    	}
    	//invalidate the session if exists
    	HttpSession session = request.getSession();
    	
    	System.out.println("LogOut Page User= "+session.getAttribute("user")+" "+session.getAttribute("user_id"));
    	 id = (int) session.getAttribute("user_id");
    	if(session != null){
    		
    		session.invalidate();
    	}
    	session = request.getSession(true);
    	
    	System.out.println(session.getId());
    	
    	//Update User LogOut Index
    	Calendar cal = Calendar.getInstance();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
		
    	try {
			 MyConn = ConnectionManager.getConnection();
			 //myStmt = MyConn.createStatement();
				
			 String sqlStatement = "Update User set LogOut=? where user_Id=?";
			 ps = MyConn.prepareStatement(sqlStatement);
			 
			 ps.setTimestamp(1, timestamp);
			 ps.setInt(2, id);
			 int result = ps.executeUpdate();
			 ps.close();
			 MyConn.close();
    	}
    	catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	//no encoding because we have invalidated the session
    	response.sendRedirect("/InsertDataWebApplication/index.jsp");
    }

}