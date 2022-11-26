package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForecastExportDTO {

    private String cityName;

    private double minTemperature;

    private double maxTemperature;

    private LocalTime sunrise;

    private LocalTime sunset;

    @Override
    public String toString() {
        return String.format("City: %s%n" +
                        "-min temperature: %.2f%n" +
                        "--max temperature: %.2f%n" +
                        "---sunrise: %s%n" +
                        "----sunset: %s%n",
                cityName,
                minTemperature,
                maxTemperature,
                sunrise.toString(),
                sunset.toString());
    }
}
