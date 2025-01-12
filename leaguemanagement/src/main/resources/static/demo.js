async function addPlayerToTeam() {
    // Get input values from your form or input fields
    const phoneNumber = 9321657025;
    const teamId = 1;
    const tournamentId = 1;

    // Create the request payload
    const requestData = {
        phoneNumber: phoneNumber,
        teamId: parseInt(teamId),
        tournamentId: parseInt(tournamentId)
    };

    try {
        // Make a POST request to the Spring Boot API
        const response = await fetch('/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        // Handle the response
        if (response.ok) {
            const data = await response.json();
            console.log("Player added successfully:", data);
            alert("Player added successfully!");
        } else {
            // Handle errors
            const errorData = await response.json();
            console.error("Error adding player:", errorData);
            alert("Failed to add player: " + (errorData.message || "Unknown error"));
        }
    } catch (error) {
        console.error("Network error:", error);
        alert("An error occurred while adding the player.");
    }
}
