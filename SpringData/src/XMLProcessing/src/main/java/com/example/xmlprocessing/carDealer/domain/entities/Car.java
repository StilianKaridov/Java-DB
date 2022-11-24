package com.example.xmlprocessing.carDealer.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Fetch(FetchMode.JOIN)
    private List<Part> parts;

    public Car() {
        this.parts = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(make, car.make)
                && Objects.equals(model, car.model)
                && Objects.equals(travelledDistance, car.travelledDistance)
                && Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, travelledDistance, getId());
    }
}
