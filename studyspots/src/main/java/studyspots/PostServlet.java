package studyspots;

import java.io.IOException;
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
import java.io.PrintWriter;

@WebServlet("/PostServlet")
public class PostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost/TrojanStudy";
    private static final String USER = "root";
    private static final String PASS = "root";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        ArrayList<Post> posts = getAllPosts();

        Gson gson = new Gson();
        String json = gson.toJson(posts);
        out.write(json);
        out.flush();
    }

    private ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT * FROM Posts";
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

                Post post = new Post(buildingName, buildingID, description, numberTrojanRatings, trojanRatingSum, imgPath, tags);
                posts.add(post);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return posts;
    }
}
