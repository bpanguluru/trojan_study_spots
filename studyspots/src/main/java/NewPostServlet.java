import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

@WebServlet("/NewPostServlet")
public class NewPostServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/trojanstudy?user=root&password=root")) {
            String buildingID = request.getParameter("buildingID");
            String buildingName = request.getParameter("buildingName");
            String description = request.getParameter("description");
            int trojansRatingSum = Integer.parseInt(request.getParameter("trojansRatingSum"));
            int numberTrojanRatings = Integer.parseInt(request.getParameter("numberTrojanRatings"));
            String locationTitle = request.getParameter("locationTitle");
            String image = request.getParameter("image");
            String tags = request.getParameter("tags");

            try (PreparedStatement pst = conn.prepareStatement("INSERT INTO posts (buildingName, buildingID, description, image, trojansRatingSum, numberTrojanRatings, tags, locationTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement pstRating = conn.prepareStatement("INSERT INTO ratings (ratingValue, userID, postID) VALUES (?, ?, ?)")) {

                pst.setString(1, buildingName);
                pst.setString(2, buildingID);
                pst.setString(3, description);
                pst.setString(4, image);
                pst.setInt(5, trojansRatingSum);
                pst.setInt(6, numberTrojanRatings);
                pst.setString(7, tags);
                pst.setString(8, locationTitle);

                int affectedRows = pst.executeUpdate();
                if (affectedRows == 1) {
                    ResultSet rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        int postID = rs.getInt(1);
                        pstRating.setInt(1, trojansRatingSum);
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
        } catch (SQLException e) {
            jsonResponse.addProperty("status", "FAILURE");
            jsonResponse.addProperty("message", e.getMessage());
        }

        out.print(jsonResponse.toString());
    }
}