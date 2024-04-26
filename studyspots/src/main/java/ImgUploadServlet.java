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
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // Directory where the uploaded files will be saved
        String uploadPath = "../webapp/uploaded_imgs";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        try {
            // Parse the request to get file items.
            List<FileItem> formItems = upload.parseRequest(request);
            String fileName="";

            // Process the uploaded items
            for (FileItem item : formItems) {
                if (!item.isFormField()) {
                    fileName = new File(item.getName()).getName();
                    String filePath = uploadPath+"/"+fileName;
                    File storeFile = new File(filePath);

                    // Save the file on disk
                    item.write(storeFile);
                }
            }

            // Store the file path in the database
            saveImagePathToDatabase(fileName);

            /*response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Upload has been done successfully!");*/
            

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void saveImagePathToDatabase(String imagePath) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/trojanstudy";
            conn = DriverManager.getConnection(url, "root", "root");

            // Prepare the SQL statement
            String sql = "INSERT INTO Posts (image) VALUES (?)";
            stmt = conn.prepareStatement(sql);

            // Set the image path
            stmt.setString(1, imagePath);

            // Execute the statement
            stmt.executeUpdate();
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        } finally {
            // Close the connection and statement
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException ignored) { }
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignored) { }
            }
        }
    }
}
