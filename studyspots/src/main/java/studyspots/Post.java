package studyspots;

import java.util.HashSet;
import java.util.Set;

public class Post {
	int postID;
	String buildingName; 
	String buildingID; 
	String description;
	String imagePath;
	int trojansRatingSum;
	int numberTrojanRatings; 
	String tagString;
	Set<Boolean> tags = new HashSet<>();
	
    public Post(String bN, String bID, String d, int tRS, int nTR, String imgPath, String tagString) {
        this.buildingName = bN;
        this.buildingID = bID; 
        this.description = d; 
        this.trojansRatingSum = tRS;
        this.numberTrojanRatings = nTR;
        this.imagePath = imgPath;
        this.tagString = tagString;
    }
    
    public String getTagString() {
        return this.tagString;
    }

    public void settagString(String tg) { // tID = temporary id; 
    	tagString = tg;
    }

    public int getImgPath() {
        return postID;
    }

    public void setImgPath(String path) { // tID = temporary id; 
        imagePath = path;
    }
    
    public int getPostID() {
        return postID;
    }

    public void setPostID(int tID) { // tID = temporary id; 
        postID = tID;
    }
    
    public String getBuildingName() {
    	return buildingName;
    }
    
    public void setBuildingID(String tBID) {
    	buildingName = tBID;
    }
    
    public String getBuildingID() {
    	return buildingID;
    }
    
    public void setBuildingName(String tBN) {
    	buildingName = tBN;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String d) {
    	description = d;
    }
    
    public int getTrojansRatingSum() {
    	return trojansRatingSum;
    }

    public void setTrojansRatingSum(int tTRS) { // tID = temporary id; 
    	trojansRatingSum = tTRS;
    }
    
    public int getNumberTrojanRatings() {
    	return numberTrojanRatings;
    }

    public void setNumberTrojanRatings(int tNTR) { // tID = temporary id; 
    	trojansRatingSum = tNTR;
    }
}
