package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CityImportDTO {

    @Expose
    private String cityName;

    @Expose
    private String description;

    @Expose
    private int population;

    @Expose
    private Long country;

    public void setCityName(String cityName) {
        if (cityName.length() < 2 || cityName.length() > 60) {
            throw new IllegalArgumentException();
        }

        this.cityName = cityName;
    }

    public void setDescription(String description) {
        if (description.length() < 2) {
            throw new IllegalArgumentException();
        }

        this.description = description;
    }

    public void setPopulation(int population) {
        if (population < 500) {
            throw new IllegalArgumentException();
        }

        this.population = population;
    }

    public boolean validate() {
        try {
            setCityName(this.cityName);
            setDescription(this.description);
            setPopulation(this.population);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
