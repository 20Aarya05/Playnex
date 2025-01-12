package com.pbl.leaguemanagement.model;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {
    
    @SuppressWarnings("deprecation")
    @Id
    @GeneratedValue(generator = "team-id-generator")
    @GenericGenerator(
            name = "team-id-generator",
            strategy = "com.pbl.leaguemanagement.model.TeamIdGenerator"
    )
    private String team_id; // Primary key

    private String teamName; // Name of the team

    private String sport; // Sport for the team

    private int teamMembers; // Number of members in the team

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    @JsonIgnore
    private Tournament tournament;
    
}