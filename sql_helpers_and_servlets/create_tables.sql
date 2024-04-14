CREATE TABLE Users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE Posts (
    postID INT AUTO_INCREMENT PRIMARY KEY,
    buildingName VARCHAR(255) NOT NULL,
    buildingID INT NOT NULL,
    description TEXT NOT NULL,
    -- storing image as the address of a directory in which we store all of em
    image VARCHAR(255),
    trojansRatingSum INT DEFAULT 0,
    numberTrojanRatings INT DEFAULT 0,
    tags BINARY(255)
);

CREATE TABLE Comments (
    commentID INT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    tags BINARY(255),
    postID INT,
    userID INT,
    FOREIGN KEY (postID) REFERENCES Posts(postID),
    FOREIGN KEY (userID) REFERENCES Users(userID)
);

CREATE TABLE Ratings (
    ratingID INT AUTO_INCREMENT PRIMARY KEY,
    ratingValue INT CHECK (ratingValue BETWEEN 1 AND 5),
    userID INT,
    postID INT,
    FOREIGN KEY (userID) REFERENCES Users(userID),
    FOREIGN KEY (postID) REFERENCES Posts(postID)
);