package com.example.xmlprocessing.carDealer.repositories;

import com.example.xmlprocessing.carDealer.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
