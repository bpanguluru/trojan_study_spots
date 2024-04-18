import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //for database
        String jdbcUrl = "jdbc:mysql://yourdatabaseserver/dbname";
        String dbUser = "yourdbusername";
        String dbPassword = "yourdbpassword";

        try {
            //connect
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            //use preparedstatemnt to prevent injection
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            //query using preparedstamtent
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                //login success
                //i dunno how to use this it was a gpt suggest
                request.getSession().setAttribute("user", username);
                //send to whatever the html is actually called
                response.sendRedirect("welcome.html"); 
            } else {
                //login_fail
                response.sendRedirect("login.html?error=invalidCredentials"); // Redirect back to login with error
            }

            // Clean up
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions
            response.sendRedirect("login.html?error=systemError"); // Redirect with system error
        }
    }
}