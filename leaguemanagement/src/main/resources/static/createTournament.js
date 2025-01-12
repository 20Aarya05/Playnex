// Navbar button navigation
let home_btn = document.querySelector(".house_choice");
let team_c_btn = document.querySelector(".team_c_choice");
let tournament_c_btn = document.querySelector(".tournament_c_choice");

home_btn.addEventListener("click", () => {
    window.location.href = "/dashboardo"; // Change the URL as needed
});
team_c_btn.addEventListener("click", () => {
    window.location.href = "/createTeam"; // Change the URL as needed
});
tournament_c_btn.addEventListener("click", () => {
    window.location.href = "/createTournament"; // Change the URL as needed
});

// Listen for the number of teams input to dynamically add team name fields
const tournamentTeamsInput = document.getElementById("tournamentTeams");
const teamsContainer = document.getElementById("teams-container");

tournamentTeamsInput.addEventListener("change", function () {
    // Clear the teams container to avoid duplicating fields
    teamsContainer.innerHTML = "<h2>Team Names</h2>";

    const numberOfTeams = parseInt(tournamentTeamsInput.value);

    if (numberOfTeams > 0) {
        for (let i = 0; i < numberOfTeams; i++) {
            // Create a label and input field for each team
            const teamLabel = document.createElement("label");
            teamLabel.setAttribute("for", `teams[${i}].teamName`);
            teamLabel.textContent = `Team ${i + 1} Name: `;

            const teamInput = document.createElement("input");
            teamInput.setAttribute("type", "text");
            teamInput.setAttribute("id", `teams[${i}].teamName`);
            teamInput.setAttribute("name", `teams[${i}].teamName`);
            teamInput.setAttribute("class", "team-input");
            teamInput.setAttribute("required", true);

            // Append the label and input to the container
            teamsContainer.appendChild(teamLabel);
            teamsContainer.appendChild(teamInput);
            teamsContainer.appendChild(document.createElement("br")); // Line break for layout
        }        
    }
});
