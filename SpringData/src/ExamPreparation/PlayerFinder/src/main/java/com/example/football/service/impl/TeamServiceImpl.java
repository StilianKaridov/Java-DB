package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static com.example.football.util.FilePaths.TEAMS_IMPORT;
import static com.example.football.util.Messages.INVALID_FORMAT;
import static com.example.football.util.Messages.SUCCESSFULLY_IMPORTED_TOWN_TEAM;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, Gson gson, ModelMapper mapper) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.mapper = mapper;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_IMPORT));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        FileReader fileReader = new FileReader(TEAMS_IMPORT);

        TeamImportDTO[] teamDTOs = this.gson.fromJson(fileReader, TeamImportDTO[].class);

        Arrays.stream(teamDTOs)
                .forEach(team -> {

                    boolean isTeamPresent = this.teamRepository.findFirstByName(team.getName()).isPresent();

                    if (!team.validate() || isTeamPresent) {
                        sb.append(String.format(INVALID_FORMAT, "Team"));
                        return;
                    }

                    Team toInsert = this.mapper.map(team, Team.class);

                    Optional<Town> town = this.townRepository.findFirstByName(team.getTownName());

                    toInsert.setTown(town.get());

                    this.teamRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN_TEAM, "Team", toInsert.getName(), toInsert.getFanBase()));
                });


        fileReader.close();

        return sb.toString();
    }
}
