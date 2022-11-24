package com.example.xmlprocessing.carDealer.repositories;

import com.example.xmlprocessing.carDealer.domain.dtos.supplier.SupplierDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = "select new com.example.xmlprocessing.carDealer.domain.dtos.supplier.SupplierDTO " +
            "(s.id, s.name, s.parts.size)" +
            "from Supplier s")
    List<SupplierDTO> getLocalSuppliers();
}
