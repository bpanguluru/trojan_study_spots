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
l
    function submitForm(event) {
        event.preventDefault(); // Prevent the default form submission
        
        // Get the number of checked checkboxes
        var checkedCheckboxes = document.querySelectorAll('input[name="rating[]"]:checked');
        var checkedCount = checkedCheckboxes.length;
        
        // Display the count
        var formValuesDiv = document.getElementById("formValues");
        formValuesDiv.textContent = checkedCount + " checkboxes checked";
    }