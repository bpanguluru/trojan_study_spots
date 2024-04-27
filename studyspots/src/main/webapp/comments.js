

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
            const buildingName = document.getElementById('buildingName').value;
            const comment = document.getElementById('comment').value;
            console.log('Building Name:', buildingName);
            console.log('Comment:', comment);
            console.log('Tags:', selectedTags);
            location.reload();
			//window.location.href = 'comments.html'; //redirect to comments again to know it was submitted
            // Need AJAX request to your server to insert data into the SQL table
            // For now, we just log it to the console and clear the form
           //clearForm(); // Optionally clear the form after submitting
            console.log('Submitting Form');
            
            
            //Ajax request with CommentsServlet
            fetch("http://localhost:8080/gkohanba_CSCI201_Assignment4/TradeServlet", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ trade: trade.ticker, user: user }) 
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // Show success message
                	trade.price = data.c; //ADDED
                    alert(`SUCCESS: ${data.message}`);
                } else {
                    // Show error message
                    alert(`FAILED: ${data.message}`);
                }
            })
            .catch(error => {
                console.error('Error during trade execution:', error);
            });
            
            
            
            
        
        }

        // Prevent form from actually submitting
        document.getElementById('commentForm').addEventListener('submit', function(event) {
            event.preventDefault();
            submitForm();
        });
