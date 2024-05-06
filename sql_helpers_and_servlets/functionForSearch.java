function displayCards(buildingID) {
    // Fetch post data from the server
    fetch(`getPostsServlet`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ buildingPrompt: buildingID })
    })
    .then(response => response.json())
    .then(data => {
        const postsContainer = document.createElement('div');
        postsContainer.className = 'posts-container';
        
        data.postsList.forEach(post => {
            const card = document.createElement('div');
            const bigPath = post.imagePath;
            const rightPath = convertToRelativePath(bigPath);
            card.className = 'card';
            card.innerHTML = `
                <div class="card-image"><img src="${rightPath}" alt="Building Image"></div>
                <div class="card-content">
                    <div class="card-title">${post.buildingName}</div>
                    <div>Building ID: ${post.buildingID}</div>
                    <div>Location Title: ${post.locationTitle}</div>
                    <div>Description: ${post.description}</div>
                    <div>Ratings: ${post.trojansRatingSum / Math.max(post.numberTrojanRatings, 1)}</div>
                    <div>Tags: ${post.tags}</div>
                </div>
            `;
            postsContainer.appendChild(card);
        });

        document.body.appendChild(postsContainer);
    })
    .catch(error => {
        console.error('Error fetching posts:', error);
        alert('Error fetching post details.');
    });
} 