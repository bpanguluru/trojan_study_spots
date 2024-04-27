package studyspots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
//sql imports
import java.sql.*;

/*
 * INPUT: 
 * OUTPUT: postID of image in uploaded_imgs with the closest SIFT similarity  
 */
/*
@WebServlet("/getImgSimilarity")
public class ImgSimilarityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<Post> postList = new ArrayList<>();
    	
    	response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        
        Gson gson = new Gson();
        Search search = gson.fromJson(request.getReader(), Search.class);
        String query = search.buildingPrompt;
        
        //get entire arraylist of posts that match the query
        SearchResult out= new SearchResult();
        out.postsList = getPostList(query);
        
        //output that as a class that contains the arraylist
        Gson gson2 = new Gson(); 
		String json = gson.toJson(out);	   
	    pw.write(json);
	    pw.flush(); 
    }
    
    private ArrayList<Post> getPostList(String searchQuery)
    {
    	Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/joe?user=root&password=root");
            String query = "SELECT * FROM posts WHERE buildingID = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, searchQuery);
            rs = pst.executeQuery();
            
            ArrayList<Post> output = new ArrayList<>();
            while(rs.next())
            {
            	String buildingName = rs.getString("buildingName");
            	String description = rs.getString("description");
            	String imgPath = rs.getString("image");
            	int trojanRatingSum = rs.getInt("trojansRatingSum");
            	int numberTrojanRatings = rs.getInt("numberTrojanRatings");
            	String tags = rs.getString("tags");
            	Post newpost = new Post(buildingName, searchQuery, description, trojanRatingSum, numberTrojanRatings, imgPath, tags);
            	output.add(newpost);
        	}
            
        	return output;
            

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println("SQLException on closing: " + sqle.getMessage());
            }
        }
        return null;
    }
    
    static class Search
    {
    	String buildingPrompt;
    }
}*/
