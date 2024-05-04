import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


//import com.mysql.cj.xdevapi.Statement;

public class JDBCConnector {
	public static String getCurrentPosts() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    Connection conn = null;
	    Statement st = null;
	    ResultSet rs = null;

	    JsonArray jsonArray = new JsonArray(); // Create a JSON array to hold the data

	    try {
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/trojanstudy?user=root&password=root");
	        st = conn.createStatement();
	        rs = st.executeQuery("SELECT * FROM posts");

	        while (rs.next()) {
	            // Create a JSON object for each row and populate it with data
	            JsonObject jsonObject = new JsonObject();
	            jsonObject.addProperty("postID", rs.getInt("postID"));
	            jsonObject.addProperty("name", rs.getString("buildingName"));
	            jsonObject.addProperty("buildingID", rs.getString("buildingID"));
	            jsonObject.addProperty("description", rs.getString("description"));
	            jsonObject.addProperty("description", rs.getString("description"));
	            jsonObject.addProperty("trojansRatingSum", rs.getString("trojansRatingSum"));
	            jsonObject.addProperty("numberTrojanRatings", rs.getString("numberTrojanRatings"));

	            // Add the JSON object to the JSON array
	            jsonArray.add(jsonObject);
	        }
	    } catch (SQLException sqle) {
	        sqle.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
	            if (conn != null) conn.close();
	        } catch (SQLException sqle) {
	            sqle.printStackTrace();
	        }
	    }

	    // Convert the JSON array to a string and return it
	    return jsonArray.toString();
	}
	

}
