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
    return count > 0 ? (sum / count).toFixed(1) : 'No ratings yet';
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
         associatedLabel.classList.add('filled-img1');
    }
    function toggleImageClass(checkboxId) {
        var checkbox = document.getElementById(checkboxId);
        var associatedLabel = document.querySelector('label[for="' + checkboxId + '"]');
        
        if (checkbox.checked) {
            associatedLabel.classList.remove('blank-img1');
            associatedLabel.classList.add('filled-img1');
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