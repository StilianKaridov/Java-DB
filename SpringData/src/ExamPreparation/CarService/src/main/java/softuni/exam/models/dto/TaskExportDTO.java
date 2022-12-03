package ExamPreparation.CarService.src.main.java.softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskExportDTO {

    private Long id;

    private BigDecimal price;

    private String carMake;

    private String carModel;

    private int kilometers;

    private double engine;

    private String firstName;

    private String lastName;

    @Override
    public String toString() {
        return String.format("Car %s %s with %dkm%n" +
                        "-Mechanic: %s %s - task №%d:%n" +
                        "--Engine: %.1f%n" +
                        "---Price: %.2f$%n",
                this.getCarMake(),
                this.getCarModel(),
                this.getKilometers(),
                this.getFirstName(),
                this.getLastName(),
                this.getId(),
                this.getEngine(),
                this.getPrice().doubleValue());
    }
}
