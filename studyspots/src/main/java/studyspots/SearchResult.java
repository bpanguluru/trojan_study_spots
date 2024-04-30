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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class SearchResult {
    
	public ArrayList<Post> postsList = new ArrayList<>();
	
	private static final long serialVersionUID = 1L; 
    private static final String DB_URL = "jdbc:mysql://localhost/TrojanStudy";
    private static final String USER = "root";
    private static final String PASS = "suna1123";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String searchBuildingID = request.getParameter("buildingID");

        ArrayList<Post> posts = getPostsByBuildingID(searchBuildingID);

        Gson gson = new Gson();
        String json = gson.toJson(posts);
        out.write(json);
        out.flush();
    }

    private ArrayList<Post> getPostsByBuildingID(String searchBuildingID) {
        ArrayList<Post> posts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT * FROM Posts WHERE buildingID = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, searchBuildingID);
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

                Post post = new Post(buildingName, buildingID, description, numberTrojanRatings, trojanRatingSum,
                        imgPath, tags);
                posts.add(post);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pst != null)
                    pst.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return posts;
    }
}
