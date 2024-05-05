
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

@WebServlet("/CurrentPostsServlet")
public class CurrentPostsServlet extends HttpServlet {

    
private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	
	    Gson gson = new Gson();
	    PrintWriter pw = response.getWriter();
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");	    
	    
	        String userInformation = JDBCConnector.getCurrentPosts();
	        if (userInformation != "") {
	            // Redirlocaect to the dashboard upon successful login
	        	response.getWriter().write(gson.toJson(userInformation));
	        } else {
	            // Respond with an error message
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            String error = "Wrong Username/password";
		            pw.write(gson.toJson(error));
		            pw.flush();
		        }
		    
	}
}