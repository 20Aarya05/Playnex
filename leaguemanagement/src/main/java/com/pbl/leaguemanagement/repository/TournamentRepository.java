package com.pbl.leaguemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbl.leaguemanagement.model.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, String> {

    Optional<Tournament> findById(String tournamentId);
    // Additional query methods if needed
}
