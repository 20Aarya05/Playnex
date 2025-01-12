package com.pbl.leaguemanagement.model;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "player_and_team", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tournament_id", "user_id"})
})
public class PlayerAndTeam {
    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "PAT-id-generator")
    @GenericGenerator(
            name = "PAT-id-generator",
            strategy = "com.pbl.leaguemanagement.model.PATIdGenerator"
    )
    private String PAT_id; // Primary key

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnore
    private Team team;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    @JsonIgnore
    private Tournament tournament;

    public PlayerAndTeam() {}

    public String getPatId() {
        return this.PAT_id;
    }

    public void setPatId(String pat_id) {
        this.PAT_id = pat_id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
