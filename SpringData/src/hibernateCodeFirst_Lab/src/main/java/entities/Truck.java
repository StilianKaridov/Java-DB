package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "trucks")
public class Truck extends Vehicle {

    private final static String TYPE = "Truck";

    @Column(name = "load_capacity")
    private double loadCapacity;

    public Truck() {
        super(TYPE);
    }

    public Truck(double loadCapacity) {
        this();
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}