import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.text.similarity.LongestCommonSubsequence;


@WebServlet("/NewPostServlet")
public class NewPostServlet extends HTTPServlet{
	
	//returns -1 if there is an existing post that is over-similar to what you're trying to add,
	public static int createPost(String userID, String buildingID, String buildingName, String locationTitle, String description, int rating, String image, String tags) {
		private static final String JDBC_DRIVER = "jdbc:mysql://localhost:3306/trojanstudy";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL Class not found");
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		int postID=-1;
		
		//get all the posts that have this building tag to see if there are oversimilar existing posts
		try {
			conn = DriverManager.getConnection(JDBC_DRIVER, DB_USER, DB_PASSWORD);
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM Posts WHERE buildingID='"+buildingID+"'");
			if(rs.next()) {
				ArrayList<String> locationTitles = new ArrayList<>();
				locationTitles.add(rs.getString("locationTitle"));
				while(rs.next()) {
					locationTitles.add(rs.getString("locationTitle"));
				}
				for(String l: locationTitles) {
					if (LongestCommonSubsequence.apply(l, locationTitle)>10) {
						return -2;
					}
				}
			}
			rs = st.executeQuery("SELECT userID From Users WHERE username='"+userID+"'");
			rs.next();
			int userIDInt = rs.getInt(1);
			st.execute("INSERT INTO Posts(buildingName, buildingID, locationTitle, description, image, trojanRatingSum, numberTrojanRatings, tags) VALUES('"
				+buildingName+"', '"+buildingID+"', '"+locationTitle+"', '"+description+"', '"+image+"', "+rating+", 1, '"+tags+"')");
			rs = st.executeQuery("SELECT LAST_INSERT_ID()");
			rs.next();
			postID = rs.getInt(1);
			st.execute("INSERT INTO Ratings(ratingValue, userID, postID) VALUES("
					+rating+", '"+userIDInt+", "+postID+")");
		} catch (SQLException sqle) {
			System.out.println("SQLE Exception in Register User. \n"+sqle.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: "+sqle.getMessage());
			}
		}
		return postID;
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        int responseID = createPost(request.getParameter("userID"), 
        		request.getParameter("buildingID"), 
        		request.getParameter("buildingName"), 
        		request.getParameter("locationTitle"), 
        		request.getParameter("description"), 
        		Integer.parseInt(request.getParameter("rating")), 
        		request.getParameter("image"), 
        		request.getParameter("tags")
        	);
        if(responseID==-2){
        	out.write("OVERSIMILAR");
        } else if(responseID==-1){
        	out.write("FAILURE");
        } else {
        	out.write("SUCCESS");
        }
    }
}
