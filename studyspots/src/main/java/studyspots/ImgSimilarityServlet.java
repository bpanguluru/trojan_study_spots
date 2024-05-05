package studyspots;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.Imgcodecs;
import java.util.ArrayList;
import javax.servlet.annotation.MultipartConfig;


@WebServlet("/getImgSimilarity")
@MultipartConfig
public class ImgSimilarityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME); 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //read
    	System.out.println("gother");
        Part imagePart = request.getPart("image");
        Mat queryImage = Imgcodecs.imdecode(new MatOfByte(imagePart.getInputStream().readAllBytes()), Imgcodecs.IMREAD_GRAYSCALE);
        System.out.println("gother1");
        //extract ORB algo features
        ORB orb = ORB.create();
        MatOfKeyPoint queryKeypoints = new MatOfKeyPoint();
        Mat queryDescriptors = new Mat();
        orb.detectAndCompute(queryImage, new Mat(), queryKeypoints, queryDescriptors);
        System.out.println("gother2");
        int highestScore = -1;
        String mostSimilarImagePath = null;

        List<String> imagePaths = getImagePathsFromDB();
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        for (String path : imagePaths) {
            Mat dbImage = Imgcodecs.imread(path, Imgcodecs.IMREAD_GRAYSCALE);
            MatOfKeyPoint dbKeypoints = new MatOfKeyPoint();
            Mat dbDescriptors = new Mat();
            orb.detectAndCompute(dbImage, new Mat(), dbKeypoints, dbDescriptors);

            //calc matches
            MatOfDMatch matches = new MatOfDMatch();
            matcher.match(queryDescriptors, dbDescriptors, matches);
            // # matches = score
            int score = matches.toArray().length;
            System.out.println("score: "+score);
            if (score > highestScore) {
                highestScore = score;
                mostSimilarImagePath = path;
            }
            System.out.println("gother3");
        }

        // Retrieve the postID corresponding to the most similar image
        //THIS IS ACTUALLY BUILDING ID - 3 lettre code
        System.out.println("gother4");
        System.out.println(mostSimilarImagePath);
        String postID = getPostIDFromImagePath(mostSimilarImagePath);
        System.out.println("postID: "+postID);
        System.out.println("gother5");
        // Set the response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonObject json = new JsonObject();
        json.addProperty("postID", postID);
        out.print(json.toString());
        out.flush();
        out.close();
        System.out.println("gother6");
        imagePart.delete();
    }

    private List<String> getImagePathsFromDB() {
    	ArrayList<String> listadd = new ArrayList<>();
    	
    	Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trojanstudy?user=root&password=root");
            String query = "SELECT image FROM posts";
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next())
            {
            	String imagePath = rs.getString(1);
            	System.out.println("Found image path: " + imagePath);
            	listadd.add(imagePath);
            }
            
            return listadd;

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

    private String getPostIDFromImagePath(String imagePath) {
    	Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trojanstudy?user=root&password=root");
            String query = "SELECT buildingID FROM posts WHERE image = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, imagePath);
            rs = pst.executeQuery();
            if(rs.next()) {
            	return rs.getString("buildingID");
            }

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
}