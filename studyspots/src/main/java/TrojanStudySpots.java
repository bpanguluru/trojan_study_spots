import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class SchoolFormServlet
 */
@WebServlet("/TrojanStudySpots")
public class TrojanStudySpots extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set content type of the response to JSON
        response.setContentType("application/json");
        
		String rating = request.getParameter("rating");
		
        // Construct JSON object
        String json = "{"
                    + "\"rating\": \"" + rating
                    + "}";

        // Write JSON object to the response
        response.getWriter().write(json);
	}

}
