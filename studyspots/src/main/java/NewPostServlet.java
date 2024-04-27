<<<<<<< HEAD
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/NewPostServlet")
//public class NewPostServlet extends HTTPServlet{
//	
//	public static int createPost(String buildingID, String areaTitle, String description, ArrayList<String> tags, int rating) {
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			System.out.println("MySQL Class not found");
//			e.printStackTrace();
//		}
//		
//		//input validation on all of the things
//		
//		Connection conn = null;
//		Statement st = null;
//		ResultSet rs = null;
//		
//		int userID=-1;
//		
//		try {
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/Assignment4?user=root&password=Eag3rBe@ver");
//			st = conn.createStatement();
//			rs = st.executeQuery("SELECT * FROM Users WHERE username=='"+username+"'");
//			if(!rs.next()) {
//				st = conn.createStatement();
//				rs = st.executeQuery("SELECT * FROM Users WHERE email=='"+email+"'");
//				if(!rs.next()) {
//					rs.close();
//					st.execute("INSERT INTO users(username, pass, email, balance) VALUES('"
//					+username+"', '"+password+"', '"+email+"', 50000)");
//					rs = st.executeQuery("SELECT LAST_INSERT_ID()");
//					rs.next();
//					userID = rs.getInt(1);
//				} else {
//					userID= -2;
//				}
//			}
//		} catch (SQLException sqle) {
//			System.out.println("SQLE Exception in Register User. \n"+sqle.getMessage());
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException sqle) {
//				System.out.println("sqle: "+sqle.getMessage());
//			}
//		}
//		return userID;
//	}
//	
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        
//    }
//}
=======
/*import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NewPostServlet")
public class NewPostServlet extends HTTPServlet{
	
	public static int createPost(String buildingID, String areaTitle, String description, ArrayList<String> tags, int rating) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL Class not found");
			e.printStackTrace();
		}
		
		//input validation on all of the things
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		int userID=-1;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Assignment4?user=root&password=Eag3rBe@ver");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Users WHERE username=='"+username+"'");
			if(!rs.next()) {
				st = conn.createStatement();
				rs = st.executeQuery("SELECT * FROM Users WHERE email=='"+email+"'");
				if(!rs.next()) {
					rs.close();
					st.execute("INSERT INTO users(username, pass, email, balance) VALUES('"
					+username+"', '"+password+"', '"+email+"', 50000)");
					rs = st.executeQuery("SELECT LAST_INSERT_ID()");
					rs.next();
					userID = rs.getInt(1);
				} else {
					userID= -2;
				}
			}
		} catch (SQLException sqle) {
			System.out.println("SQLE Exception in Register User. \n"+sqle.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: "+sqle.getMessage());
			}
		}
		return userID;
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
    }
}*/
>>>>>>> 68ac38670bc83190f8b99792e7ea9a465714ea82
