<%@page import="java.sql.*"%>
<html>
<Title>Verify Login</Title>
<body>

<%
String user_Type ="";
String user=request.getParameter("Username");
String pass=request.getParameter("Password");
ResultSet rs=null;
try{

 Class.forName("com.mysql.jdbc.Driver").newInstance();
    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:8889/test","root","root");  
           Statement st=con.createStatement();
           rs=st.executeQuery("select * from User where user_Name='"+user+"' and user_Password='"+pass+"'");
           int count=0;
           while(rs.next()){
           count++;
           
          }
           rs.close();
           con.close();
          if(count>0){
        	 String Reg = "/InsertDataWebApplication/Reg.jsp";
        	  response.sendRedirect(response.encodeRedirectURL(Reg));
        	  
        	  return; 
           }
          else{
          
        	  String index = "/InsertDataWebApplication/index.jsp";
           	  response.sendRedirect(response.encodeRedirectURL(index));
           	  return; 
          }
          
  }
    catch(Exception e){
    System.out.println(e);
}
%>
</body>
</html>