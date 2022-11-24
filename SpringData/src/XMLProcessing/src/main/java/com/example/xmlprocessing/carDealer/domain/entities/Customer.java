package com.example.xmlprocessing.carDealer.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "date_of_birth")
    private Date birthDate;

    @Column(name = "is_young_driver")
    private boolean isYoungDriver;

    @OneToMany(targetEntity = Sale.class, mappedBy = "customer")
    @Fetch(FetchMode.JOIN)
    private List<Sale> sales;
}
