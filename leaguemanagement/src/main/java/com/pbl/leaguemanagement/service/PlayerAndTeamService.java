package com.pbl.leaguemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbl.leaguemanagement.dto.TeamTournamentDTO;
import com.pbl.leaguemanagement.model.PlayerAndTeam;
import com.pbl.leaguemanagement.model.Team;
import com.pbl.leaguemanagement.model.Tournament;
import com.pbl.leaguemanagement.model.User;
import com.pbl.leaguemanagement.repository.PlayerAndTeamRepository;
import com.pbl.leaguemanagement.repository.TeamRepository;
import com.pbl.leaguemanagement.repository.TournamentRepository;
import com.pbl.leaguemanagement.repository.UserRepository;

@Service
public class PlayerAndTeamService {

    @Autowired
    private PlayerAndTeamRepository playerAndTeamRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    public long getPlayerCountByTeamId(String teamId) {
        return playerAndTeamRepository.countPlayersByTeamId(teamId);
    }

    public List<String> getUserDetailsByTeamId(String teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) {
            System.out.println("No team found with ID: " + teamId);
            return List.of();
        }

        List<PlayerAndTeam> associations = playerAndTeamRepository.findByTeam(team);

        return associations.stream()
                .map(association -> {
                    User user = association.getUser();
                    if (user != null) {
                        return "User ID: " + user.getUser_id() + ", Name: " + user.getName() + ", Age: " + user.getAge();
                    } else {
                        return "User not found for association with team ID: " + teamId;
                    }
                })
                .toList();
    }

    public PlayerAndTeam addPlayerToTeam(String phoneNumber, String teamId, String tournamentId) {
        // Retrieve the user based on phone number
        User user = userRepository.findByNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found with phone number: " + phoneNumber));
    
        // Retrieve the team based on teamId
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with ID: " + teamId));
    
        // Retrieve the tournament based on tournamentId
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found with ID: " + tournamentId));
    
        // Check if an entry already exists with the same user and tournament
        boolean exists = playerAndTeamRepository.existsByUserAndTournament(user, tournament);
        if (exists) {
            throw new RuntimeException("This user is already registered in the tournament.");
        }
    
        // Create a new PlayerAndTeam entity
        PlayerAndTeam playerAndTeam = new PlayerAndTeam();
        playerAndTeam.setUser(user);
        playerAndTeam.setTeam(team);
        playerAndTeam.setTournament(tournament);
    
        // Save the entity in the database
        return playerAndTeamRepository.save(playerAndTeam);
    }    

    public void removePlayerFromTeam(String teamId, String playerName) {
        // Use the repository method to find the PlayerAndTeam entry
        PlayerAndTeam playerAndTeam = playerAndTeamRepository.findByTeamIdAndPlayerName(teamId, playerName);

        if (playerAndTeam != null) {
            // Remove the player if the association exists
            playerAndTeamRepository.delete(playerAndTeam);
        } else {
            // Throw an exception if the player was not found in the team
            throw new RuntimeException("Player not found in team with ID: " + teamId + " and name: " + playerName);
        }
    }

    public List<TeamTournamentDTO> getTeamsAndTournamentsByUserId(Long userId) {
        return playerAndTeamRepository.findTeamsAndTournamentsByUserId(userId);
    }
}