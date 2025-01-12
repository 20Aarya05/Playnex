package com.pbl.leaguemanagement.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbl.leaguemanagement.model.Team;
import com.pbl.leaguemanagement.model.Tournament;
import com.pbl.leaguemanagement.service.TournamentService;

@Controller
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/createTournament")
    public String createTournamentPage() {
        return "createTournament"; // Return the createTournament.html view
    }

    @PostMapping("/createTournament")
    public String createTournament(@ModelAttribute Tournament tournament,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @ModelAttribute("tournamentDate") LocalDate tournamentDate,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @ModelAttribute("tournamentTime") LocalTime tournamentTime) {
        
        // Set the date and time for the tournament
        tournament.setTournamentDate(tournamentDate);
        tournament.setTournamentTime(tournamentTime);
        
        // Call the service to create the tournament
        tournamentService.createTournament(tournament);

        return "redirect:/createTournament"; // Redirect after POST to avoid form resubmission
    }

    @GetMapping("/createTeam")
    public String showcreateTeam() {
        return "createTeam"; 
    }

    // API endpoint to get teams by tournament ID
    @GetMapping("/api/tournaments/{tournamentId}/teams")
    @ResponseBody
    public ResponseEntity<List<Team>> getTeamsByTournamentId(@PathVariable String tournamentId) {
        Optional<Tournament> tournament = tournamentService.getTournamentById(tournamentId);
        
        if (tournament.isPresent()) {
            List<Team> teams = tournament.get().getTeams();
            return ResponseEntity.ok(teams);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/demo")
    public String showdemo() {
        return "demo"; 
    }
}
