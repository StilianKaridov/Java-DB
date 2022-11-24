package com.example.xmlprocessing.carDealer.repositories;

import com.example.xmlprocessing.carDealer.domain.dtos.customer.CustomerDTO;
import com.example.xmlprocessing.carDealer.domain.dtos.customer.CustomerSalesDTO;
import com.example.xmlprocessing.carDealer.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "select new com.example.xmlprocessing.carDealer.domain.dtos.customer.CustomerDTO" +
            "(c.id, c.name, c.birthDate, c.isYoungDriver) " +
            "from Customer c " +
            "order by c.birthDate, c.isYoungDriver")
    Set<CustomerDTO> getAllOrderByBirthDateAndYoungDriver();

    @Query(value = "select new com.example.xmlprocessing.carDealer.domain.dtos.customer.CustomerSalesDTO" +
            "(c.name, c.sales.size , sum(p.price))" +
            "from Customer c " +
            "join c.sales s " +
            "join s.car car " +
            "join car.parts p " +
            "group by c " +
            "order by sum(p.price) desc, c.sales.size desc")
    List<CustomerSalesDTO> findAllWithAtLeastOneBoughtCar();
}
