package com.pbl.leaguemanagement.dto;

public class TeamTournamentDTO {
    private String teamName;
    private String tournamentName;

    public TeamTournamentDTO(String teamName, String tournamentName) {
        this.teamName = teamName;
        this.tournamentName = tournamentName;
    }

    // Getters and setters
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
}
