document.addEventListener('DOMContentLoaded', function () {
    const userId = localStorage.getItem('userId');
    const teamBox = document.getElementById('teamBox');
    const ttList = document.getElementById('ttList');

    if (userId) {
        console.log("User ID found:", userId);

        fetch(`/api/player-team/user/${userId}/teams-tournaments`)
            .then(response => response.json())
            .then(data => {
                console.log("Full Data Response:", data);

                ttList.innerHTML = ''; // Clear previous data

                if (data && data.length > 0) {
                    data.forEach(item => {
                        // Update property names based on API response
                        const tournamentName = item.tournamentName || "Not Available";
                        const teamName = item.teamName || "Not Available";

                        const tournamentButton = document.createElement('div');
                        tournamentButton.className = 'tournament_button';
                        tournamentButton.dataset.tournamentId = item.tournamentId; // Update property names
                        tournamentButton.dataset.teamId = item.teamId; // Update property names

                        const tournamentNameElement = document.createElement('p');
                        tournamentNameElement.className = 'tournament_name';
                        tournamentNameElement.textContent = `Tournament: ${tournamentName}`;
                        tournamentButton.appendChild(tournamentNameElement);

                        const teamNameElement = document.createElement('p');
                        teamNameElement.className = 'team_name';
                        teamNameElement.textContent = `Team: ${teamName}`;
                        tournamentButton.appendChild(teamNameElement);

                        tournamentButton.addEventListener('click', () => {
                            handleTournamentSelection(tournamentName, teamName);
                        });

                        ttList.appendChild(tournamentButton);
                    });

                    teamBox.style.display = 'block'; // Show team box
                } else {
                    ttList.innerHTML = '<p>No tournaments found for this user.</p>';
                }
            })
            .catch(error => {
                console.error("Error fetching teams and tournaments:", error);
                alert('Error fetching data.');
            });
    } else {
        console.error("No user ID found in localStorage. Redirecting to login.");
        window.location.href = "/login";
    }

    // Handle tournament selection
    function handleTournamentSelection(tournamentName, teamName) {
        console.log(`Tournament: ${tournamentName || "Not Available"}, Team: ${teamName || "Not Available"}`);
    }

    let home_btn = document.querySelector(".house_choice");
    let team_c_btn = document.querySelector(".team_c_choice");

    home_btn.addEventListener("click",()=>{
        window.location.href="/dashboardp";
    })
    team_c_btn.addEventListener("click",()=>{
        window.location.href="/tour_team_show";
    })
});
