package com.example.football.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TownImportDTO {

    @Expose
    private String name;

    @Expose
    private int population;

    @Expose
    private String travelGuide;

    public void setName(String name) {
        if (name.length() < 2) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public void setPopulation(int population) {
        if (population <= 0) {
            throw new IllegalArgumentException();
        }

        this.population = population;
    }

    public void setTravelGuide(String travelGuide) {
        if (travelGuide.length() < 10) {
            throw new IllegalArgumentException();
        }

        this.travelGuide = travelGuide;
    }

    public boolean validate() {
        try {
            setName(this.name);
            setPopulation(this.population);
            setTravelGuide(this.travelGuide);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
