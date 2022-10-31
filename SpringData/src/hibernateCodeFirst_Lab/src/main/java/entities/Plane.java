package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "planes")
public class Plane extends Vehicle {

    private final static String TYPE = "Plane";

    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    public Plane() {
        super(TYPE);
    }

    public Plane(int passengerCapacity) {
        this();
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}