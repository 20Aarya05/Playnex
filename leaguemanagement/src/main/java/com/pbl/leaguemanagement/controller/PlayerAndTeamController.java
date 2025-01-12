package com.pbl.leaguemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pbl.leaguemanagement.dto.TeamTournamentDTO;
import com.pbl.leaguemanagement.model.PlayerAndTeam;
import com.pbl.leaguemanagement.service.PlayerAndTeamService;

@RestController
public class PlayerAndTeamController {

    @Autowired
    private PlayerAndTeamService playerAndTeamService;

    // This method will handle the /user/{userId}/teams-tournaments endpoint
    @GetMapping("/api/player-team/user/{userId}/teams-tournaments")
    public ResponseEntity<List<TeamTournamentDTO>> getTeamsAndTournamentsForUser(@PathVariable Long userId) {
        List<TeamTournamentDTO> teamsAndTournaments = playerAndTeamService.getTeamsAndTournamentsByUserId(userId);
        return ResponseEntity.ok(teamsAndTournaments);
    }

    @GetMapping("/player-count")
    public long getPlayerCount(@RequestParam String teamId) {
        return playerAndTeamService.getPlayerCountByTeamId(teamId);
    }

    @GetMapping("/teams/{teamId}/user-details")
    public List<String> getUserDetailsByTeamId(@PathVariable String teamId) {
        return playerAndTeamService.getUserDetailsByTeamId(teamId);
    }

    @PostMapping("/add")
    public ResponseEntity<PlayerAndTeam> addPlayerToTeam(@RequestBody AddPlayerRequest request) {
        PlayerAndTeam playerAndTeam = playerAndTeamService.addPlayerToTeam(
                request.getPhoneNumber(), 
                request.getTeamId(), 
                request.getTournamentId());
        return ResponseEntity.ok(playerAndTeam);
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removePlayerFromTeam(@RequestBody RemovePlayerRequest request) {
        try {
            playerAndTeamService.removePlayerFromTeam(request.getTeamId(), request.getPlayerName());
            return ResponseEntity.ok("Player removed from team successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error removing player");
        }
    }

    public static class RemovePlayerRequest {
        private String teamId;
        private String playerName;

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }
    }

    public static class AddPlayerRequest {
        private String phoneNumber;
        private String teamId;
        private String tournamentId;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getTournamentId() {
            return tournamentId;
        }

        public void setTournamentId(String tournamentId) {
            this.tournamentId = tournamentId;
        }
    }
}
