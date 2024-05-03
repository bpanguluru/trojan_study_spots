import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/VerifyLogin")
public class VerifyLogin extends HttpServlet {
    private static final String JDBC_DRIVER = "jdbc:mysql://localhost:3306/trojanstudy";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            conn = DriverManager.getConnection(JDBC_DRIVER, DB_USER, DB_PASSWORD);

            // Prepare SQL statement to check if the username exists and password matches
            String sql = "SELECT * FROM trojanstudy.users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Username and password match
                response.getWriter().write(username);
            } else {
                // Username or password is incorrect
                response.getWriter().write("failure");
            }
            
        } catch (SQLException | ClassNotFoundException e) {
        	
            e.printStackTrace();
            response.getWriter().write("error");
            
        } finally {
            
            try {
            	
                if (rs != null) {
                	rs.close();
                }
                
                if (pstmt != null) {
                	pstmt.close();
                }
                
                if (conn != null) {
                	conn.close();
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
