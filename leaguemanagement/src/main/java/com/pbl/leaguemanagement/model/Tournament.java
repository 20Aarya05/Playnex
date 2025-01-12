package com.pbl.leaguemanagement.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tournament {

    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "tournament-id-generator")
    @GenericGenerator(
            name = "tournament-id-generator",
            strategy = "com.pbl.leaguemanagement.model.TournamentIdGenerator"
    )
    private String tournament_id;

    private String tournamentName;
    private String sport;
    private int teamPlayerNo;

    // New fields for date and time
    private LocalDate tournamentDate;
    private LocalTime tournamentTime;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    // Getters and Setters
    public String getTournamentId() {
        return tournament_id;
    }

    public void setTournamentId(String tournament_id) {
        this.tournament_id = tournament_id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public int getTeamPlayerNo() {
        return teamPlayerNo;
    }

    public void setTeamPlayerNo(int teamPlayerNo) {
        this.teamPlayerNo = teamPlayerNo;
    }

    public LocalDate getTournamentDate() {
        return tournamentDate;
    }

    public void setTournamentDate(LocalDate tournamentDate) {
        this.tournamentDate = tournamentDate;
    }

    public LocalTime getTournamentTime() {
        return tournamentTime;
    }

    public void setTournamentTime(LocalTime tournamentTime) {
        this.tournamentTime = tournamentTime;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}