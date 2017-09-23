package Items;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

//import com.mysql.jdbc.Connection;

public class ConnectionManager {
   
	 private static java.sql.Connection MyConn = null;
	static java.sql.Connection getConnection() throws Exception {

		
        
	        String url = "jdbc:mysql://localhost:8889/test";
	        String dbName = "test";
	        String driver = "com.mysql.jdbc.Driver";
	        String userName = "root";
	        String password = "root";
	        
		/*// Jalastic
	        String url = "jdbc:mysql://node23485-onlinepos.njs.jelastic.vps-host.net/test";
	        String dbName = "test";
	        String driver = "com.mysql.jdbc.Driver";
	        String userName = "root";
	        String password = "BPNivr47456";*/
	        
/*
		//kdpatel2424@outlook.com--
		  String url = "jdbc:mysql://us-cdbr-azure-southcentral-f.cloudapp.net:3306/";
		String dbName = "testdata1";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "b6893a64d2d8f6";
		String password = "76959b7d";
		*/
		
		/*//kdptel2424@gmail.com
		String url = "jdbc:mysql://us-cdbr-azure-southcentral-f.cloudapp.net:3306/acsm_46e3f8456a36547";
		String dbName = "test";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "b9f000a9727bab";
		String password = "4fc8eab7";*/
		
	        Connection connection = null;
	        Class.forName(driver).newInstance();
	         MyConn = (Connection) DriverManager.getConnection(url, userName,password);
	         if(MyConn ==null)
	        	 System.out.println("Error");
	        return MyConn;
	    }
	 public static void closeConnection(java.sql.Connection myConn) {

	        try {

	            MyConn.close();

	        } catch (SQLException e) {

	        }

	    }
	 
	 
}