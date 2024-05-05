import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class commentsSearchServlet
 */
@WebServlet("/commentsSearchServlet")
public class commentsSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public commentsSearchServlet() {
        super();
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
        
        JsonObject jsonRequest = JsonParser.parseReader(request.getReader()).getAsJsonObject();
        String buildingName = jsonRequest.get("buildingName").getAsString();

        System.out.println(buildingName);
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/trojanstudy";
            
            conn = DriverManager.getConnection(url, "root", "root"); //whoever is presenting can change their info here 
        	System.out.println("connected commentSearch");
        	
            String query = "SELECT content, tags FROM Comments WHERE buildingName = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, buildingName);
            rs = pstmt.executeQuery();

            JsonArray commentsJson = new JsonArray();
            while (rs.next()) {
                JsonObject commentJson = new JsonObject();
                commentJson.addProperty("content", rs.getString("content"));
                commentJson.addProperty("tags", rs.getString("tags"));
                commentsJson.add(commentJson);
            }

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", true);
            jsonResponse.add("comments", commentsJson);

            out.print(new Gson().toJson(jsonResponse));
            
        } catch (SQLException sqle) {
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Database error: " + sqle.getMessage());
            out.print(new Gson().toJson(jsonResponse));
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        out.flush();
		
		
	}

}
