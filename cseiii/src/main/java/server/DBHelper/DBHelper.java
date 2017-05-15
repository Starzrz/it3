package server.DBHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	 public static final String url = "jdbc:mysql://localhost/quantour?user=root&password=1234&characterEncoding=utf8&useSSL=true";  
	    public static final String driver = "com.mysql.jdbc.Driver";  
	    public static final String user = "root";  
	    public static final String password = "1234";  
	  
	    public Connection conn = null;  
	    public PreparedStatement pst = null;  
	  
	    public DBHelper(String operateString) {
	        try {  
	            Class.forName(driver);
	            conn = DriverManager.getConnection(url, user, password);
	            pst = conn.prepareStatement(operateString);
	        } catch (Exception e) {  
	            e.printStackTrace();
	        }  
	    }  
	  
	    public void close() {  
	        try {  
	            this.conn.close();  
	            this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
