package ExamPreparation.CarService.src.main.java.softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.enums.CarType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name =  "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_type", nullable = false)
    private CarType carType;

    @Column(name = "car_make", nullable = false)
    private String carMake;

    @Column(name = "car_model", nullable = false)
    private String carModel;

    @Column(nullable = false)
    private int year;

    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    @Column(nullable = false)
    private int kilometers;

    @Column(nullable = false)
    private double engine;
}
