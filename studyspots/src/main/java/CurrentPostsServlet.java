import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CurrentPostsServlet")
public class CurrentPostsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set content type of the response
        response.setContentType("application/json");

        // Fetch data from the database (assuming you have a method in your JDBCConnector class)
        String jsonData = JDBCConnector.getCurrentPosts();

        // Write JSON data to the response
        PrintWriter out = response.getWriter();
        out.println(jsonData);
    }
}