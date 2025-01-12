package com.pbl.leaguemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbl.leaguemanagement.model.Team;
import com.pbl.leaguemanagement.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository; // Assume this is your JPA repository

    public void createTeam(Team team) {
        teamRepository.save(team); // Save team to the database
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll(); // Get all teams from the database
    }

    public void addPlayerToTeam(String teamId, String phoneNumber) {
        throw new UnsupportedOperationException("Unimplemented method 'addPlayerToTeam'");
    }
}

