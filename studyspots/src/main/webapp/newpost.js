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


   /* function submitForm(event) {
        event.preventDefault(); // Prevent the default form submission
        
        // Get the number of checked checkboxes
        var checkedCheckboxes = document.querySelectorAll('input[name="rating[]"]:checked');
        var checkedCount = checkedCheckboxes.length;
        
        // Display the count
        var formValuesDiv = document.getElementById("formValues");
        formValuesDiv.textContent = checkedCount + " checkboxes checked";
    }*/

const selectedTags = [];

// update the selectedTags list every time you click on the buttons
document.querySelectorAll('.tagButton').forEach(button => {
    button.addEventListener('click', function() {
        const tag = parseInt(this.value);
        const index = selectedTags.indexOf(tag);
        if (index === -1) {
            selectedTags.push(tag);
            //this.classList.add('selected');
            document.getElementById(this.id).style.backgroundColor="white";
            document.getElementById(this.id).style.color="#00bcd4";
            console.log("added "+tag+" to selected");
        } else {
            selectedTags.splice(index, 1);
            //this.classList.remove('selected');
            document.getElementById(this.id).style.backgroundColor="#00bcd4";
            document.getElementById(this.id).style.color="white";
            console.log("removed "+tag+" from selected");
        }
        console.log('Selected Tags:', selectedTags);
    });
});

//submit the form-do form validation before submitting
document.getElementById("submitPost").addEventListener("click", function(event) {
    event.preventDefault();
    console.log("In postForm eventListener");
    
    var checkedCheckboxes = document.querySelectorAll('input[name="rating[]"]:checked');
    var checkedCount = checkedCheckboxes.length;
    var buildingPart = document.getElementById("building");
    var bdSelec = buildingPart.options[buildingPart.selectedIndex];
    var bdgVal = bdSelec.value;
    var bdgName = bdSelec.text;
    console.log("building val: "+bdgVal+" building name: "+bdgName);
    
    
	let baseURL =  window.location.origin+"/trojan-study-spots/";
	var url = new URL("NewPostServlet", baseURL);
	if(bdgVal==""
	||document.getElementById("locationTitle").value==""
	||document.getElementById("description").value==""
	||selectedTags==[]||checkedCount==0)
	{
		document.getElementById("postMsg").innerHTML = "Building, location title, description, 3 tags, and a rating are required to make a post.";
		return;
	}
    if(selectedTags.length()<3){
        document.getElementById("postMsg").innerHTML = "Need 3 or more tags selected.";
        return;
    }
    let tagString = "0000000000000000000000"
    for (let i=0; i< selectedTags.length; i++){
		tagString = tagString.substring(0, selectedTags[i]) + '1' + str.substring(selectedTags[i] + 1);
	}
    console.log("tagString: "+tagString);
    
    //handling oversimilarity
    if(document.getElementById("locationTitle").value=="sidney harman polymath center"){
		document.getElementById("postMsg").innerHTML = "Hey! It looks like this building area has already been posted. \nIf you're sure it's new, you can submit, but we recommend looking at other posts under LVL and rating or commenting on one of those";
		return;
	}
    
    
    //swearword filtering
    var swearWords = ["fuck", "fucking", "shit",  "ass"];
	var regexPattern = new RegExp("\\b(" + swearWords.join("|") + ")\\b", "gi");
	var textDescription = document.postForm.getElementById("description").value;
	var filteredText = textDescription.replace(regexPattern, "***");
	console.log(filteredText);
    
	var params = {
		userID: localStorage.getItem("currentUser"), // 
		action: "post",
		buildingID: bdgVal,
		buildingName: bdgName,
		locationTitle: document.postForm.getElementById("locationTitle").value,
		description: filteredText,
        tags: tagString,
        rating: checkedCount,
        image: fname,
	};
	console.log("params: u-"+localStorage.getItem("currentUser")
	+" a-post bID-"+bdgVal+" bname-"+bdgName
	+" loc-"+document.postForm.getElementById("locationTitle").value
	+" dsc-"+filteredText+" tags-"+tagString)
	+" r-"+checkedCount;
	+" image-"+fname;
	url.search = new URLSearchParams(params).toString();
	fetch(url)
		.then(response => response.text())
	    .then(data => {
			if(data === "FAILURE"){
				console.log("Post failure");
				document.getElementById("postMsg").innerHTML = "Post could not be submitted.";
			} else if (data.userID==="OVERSIMILAR"){
				console.log("Post oversimilar");
				document.getElementById("postMsg").innerHTML = "Our records indicate a post about this location (or a very similar one) already exists.";
			} else{
				console.log("New Post success");
				window.location.href = 'user_home.html';
			}
    	}).catch(function (error) {
      		console.log('request failed', error)
    	});
});