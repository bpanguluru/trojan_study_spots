
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.WebServlet;

@WebServlet("/SubmitRatingServlet")
public class SubmitRatingServlet extends HttpServlet {

    
private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Extract data from the request
	    String buildingID = request.getParameter("buildingID");
	    int checkboxCount = Integer.parseInt(request.getParameter("checkboxCount"));
	
	    // Update the SQL database
	    JDBCConnector.updateTrojanSum(buildingID, checkboxCount);
	
	    // Send response back to the client
	    response.setContentType("text/plain");
	    PrintWriter out = response.getWriter();
	    out.println("Database updated successfully");
	}
}
