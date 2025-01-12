package com.pbl.leaguemanagement.repository;

import com.pbl.leaguemanagement.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    // Additional query methods if needed
}
