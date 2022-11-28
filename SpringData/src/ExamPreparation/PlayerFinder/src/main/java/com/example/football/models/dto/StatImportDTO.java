package com.example.football.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "stat")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatImportDTO {

    @XmlElement
    private double passing;

    @XmlElement
    private double shooting;

    @XmlElement
    private double endurance;

    public void setPassing(double passing) {
        if (passing <= 0) {
            throw new IllegalArgumentException();
        }

        this.passing = passing;
    }

    public void setShooting(double shooting) {
        if (shooting <= 0) {
            throw new IllegalArgumentException();
        }

        this.shooting = shooting;
    }

    public void setEndurance(double endurance) {
        if (endurance <= 0) {
            throw new IllegalArgumentException();
        }

        this.endurance = endurance;
    }

    public boolean validate() {
        try {
            setPassing(this.passing);
            setShooting(this.shooting);
            setEndurance(this.endurance);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
