document.addEventListener('DOMContentLoaded', () => {
    const searchBtn = document.getElementById('searchBtn');
    const tournamentIdInput = document.getElementById('tournamentIdInput');
    const teamBox = document.getElementById('teamBox');
    const teamList = document.getElementById('teamList');
    const playerBox = document.getElementById('playerBox');
    const selectedTeamName = document.getElementById('selectedTeamName');
    const playerList = document.getElementById('playerList');
    const addPlayerContainer = document.querySelector('.add_player_cont');


    // Function to render team list based on selected tournament
    async function fetchTeamsByTournamentId(tournamentId) {
        try {
            const response = await fetch(`/api/tournaments/${tournamentId}/teams`);
            if (response.ok) {
                const teams = await response.json();
                console.log('Teams for Tournament ID:', tournamentId);
                console.log(`Number of teams: ${teams.length}`);
                
                teamList.innerHTML = '';

                if (teams.length === 0) {
                    teamList.innerHTML = '<p>No teams found for this tournament.</p>';
                    return;
                }

                teams.forEach(team => {
                    const teamButton = document.createElement('button');
                    teamButton.className = 'team_button';
                    teamButton.textContent = team.teamName;
                    teamButton.style.height = "60px";
                    teamButton.style.borderRadius = "10px";
                    teamButton.style.fontSize = "2rem";
                    teamButton.dataset.teamId = team.teamId;

                    teamButton.addEventListener('click', () => {
                        console.clear();
                        console.log(`Team ID: ${team.team_id} \n`);
                        console.log(`No. of player: ${team.teamMembers}`);
                        handleTeamSelection(team.teamName,team.teamMembers,tournamentId)
                        displayUserDetails(team.team_id,team.teamMembers,tournamentId);  // Call the function with team ID 
                    });

                    teamList.appendChild(teamButton);
                });

                teamBox.style.display = 'block';
                playerBox.style.display = 'none';
            } else {
                console.error('Error fetching teams: Tournament not found');
                alert('Tournament not found.');
            }
        } catch (error) {
            console.error('Error fetching teams:', error);
            alert('Error fetching teams.');
        }
    }

    // Event listener for search button
    searchBtn.addEventListener('click', () => {
        console.clear();
        const tournamentId = tournamentIdInput.value.trim();
        if (tournamentId) {
            fetchTeamsByTournamentId(tournamentId);
        } else {
            alert('Please enter a tournament ID.');
        }
    });

    // Function to handle team selection
    function handleTeamSelection(teamName, totalPlayers,tournamentId) {
        selectedTeamName.textContent = `Selected Team: ${teamName}`;
        playerBox.style.display = 'block';
    }

    function displayUserDetails(teamId,teamMembers,tournamentId) {
        const apiUrl = `http://localhost:9090/teams/${teamId}/user-details`;
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                playerList.innerHTML = '';
                console.log(`User Details for Team ${teamId}:`, data);
    
                // Assuming data is an array of strings like:
                // ["User ID: 420201010001, Name: Dhruv Chudasama, Age: 19", "User ID: 420201010002, Name: test, Age: 26"]
                data.forEach((userDetail, index) => {
                    const nameMatch = userDetail.match(/Name: ([^,]+)/);
                    const ageMatch = userDetail.match(/Age: (\d+)/);
    
                    const name = nameMatch ? nameMatch[1] : 'Unknown';
                    const age = ageMatch ? ageMatch[1] : 'Unknown';
    
                    console.log(`Name: ${name}, Age: ${age}`);
    
                    displayplayerItem(teamId,name,age,index,tournamentId,teamMembers);
                });

                // Get the number of players already added
                const numberOfPlayers = data.length;
                console.log(`Number of players already added: ${numberOfPlayers}`);

                if (!data || numberOfPlayers === 0) {
                    playerList.innerHTML = '<p>No players found for this team.</p>';
                    renderAddPlayerButtons(teamMembers,teamId,teamMembers,tournamentId);
                    return;
                }

                renderAddPlayerButtons(teamMembers - numberOfPlayers,teamId,teamMembers,tournamentId);
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }

    function displayplayerItem(teamId,name,age,index,tournamentId,teamMembers){
        const playerItem = document.createElement('div');
        playerItem.className = 'player_item';
        playerItem.innerHTML = `
            <span><strong>Name:</strong> ${name}</span>
            <span><strong>Age:</strong> ${age}</span>
            <button class="remove_player_btn" data-team="${teamId}" data-index="${index}">-</button>
        `;
        playerList.appendChild(playerItem);

        const removePlayerBtn = playerItem.querySelector('.remove_player_btn');
        removePlayerBtn.addEventListener('click', () => {
        removePlayerFromTeam(teamId, name, tournamentId,teamMembers);
        });  
    }

    // Function to render "Add Player" buttons based on the number of players
    function renderAddPlayerButtons(remainingSlots,teamId,teamMembers,tournamentId) {
        addPlayerContainer.innerHTML = '';
        for (let i = 0; i < remainingSlots; i++) {
            const addPlayerBtn = document.createElement('div');
            addPlayerBtn.className = 'add_player_btn';
            addPlayerBtn.innerHTML = '<p>+</p>';
            addPlayerBtn.addEventListener('click', () => addPlayerToTeam(teamId,teamMembers,tournamentId));
            console.clear();
            addPlayerContainer.appendChild(addPlayerBtn);
        }
    }

    function addPlayerToTeam(teamId,teamMembers,tournamentId) {
        const teamName = selectedTeamName.textContent.replace('Selected Team: ', '');
    
        const addPlayerForm = document.createElement('div');
        addPlayerForm.className = 'add_player_form';
        addPlayerForm.className = 'add_player_form';
    
        const phoneInput = document.createElement('input');
        phoneInput.type = 'tel';
        phoneInput.placeholder = 'Enter Player Phone Number';
        phoneInput.className = 'player_phone_input';
    
        const submitPButton = document.createElement('button');
        submitPButton.textContent = 'Submit';
        submitPButton.className = 'submit_player_btn';
    
        addPlayerForm.appendChild(phoneInput);
        addPlayerForm.appendChild(submitPButton);
    
        addPlayerContainer.innerHTML = '';
        addPlayerContainer.appendChild(addPlayerForm);
    
        submitPButton.addEventListener('click', () => {
            const phoneNumber = phoneInput.value.trim(); // Get the value from the input
            console.log('Player Phone Number:', phoneNumber); // Print to the console
            console.log('Team_id :', teamId);
            if (phoneNumber) {
                sendPlayerDataToAPI(phoneNumber, teamId,teamMembers,tournamentId); // Send data to API
            } else {
                alert('Please enter a valid phone number.'); // Alert if the input is empty
            }
        });
    }    
    
    async function sendPlayerDataToAPI(phoneNumber, teamId,teamMembers,tournamentId) {
        const requestData = {
            phoneNumber: phoneNumber,
            teamId: parseInt(teamId),
            tournamentId: parseInt(tournamentId)
        };
        try {
            const response = await fetch('http://localhost:9090/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestData)
            });
    
            if (!response.ok) {
                const errorDetails = await response.text();
                throw new Error('Failed to add player to team: ' + errorDetails);
            }
            
            const data = await response.json();
            console.log('Player added to team:', data);
            alert('Player successfully added to the team!');
        } catch (error) {
            console.error('Error:', error);
            alert('Error adding player to the team: ' + error.message);
        }
        displayUserDetails(teamId,teamMembers,tournamentId);
    }

    // Navigation buttons for different pages
    let home_btn = document.querySelector(".house_choice");
    let team_c_btn = document.querySelector(".team_c_choice");
    let tournament_c_btn = document.querySelector(".tournament_c_choice");

    home_btn.addEventListener("click", () => {
        window.location.href = "/dashboardo";
    });
    team_c_btn.addEventListener("click", () => {
        window.location.href = "/createTeam";
    });
    tournament_c_btn.addEventListener("click", () => {
        window.location.href = "/createTournament";
    });

    // Add the removePlayerFromTeam function as shown above
    async function removePlayerFromTeam(teamId, playerName, tournamentId, teamMembers) {
        const requestData = {
            playerName: playerName,
            teamId: parseInt(teamId),
            tournamentId: parseInt(tournamentId)
        };
        try {
            const response = await fetch('http://localhost:9090/remove', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(requestData)
            });

            if (!response.ok) {
                const errorDetails = await response.text();
                throw new Error('Failed to remove player from team: ' + errorDetails);
            }

            console.log(`Player ${playerName} removed from team ${teamId}`);
            alert(`Player ${playerName} successfully removed from the team!`);
        } catch (error) {
            console.error('Error:', error);
            alert('Error removing player from the team: ' + error.message);
        }

        // Refresh the player list
        displayUserDetails(teamId, teamMembers, tournamentId);
    }
});

