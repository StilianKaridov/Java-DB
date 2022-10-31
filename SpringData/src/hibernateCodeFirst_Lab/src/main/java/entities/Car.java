package entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends Vehicle {

    private final static String TYPE = "Car";

    @Basic
    private int seats;

    public Car() {
        super(TYPE);
    }

    public Car(String model, String fuelType, int seats) {
        this();
        this.model = model;
        this.fuelType = fuelType;
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}