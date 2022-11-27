package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TownImportDTO {

    @Expose
    private String townName;

    @Expose
    private int population;

    public void setTownName(String townName) {
        if (townName.length() < 2) {
            throw new IllegalArgumentException();
        }

        this.townName = townName;
    }

    public void setPopulation(int population) {
        if (population <= 0) {
            throw new IllegalArgumentException();
        }

        this.population = population;
    }

    public boolean validate() {
        try {
            setTownName(this.townName);
            setPopulation(this.population);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
