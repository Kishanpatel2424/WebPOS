package Items;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
    	String OldSessionId =null;
    	
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("JSESSIONID")){
    			OldSessionId = cookie.getValue();
    			System.out.println("LogOut JSESSIONID="+OldSessionId);
    			
    			
    		}
    		Cookie id = new Cookie("OldSessionId", OldSessionId);
			
			response.addCookie(id);
    		cookie.setMaxAge(0);
    		response.addCookie(cookie);
    	}
    	}
    	//invalidate the session if exists
    	HttpSession session = request.getSession();
    	
    	System.out.println("LogOut Page User= "+session.getAttribute("user")+" "+OldSessionId);
    	request.setAttribute("OldSessionId", OldSessionId);
    	if(session != null){
    		
    		session.invalidate();
    	}
    	session = request.getSession(true);
    	
    	System.out.println(session.getId());
    	//no encoding because we have invalidated the session
    	response.sendRedirect("/InsertDataWebApplication/index.jsp");
    }

}