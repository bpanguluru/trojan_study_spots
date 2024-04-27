package studyspots;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 response.setContentType("application/json");
    	    PrintWriter out = response.getWriter();
    	    out.print("{\"message\":\"Hello from servlet\"}");
    	    out.flush();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();

        ArrayList<Post> postsList = getPostList();

        Gson gson = new Gson(); 
        String json = gson.toJson(postsList);
        out.write(json);
        out.flush(); 
    }
    
    private ArrayList<Post> getPostList()
    {
        ArrayList<Post> posts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/joe?user=root&password=root");
            String query = "SELECT * FROM posts";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {
                int postID = rs.getInt("postID");
                String buildingName = rs.getString("buildingName");
                String buildingID = rs.getString("buildingID");
                String description = rs.getString("description");
                String imgPath = rs.getString("image");
                int trojanRatingSum = rs.getInt("trojansRatingSum");
                int numberTrojanRatings = rs.getInt("numberTrojanRatings");
                String tags = rs.getString("tags");

                // Create a Post object for each row in the result set
                Post post = new Post(postID, buildingName, buildingID, description, trojanRatingSum, numberTrojanRatings, imgPath);
                posts.add(post);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            try {
                if (rs != null) { rs.close(); }
                if (pst != null) { pst.close(); }
                if (conn != null) { conn.close(); }
            } catch (SQLException sqle) {
                System.out.println("SQLException on closing: " + sqle.getMessage());
            }
        }
        return posts;
    }
}
