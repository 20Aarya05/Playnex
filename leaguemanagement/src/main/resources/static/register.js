document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('registerForm');
    
    form.addEventListener('submit', (event) => {
        event.preventDefault();  // Prevent the form from submitting
  
        // Validate the form inputs
        const name = document.getElementById('name').value.trim();
        const age = document.getElementById('age').value.trim();
        const number = document.getElementById('number').value.trim();
        const email = document.getElementById('email').value.trim();
        const userType = document.getElementById('userType').value;
        const password = document.getElementById('password').value.trim();
        const confirmPassword = document.getElementById('confirmPassword').value.trim();
        
        if (!name || !number || !age || !email || !userType || !country || !password || !confirmPassword) {
            alert('Please fill out all fields.');
            return;
        }
        
        if (password !== confirmPassword) {
            alert('Passwords do not match.');
            return;
        }
        
        // Simulate form submission (this can be replaced with actual AJAX request)
        console.log({
            name,
            age,
            number,
            email,
            userType,
            password
        });
  
        alert('Registration successful!');
  
        // Redirect to the login page after successful registration
        window.location.href = '/login';  // Redirect to login page
    });
  });