import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.io.output.*;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.PrintWriter;

@WebServlet("/ImgUploadServlet")
public class ImgUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter pw = response.getWriter();
        Gson gson = new Gson();
    	
    	DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        //CHANGE THIS FOR YOUR OWN MACHINE
        String uploadPath = "C:\\Users\\Allena Villanueva\\OneDrive\\Desktop\\csci201 group project\\trojan_study_spots\\studyspots\\src\\main\\webapp\\uploaded_imgs";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) 
        {
        	uploadDir.mkdir();
        }

        try {
            List<FileItem> formItems = upload.parseRequest(request);
            String fileName="";
            String filePath = "";
            System.out.println(request);        //for each item in the request
            for (FileItem item : formItems) {
                if (!item.isFormField()) {
                    fileName = new File(item.getName()).getName();
                    
                    //only if we have the patience to find this jar
//                    String baseName = FilenameUtils.getBaseName(fileName); // Requires Apache Commons IO
//                    String extension = FilenameUtils.getExtension(fileName);
//                    String uniqueFileName = baseName + "_" + System.currentTimeMillis() + "." + extension;
//                    
                    filePath = uploadPath+File.separator+fileName;
                    System.out.println(filePath);
                    File storeFile = new File(filePath);
                    if (storeFile.exists()) {
                        storeFile.delete(); // Delete the existing file
                    }

                    //saves to uploaded_imgs folder
                    item.write(storeFile);
                }
            }

            saveImagePathToDatabase(fileName);
            response.sendRedirect("user_home.html");
            

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void saveImagePathToDatabase(String imagePath) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/trojanstudy";
            conn = DriverManager.getConnection(url, "root", "root");
            
            String getLastPostIdQuery = "SELECT MAX(postID) FROM Posts";
            PreparedStatement getLastPostIdStmt = conn.prepareStatement(getLastPostIdQuery);
            ResultSet lastPostIdResult = getLastPostIdStmt.executeQuery();
            int lastPostId = 0;
            if (lastPostIdResult.next()) {
                lastPostId = lastPostIdResult.getInt(1);
            }
            lastPostIdResult.close();
            getLastPostIdStmt.close();

            String updateSql = "UPDATE Posts SET image = ? WHERE postID = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            // Assuming you have the new image string in a variable named 'newImage'
            updateStmt.setString(1, imagePath);
            updateStmt.setInt(2, lastPostId);
            updateStmt.executeUpdate();
            updateStmt.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ignored) { }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignored) { }
            }
        }
    }
}