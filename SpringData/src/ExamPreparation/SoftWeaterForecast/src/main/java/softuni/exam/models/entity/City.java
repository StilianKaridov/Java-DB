package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_name", unique = true, nullable = false)
    private String cityName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int population;

    @ManyToOne
    private Country country;
}
