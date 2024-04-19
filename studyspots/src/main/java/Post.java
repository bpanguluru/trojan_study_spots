import java.util.HashSet;
import java.util.Set;

public class Post {
// comments are arrays 
	/*
	 *  postID INT AUTO_INCREMENT PRIMARY KEY,
    buildingName VARCHAR(255) NOT NULL,
    buildingID VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    -- storing image as the address of a directory in which we store all of em
    image VARCHAR(255),
    trojansRatingSum INT DEFAULT 0,
    numberTrojanRatings INT DEFAULT 0,
    tags BINARY(255)
	 */
	int postId;
	String buildingName; 
	String buildingID; 
	String description; 
	int trojansRatingSum;
	int numberTrojanRatings; 
	Set<Boolean> tags = new HashSet<>();
}
