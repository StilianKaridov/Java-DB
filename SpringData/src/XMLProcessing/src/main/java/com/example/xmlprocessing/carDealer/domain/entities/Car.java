package com.example.xmlprocessing.carDealer.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String make;

    @Column
    private String model;

    @Column(name = "travelled_distance")
    private double travelledDistance;

    @ManyToMany
    private List<Part> parts;

    public Car() {
        this.parts = new ArrayList<>();
    }
}
