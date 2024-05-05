

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

        function clearForm() {
            document.getElementById('buildingName').value = '';
            document.getElementById('comment').value = '';
            selectedTags.length = 0;
            document.querySelectorAll('.selected').forEach(button => {
                button.classList.remove('selected');
                 
            });
            console.log('Form Cleared');
        }
        
        
        function submitForm() {
        	console.log(localStorage.getItem("currentUser"));
            const buildingName = document.getElementById('buildingName').value;
            const content = document.getElementById('comment').value;
            const tags = selectedTags.join(','); // Join the tags array into a comma-separated string

            
            console.log('Info', buildingName, content, tags);
            
            
            fetch("http://localhost:8080/studyspots/CommentsServlet", { 
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                
                body: JSON.stringify({ content, tags, buildingName })
                
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(`Comment added: ${data.message}`);
                } else {
                    alert(`Comment was not able to be posted: ${data.message}`);
                }
                clearForm(); // Clear the form on successful submission
                window.location.href = 'user_home.html';
            })
            .catch(error => {
                console.error('Error during form submission:', error);
            });
        }

        

        

        // Prevent form from actually submitting
        document.getElementById('commentForm').addEventListener('submit', function(event) {
            event.preventDefault();
            submitForm();
        });
