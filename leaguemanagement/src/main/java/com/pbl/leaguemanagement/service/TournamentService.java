package com.pbl.leaguemanagement.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbl.leaguemanagement.model.Team;
import com.pbl.leaguemanagement.model.Tournament;
import com.pbl.leaguemanagement.repository.TeamRepository;
import com.pbl.leaguemanagement.repository.TournamentRepository;

@Service
public class TournamentService {

    private static final Logger logger = LoggerFactory.getLogger(TournamentService.class);

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Transactional
    public Tournament createTournament(Tournament tournament) {
        logger.info("Creating tournament: {}", tournament.getTournamentName());

        // Log the tournament date and time
        logger.info("Tournament Date: {}", tournament.getTournamentDate());
        logger.info("Tournament Time: {}", tournament.getTournamentTime());

        // Iterate over the teams and set their tournament reference
        for (Team team : tournament.getTeams()) {
            logger.info("Adding team: {}", team.getTeamName());
            team.setTournament(tournament);
            team.setSport(tournament.getSport());
            team.setTeamMembers(tournament.getTeamPlayerNo());
        }

        // Save the tournament along with its teams
        Tournament savedTournament = tournamentRepository.save(tournament);
        logger.info("Tournament saved with ID: {}", savedTournament.getTournament_id());
        return savedTournament;
    }

    @Transactional(readOnly = true)
    public Optional<Tournament> getTournamentById(String tournamentId) {
        logger.info("Fetching tournament with ID: {}", tournamentId);
        return tournamentRepository.findById(tournamentId);
    }

    @Transactional(readOnly = true)
    public List<Team> getTeamsByTournamentId(String tournamentId) {
        logger.info("Fetching teams for tournament ID: {}", tournamentId);
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found with ID: " + tournamentId));
        return tournament.getTeams();
    }

    @Transactional
    public void deleteTournament(String tournamentId) {
        logger.info("Deleting tournament with ID: {}", tournamentId);
        tournamentRepository.deleteById(tournamentId);
    }
}
