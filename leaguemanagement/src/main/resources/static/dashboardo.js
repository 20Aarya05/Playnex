document.addEventListener('DOMContentLoaded', function() {
    const userId = localStorage.getItem('userId');

    // Log the userId
    console.log("User ID:", userId);

    if (userId) {
        // Fetch the user details from the backend using the stored userId
        fetch(`/user/${userId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                const nameElement = document.getElementById('name');
                const ageElement = document.getElementById('age');
                const ratingElement = document.getElementById('rating');

                if (nameElement) {
                    nameElement.textContent = data.name || 'User data is incomplete.';
                }
                
                if (ageElement) {
                    ageElement.textContent = data.age || 'Age data is incomplete.';
                }

                if (ratingElement) {
                    const rating = data.rating || 0; // Default to 0 if no rating
                    ratingElement.innerHTML = generateStars(rating);
                }
            })
            .catch(error => {
                console.error('Error fetching user data:', error);
                document.getElementById('name').textContent = 'Failed to load user data.';
                document.getElementById('age').textContent = 'Failed to load age data.';
            });
    } else {
        console.error('No userId found in localStorage.');
        document.getElementById('name').textContent = 'No user information available.';
        document.getElementById('age').textContent = 'No age information available.';
    }
});

function generateStars(rating) {
    const stars = [];
    const fullStars = Math.floor(rating); // Full stars
    const hasHalfStar = (rating % 1) >= 0.5; // Check for half star

    for (let i = 1; i <= 5; i++) {
        if (i <= fullStars) {
            stars.push('<i class="fa-solid fa-star"></i>'); // Full star
        } else if (i === fullStars + 1 && hasHalfStar) {
            stars.push('<i class="fa-solid fa-star-half-alt"></i>'); // Half star
        } else {
            stars.push('<i class="fa-regular fa-star"></i>'); // Empty star
        }
    }
    return stars.join('');
}

let user_data = document.querySelector(".user_data");

let home_btn = document.querySelector(".house_choice");
let team_c_btn = document.querySelector(".team_c_choice");
let tournament_c_btn = document.querySelector(".tournament_c_choice");

home_btn.addEventListener("click",()=>{
    window.location.href="/dashboardo";
})
team_c_btn.addEventListener("click",()=>{
    window.location.href="/createTeam";
})
tournament_c_btn.addEventListener("click",()=>{
    window.location.href="/createTournament";
})
