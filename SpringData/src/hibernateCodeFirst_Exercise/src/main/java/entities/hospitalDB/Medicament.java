package entities.hospitalDB;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Medicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "medicaments")
    private Set<Visitation> visitations;

    @ManyToMany(mappedBy = "medicaments")
    private Set<Patient> patients;

    public Medicament() {
    }

    public Medicament(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
