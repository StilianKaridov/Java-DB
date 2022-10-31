package entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bikes")
public class Bike extends Vehicle {
    private final static String TYPE = "Bike";

    public Bike() {
        super(TYPE);
    }
}