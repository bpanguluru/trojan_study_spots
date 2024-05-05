

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.xdevapi.Statement;

import studyspots.Tags;

/**
 * Servlet implementation class CommentsServlet
 */
@WebServlet("/CommentsServlet")
public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CommentsServlet() {
    	
    	System.out.println("In comments servlet");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        Gson gson = new Gson();
        
        Comments comment = gson.fromJson(request.getReader(), Comments.class);
        
        String buildingName = comment.getBuildingName();
        String commentData = comment.getContent();
        String tags = comment.getTags();
        
        
        System.out.println(buildingName);
        System.out.println(commentData);
        System.out.println(tags);
        
        JsonObject jsonResponse = new JsonObject();
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/trojanstudy";
            
            conn = DriverManager.getConnection(url, "root", "root"); //whoever is presenting can change their info here 
        	System.out.println("connected ");
        	
        	String sql = "INSERT INTO Comments (content, tags, buildingName) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, commentData);
                stmt.setString(2, tags);
                stmt.setString(3, buildingName);
                int result = stmt.executeUpdate();

                if (result > 0) {
                    jsonResponse.addProperty("success", true);
                    jsonResponse.addProperty("message", "Comment added successfully");
                } else {
                    jsonResponse.addProperty("success", false);
                    jsonResponse.addProperty("message", "Failed to add comment");
                }
            }
        } catch (SQLException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Database error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        out.print(gson.toJson(jsonResponse));
        out.flush();
		
		
	}

}
