/**
 * users should be able to select a building name and all comments for that building should
 * fill up the page
 */


 
 document.querySelector('form').addEventListener('submit', function(event) {
	 
    event.preventDefault();
    const buildingName = document.getElementById('buildingName').value;
    console.log("comment search building: ", buildingName);
    
    fetch("http://localhost:8080/studyspots/commentsSearchServlet", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ buildingName })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            displayComments(data.comments);
        } else {
            alert(`Failed to retrieve comments: ${data.message}`);
        }
    })
    .catch(error => {
        console.error('Error during search:', error);
        alert('Error searching comments.');
    });
    
    
    
});

 
 function displayComments(comments) {
	    const container = document.getElementById('commentsSection');
	    container.innerHTML = ''; // Clear previous results

	    if (comments.length === 0) {
	        container.innerHTML = '<p>No comments found for this building.</p>';
	    } else {
	        const commentsHtml = comments.map(comment => {
	            const tagHTML = comment.tags.split(',').map(tag => 
	                `<div class="tag-box">${tag.trim()}</div>` // trim() removes whitespace
	            ).join(''); 

	            return `<div class="comment-container">
	                <p><strong>Content:</strong> ${comment.content}</p>
	                <p><strong>Tags:</strong> ${tagHTML}</p>
	            </div>`;
	        }).join('');
	        container.innerHTML = commentsHtml;
	    }
	}