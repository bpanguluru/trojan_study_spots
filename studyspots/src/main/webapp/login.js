function redirectToHome() {

        window.location.href = 'guest_home.html';

    }

function redirectToReg()

    {

      window.location.href = "register.html"

    }



document.querySelector('.login-button').addEventListener('click', function() {

	

    var username = document.querySelector('.username-input').value;

    var password = document.querySelector('.password-input').value;



    var xhr = new XMLHttpRequest();

    xhr.open('POST', 'VerifyLogin', true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    xhr.onreadystatechange = function() {

        if (xhr.readyState === XMLHttpRequest.DONE) {

            if (xhr.status === 200) {

                if (xhr.responseText !== 'failure') {

                    // Redirect to home page or perform other actions for successful login

                    localStorage.setItem('username', xhr.responseText);

                    window.location.href = 'user_home.html';

                } else {

                    // Display error message for unsuccessful login

                    alert('Invalid username or password');

                }

            } else {

                // Handle other status codes if needed

            }

        }

    };

    xhr.send('username=' + encodeURIComponent(username) + '&password=' + encodeURIComponent(password));

    

});