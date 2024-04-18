import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@SuppressWarnings("serial")
public class RegisterUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // for database connection
        String jdbcUrl = "jdbc:mysql://yourdatabaseserver/dbname";
        String dbUser = "yourdbusername";
        String dbPassword = "yourdbpassword";

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // Check if username already exists
            String checkUserSql = "SELECT username FROM Users WHERE username = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkUserSql);
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                // Username exists
                response.sendRedirect("register.html?error=usernameExists");
            } else {
                // Username does not exist, insert new user
                String insertSql = "INSERT INTO Users (username, password) VALUES (?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.executeUpdate();

                // Registration successful
                response.sendRedirect("login.html?success=registrationSuccessful");
            }

            // Clean up
            resultSet.close();
            checkStmt.close();
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            response.sendRedirect("register.html?error=systemError");
        }
    }
}
