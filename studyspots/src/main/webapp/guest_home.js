window.addEventListener('load', function () {
    fetchPosts();
});


function fetchPosts() {
    fetch('PostServlet')
        .then(response => response.json())
        .then(data => {
            console.log(data);
            return data;  // This is necessary to pass the data to the next .then()
        })
        .then(posts => {
            posts.forEach(post => {
                createPostCard(post);
                console.log("Here");
            });
        })
        .catch(error => console.error('Error fetching posts:', error));
}


function createPostCard(post) {
    const container = document.querySelector('.search-container');
    const card = document.createElement('div');
    card.className = 'card';
    card.innerHTML = `
        <div class="card-image"><img src="${post.image}" alt="Building Image"></div>
        <div class="card-content">
            <div class="card-title">${post.buildingName}</div>
            <div>${post.buildingID}</div>
            <div>${post.description}</div>
            <div class="rating">Rating: ${calculateRating(post.trojansRatingSum, post.numberTrojanRatings)}</div>
            <button class="comments-link">Comments...</button>
        </div>
    `;
    container.appendChild(card);
}

function calculateRating(sum, count) {
	return count > 0 ? Math.round(sum / count) : 'No ratings yet';
 }
  
      // Get all the checkboxes
    var checkboxes = document.querySelectorAll('input[type="checkbox"]');
    
    // Attach event listeners to each checkbox
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            // Get the ID of the clicked checkbox
            var checkboxId = checkbox.id;
            
            // Find the index of the clicked checkbox in the list of checkboxes
            var index = Array.from(checkboxes).indexOf(checkbox);
            
            // If the clicked checkbox is not the first one, ensure the previous one is checked
            if (index > 0) {
            	var curr = index;
            	while(curr >= 0) {
            		if (checkboxes[curr].checked == false) {
            			checkboxes[curr].checked = true;
                        makeChecked(checkboxes[curr].id); // Update the image class
                    }
                    curr = curr - 1;
            	}
            }
        });
    });

    function makeChecked(checkboxId) {
    	 var checkbox = document.getElementById(checkboxId);
         var associatedLabel = document.querySelector('label[for="' + checkboxId + '"]');
         associatedLabel.classList.remove('blank-img1');
         associatedLabel.classList.add('filled-img1');df
    }
    function toggleImageClass(checkboxId) {
        var checkbox = document.getElementById(checkboxId);
        var associatedLabel = document.querySelector('label[for="' + checkboxId + '"]');
        
        if (checkbox.checked) {
            associatedLabel.classList.remove('blank-img1');
            associatedLabel.classList.add('filled-img1');df
        } else {
            associatedLabel.classList.remove('filled-img1');
            associatedLabel.classList.add('blank-img1');
        }
    }
    function submitForm(event) {
        event.preventDefault(); // Prevent the default form submission
        
        // Get the number of checked checkboxes
        var checkedCheckboxes = document.querySelectorAll('input[name="rating[]"]:checked');
        var checkedCount = checkedCheckboxes.length;
        
        // Display the count
        var formValuesDiv = document.getElementById("formValues");
        formValuesDiv.textContent = checkedCount + " checkboxes checked";
    }
    function displayCurrPosts() {       
        var xhr = new XMLHttpRequest();
        xhr.open('GET', 'CurrentPostsServlet?', true);
        xhr.responseType = 'json'; // Expecting JSON response
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    var data = xhr.response;
                    generateCards(data); // Call generateCards() with the fetched data
                    //localStorage.setItem("postID", JSON.stringify(data)); // Move inside the if block
                } else {
                    var message = xhr.responseText;
                    alert("Error: " + message); // Display error message
                }
            }
        };
        xhr.send();
    }

function displayPostInformation(data) {
    // Parse the JSON string into a JavaScript object
    var jsonObject = JSON.parse(data);

    // Extract values from the JSON object
    var postName = jsonObject.name;
    var buildingID = jsonObject.buildingID;
    var description = jsonObject.description;
    var trojansRatingSum = jsonObject.trojansRatingSum;
    var numberTrojanRatings = jsonObject.numberTrojanRatings;

    // Update HTML content with the extracted values
    document.getElementById("postName").textContent = postName;
    document.getElementById("buildingID").textContent = buildingID;
    document.getElementById("description").textContent = description;
    document.getElementById("trojansRatingSum").textContent = trojansRatingSum;
    document.getElementById("numberTrojanRatings").textContent = numberTrojanRatings;
}
	
function generateCards(data) {
    var cardContainer = document.getElementById("cardContainer");
    
    // Clear previous cards
    cardContainer.innerHTML = "";

    // Parse the JSON data
    var jsonData = JSON.parse(data);
    
    var index = 0; // Initialize index variable

    // Loop through the JSON array and generate cards dynamically
    jsonData.forEach(function(item) {
        var buildingName = item.name;
        var buildingID = item.buildingID;
        var description = item.description;
        var trojansRatingSum = item.trojansRatingSum;
        var numberTrojanRatings = item.numberTrojanRatings;
        var avgRatingImageID = "avgRatingImage_" + index;

        // Create card elements and populate with data
        var card = document.createElement("div");
        card.className = "card";
        var cardContent = `
            <div class="card-image">Image</div>
            <div class="card-content">
                <div class="card-title">Building: ${buildingName}</div>
                <div>Location: ${buildingID}</div>
                <div>Description: ${description}</div>
                <div class="rating">
                <div class="rating-image">Building Rating:
                    <img id="${avgRatingImageID}" src="" alt="Average Rating">
                </div>
            </div>
        `;

        card.innerHTML = cardContent;
        cardContainer.appendChild(card);

        // Call displayAvgRating for each card
        displayAvgRating(trojansRatingSum, numberTrojanRatings, avgRatingImageID);

        // Increment index for the next card
        index++;
    });
}

function displayAvgRating(sum, count, avgRatingImageID) {
    // Calculate average rating
    var avgTrojanRating = count > 0 ? Math.round(sum / count) : 0;

    // Get the image element for average rating
    var avgRatingImage = document.getElementById(avgRatingImageID);

    // Set image source based on the average rating
    if (avgTrojanRating >= 1 && avgTrojanRating <= 5) {
        avgRatingImage.src = avgTrojanRating + " filled.png";
        avgRatingImage.alt = "Rating " + avgTrojanRating;
    } else {
        avgRatingImage.src = "no-rating.png";
        avgRatingImage.alt = "No rating available";
    }
}

function runOnLoadFunctions() {
    // Call the first function
    displayCurrPosts();

    // Call the second function
    // Add more function calls as needed
}