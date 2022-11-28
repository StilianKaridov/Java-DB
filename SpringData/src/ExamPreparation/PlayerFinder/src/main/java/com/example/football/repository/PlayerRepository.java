package com.example.football.repository;

import com.example.football.models.dto.BestPlayersExportDTO;
import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findFirstByEmail(String email);

    @Query(value = "select new com.example.football.models.dto.BestPlayersExportDTO" +
            "(p.firstName, p.lastName, p.position, p.team.name, p.team.stadiumName) " +
            "from Player p " +
            "where p.birthdate >= '1995-01-01' and p.birthdate <= '2003-01-01' " +
            "order by p.stat.shooting desc, p.stat.passing desc, p.stat.endurance desc, p.lastName")
    List<BestPlayersExportDTO> getTheBestPlayers();
}
