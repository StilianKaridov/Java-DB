package com.example.football.models.dto;

import com.example.football.config.LocalDateAdapter;
import com.example.football.util.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerImportDTO {

    @XmlElement(name = "first-name")
    private String firstName;

    @XmlElement(name = "last-name")
    private String lastName;

    @XmlElement
    private String email;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @XmlElement(name = "birth-date")
    private LocalDate birthdate;

    @XmlElement
    private Position position;

    @XmlElement
    private TownDTO town;

    @XmlElement
    private TeamDTO team;

    @XmlElement
    private StatDTO stat;

    public void setFirstName(String firstName) {
        if (firstName.length() <= 2) {
            throw new IllegalArgumentException();
        }

        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName.length() <= 2) {
            throw new IllegalArgumentException();
        }

        this.lastName = lastName;
    }

    public void setEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException();
        }

        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setTown(TownDTO town) {
        this.town = town;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }

    public void setStat(StatDTO stat) {
        this.stat = stat;
    }

    public boolean validate() {
        try {
            setFirstName(this.firstName);
            setLastName(this.lastName);
            setEmail(this.email);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
