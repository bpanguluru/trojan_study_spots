document.querySelector('.register-button').addEventListener('click', function() {
	
	var email = document.querySelector('.email-input').value;
    var username = document.querySelector('.username-input').value;
    var password = document.querySelector('.password-input').value;
    var passwordconf = document.querySelector('.password-conf-input').value;
    
    if (password !== passwordconf) {
        alert('Passwords do not match');
        return;
    }

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'RegisterUser', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                if (xhr.responseText === 'success') {
                    // Redirect to home page or perform other actions for successful login
                    window.location.href = 'user_home.html';
                } else {
                    // Display error message for unsuccessful login
                    alert(xhr.responseText);
                }
            } else {
                // Handle other status codes if needed
            }
        }
    };
    xhr.send('email=' + encodeURIComponent(email) + '&username=' + encodeURIComponent(username) + '&password=' + encodeURIComponent(password));
    
});