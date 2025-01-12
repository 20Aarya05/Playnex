const togglePassword = document.getElementById('togglePassword');
const passwordInput = document.getElementById('password');

// Toggle password visibility
togglePassword.addEventListener('click', function() {
    // Toggle the type attribute
    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
    passwordInput.setAttribute('type', type);

    // Toggle the eye icon class
    this.classList.toggle('fa-eye-slash');
});

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    console.log("Submitting login form with email:", email, "and password:", password); // Log form data

    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            email: email,
            password: password
        })
    })
    .then(response => {
        console.log("Response status:", response.status); // Log the response status
        if (response.ok) {
            return response.json(); // Expecting a JSON response
        } else {
            throw new Error('Invalid response from the server');
        }
    })
    .then(data => {
        console.log("Received data:", data); // Log the response data, including the role

        if (data.userId && data.name && data.role) {
            localStorage.setItem('userId', data.userId);

            // Redirect based on user role
            if (data.role === 'player') {
                window.location.href = '/dashboardp';
            } else if (data.role === 'coach') {
                window.location.href = '/dashboardc';
            } else if (data.role === 'owner') {
                window.location.href = '/dashboardo';
            } else {
                alert('Unknown role. Please contact support.');
            }
        } else {
            alert('Invalid credentials. Please try again.');
            document.getElementById('email').value = '';
            document.getElementById('password').value = '';
        }
    })
    .catch(error => {
        console.error('Error during login:', error);
        alert('An error occurred. Please try again.');
    });
});