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

import com.google.gson.JsonObject;

@WebServlet("/NewPostServlet")
public class NewPostServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String buildingID = request.getParameter("buildingID");
        String buildingName = request.getParameter("buildingName");
        String locationTitle = request.getParameter("locationTitle");
        String description = request.getParameter("description");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String image = request.getParameter("image");
        String tags = request.getParameter("tags");

        JsonObject jsonResponse = new JsonObject();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/trojanstudy?user=root&password=root");
             PreparedStatement pst = conn.prepareStatement("INSERT INTO posts (buildingName, buildingID, locationTitle, description, image, trojanRatingSum, numberTrojanRatings, tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
             PreparedStatement pstRating = conn.prepareStatement("INSERT INTO ratings (ratingValue, userID, postID) VALUES (?, ?, ?)")) {

            pst.setString(1, buildingName);
            pst.setString(2, buildingID);
            pst.setString(3, locationTitle);
            pst.setString(4, description);
            pst.setString(5, image);
            pst.setInt(6, rating);
            pst.setInt(7, 1); // Initial value for trojanRatingSum
            pst.setInt(8, 1); // Initial value for numberTrojanRatings

            int affectedRows = pst.executeUpdate();
            if (affectedRows == 1) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int postID = rs.getInt(1);
                    pstRating.setInt(1, rating);
                    //pstRating.setString(2, userID);
                    pstRating.setInt(3, postID);
                    pstRating.executeUpdate();
                    jsonResponse.addProperty("status", "SUCCESS");
                    jsonResponse.addProperty("postID", postID);
                } else {
                    jsonResponse.addProperty("status", "FAILURE");
                    jsonResponse.addProperty("message", "Failed to retrieve post ID");
                }
            } else {
                jsonResponse.addProperty("status", "FAILURE");
                jsonResponse.addProperty("message", "Failed to insert post");
            }
        } catch (SQLException e) {
            jsonResponse.addProperty("status", "FAILURE");
            jsonResponse.addProperty("message", e.getMessage());
        }

        out.print(jsonResponse.toString());
    }
}