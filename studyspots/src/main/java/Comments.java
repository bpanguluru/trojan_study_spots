import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import studyspots.Tags;

public class Comments {
	
	
	 	//private int commentID;
	    private String content;
	    private String tags;
	    private String buildingName;

	    // Constructor
	    public Comments(String content, String tags, String buildingName) {
	        this.content = content;
	        this.tags = tags;
	        this.buildingName = buildingName;
	    }

	    // Getters
	    /*public int getCommentID() {
	        return commentID;
	    }*/

	    public String getContent() {
	        return content;
	    }

	    public String getTags() {
	        return tags;
	    }
	    
	    public String getBuildingName() {  // Getter for buildingName
	        return buildingName;
	    }


	    // Setters
	   /* public void setCommentID(int commentID) {
	        this.commentID = commentID;
	    }*/

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public void setTags(String tags) {
	        this.tags = tags;
	    }
	    
	    public void setBuildingName(String buildingName) {  // Setter for buildingName
	        this.buildingName = buildingName;
	    }
	    
	    

}
