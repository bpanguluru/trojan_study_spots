import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@SuppressWarnings("serial")
@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
    private static final String JDBC_DRIVER = "jdbc:mysql://localhost:3306/trojanstudy";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        
        ResultSet rs1 = null;
        ResultSet rs2 = null;

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            conn = DriverManager.getConnection(JDBC_DRIVER, DB_USER, DB_PASSWORD);

            // Check if email is already registered
            String emailCheckQuery = "SELECT * FROM trojanstudy.users WHERE email = ?";
            pstmt1 = conn.prepareStatement(emailCheckQuery);
            pstmt1.setString(1, email);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                response.getWriter().write("Email is already registered");
                return; // Stop further execution
            }

            // Check if username is already registered
            String usernameCheckQuery = "SELECT * FROM trojanstudy.users WHERE username = ?";
            pstmt2 = conn.prepareStatement(usernameCheckQuery);
            pstmt2.setString(1, username);
            rs2 = pstmt2.executeQuery();

            if (rs2.next()) {
                response.getWriter().write("Username is already taken");
                return; // Stop further execution
            }

            // Insert new user into the database
            String insertQuery = "INSERT INTO trojanstudy.users (email, username, password) VALUES (?, ?, ?)";
            pstmt3 = conn.prepareStatement(insertQuery);
            pstmt3.setString(1, email);
            pstmt3.setString(2, username);
            pstmt3.setString(3, password);
            pstmt3.executeUpdate();

            response.getWriter().write("success");
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().write("An error occurred. Please try again later.");
        } finally {
            try {
            	
                if (rs1 != null) {
                	rs1.close();
                }
                
                if (rs2 != null) {
                	rs2.close();
                }
                
                if (pstmt1 != null) {
                	pstmt1.close();
                }
                
                if (pstmt2 != null) {
                	pstmt2.close();
                }
                
                if (pstmt3 != null) {
                	pstmt3.close();
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
