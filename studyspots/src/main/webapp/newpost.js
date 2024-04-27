document.querySelectorAll('.tagButton').forEach(button => {
    button.addEventListener('click', function() {
        const tag = this.value;
        const index = selectedTags.indexOf(tag);
        if (index === -1) {
            selectedTags.push(tag);
            this.classList.add('selected');
        } else {
            selectedTags.splice(index, 1);
            this.classList.remove('selected');
        }
        console.log('Selected Tags:', selectedTags);
    });
});

const selectedTags = [];

document.getElementById("postForm").addEventListener("submit", function(event) {
    console.log("In postForm eventListener");
    event.preventDefault();
	let baseURL =  window.location.origin+"/trojan-study-spots/";
	var url = new URL("NewPostServlet", baseURL);
	if(document.postForm.getElementByID(building).value==""
	||document.postForm.getElementByID(locationTitle).value==""
	||document.postForm.getElementByID(description).value==""
	||selectedTags==[]
	{
		document.getElementById("postMsg").innerHTML = "All required fields must be completed to create a post.";
		return;
	}
    if(selectedTags.length()){
        document.getElementById("postMsg").innerHTML = "Need 3 or more tags selected.";
        return;
    }
	var params = {
		action: "post",
		building: document.postForm.getElementByID(building).value,
		locationTitle: document.postForm.getElementByID(locationTitle).value,
		description: document.postForm.getElementByID(decription).value,
        tags: selectedTags
	};
	url.search = new URLSearchParams(params).toString();
	fetch(url)
		.then(response => response.json())
	    .then(data => {
			if(data.userID === "-1"){
				console.log("Username already registered.");
				document.getElementById("postMsg").innerHTML = "Username already registered.";
			} else if (data.userID==="-2"){
				console.log("email already registered.");
				document.getElementById("postMsg").innerHTML = "Email already registered.";
			} else{
				console.log("New User NOW logging in w/ ID: "+data.userID);
				localStorage.setItem("currentUser", data.userID);
				window.location.href = 'index.html';
			}
    	}).catch(function (error) {
      		console.log('request failed', error)
    	});
    
        

}