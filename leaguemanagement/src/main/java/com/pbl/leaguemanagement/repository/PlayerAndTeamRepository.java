package com.pbl.leaguemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pbl.leaguemanagement.dto.TeamTournamentDTO;
import com.pbl.leaguemanagement.model.PlayerAndTeam;
import com.pbl.leaguemanagement.model.Team;
import com.pbl.leaguemanagement.model.Tournament;
import com.pbl.leaguemanagement.model.User;

public interface PlayerAndTeamRepository extends JpaRepository<PlayerAndTeam, String> {
    @Query("SELECT COUNT(p) FROM PlayerAndTeam p WHERE p.team.id = :teamId")
    long countPlayersByTeamId(@Param("teamId") String teamId);

    List<PlayerAndTeam> findByTeam(Team team);

    boolean existsByUserAndTournament(User user, Tournament tournament);

    @Query("SELECT p FROM PlayerAndTeam p WHERE p.team.id = :teamId AND p.user.name = :playerName")
    PlayerAndTeam findByTeamIdAndPlayerName(@Param("teamId") String teamId, @Param("playerName") String playerName);

    @Query("SELECT new com.pbl.leaguemanagement.dto.TeamTournamentDTO(t.teamName, tr.tournamentName) " +
           "FROM PlayerAndTeam pat " +
           "JOIN pat.team t " +
           "JOIN pat.tournament tr " +
           "WHERE pat.user.id = :userId")
    List<TeamTournamentDTO> findTeamsAndTournamentsByUserId(Long userId);
}