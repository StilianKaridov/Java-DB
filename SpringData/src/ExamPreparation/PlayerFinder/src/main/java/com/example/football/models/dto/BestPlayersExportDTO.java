package com.example.football.models.dto;

import com.example.football.util.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestPlayersExportDTO {

    private String firstName;

    private String lastName;

    private Position position;

    private String teamName;

    private String teamStadiumName;

    @Override
    public String toString() {
        return String.format("Player - %s %s%n" +
                        "   Position - %s%n" +
                        "   Team - %s%n" +
                        "   Stadium - %s%n",
                this.firstName,
                this.lastName,
                this.position,
                this.teamName,
                this.teamStadiumName);
    }
}
