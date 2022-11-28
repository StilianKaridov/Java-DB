package com.example.football.models.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamImportDTO {

    @Expose
    private String name;

    @Expose
    private String stadiumName;

    @Expose
    private int fanBase;

    @Expose
    private String history;

    @Expose
    private String townName;

    public void setName(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public void setStadiumName(String stadiumName) {
        if (stadiumName.length() < 3) {
            throw new IllegalArgumentException();
        }

        this.stadiumName = stadiumName;
    }

    public void setFanBase(int fanBase) {
        if (fanBase < 1000) {
            throw new IllegalArgumentException();
        }

        this.fanBase = fanBase;
    }

    public void setHistory(String history) {
        if (history.length() < 10) {
            throw new IllegalArgumentException();
        }

        this.history = history;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public boolean validate() {
        try {
            setName(this.name);
            setStadiumName(this.stadiumName);
            setFanBase(this.fanBase);
            setHistory(this.history);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
