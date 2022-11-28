package com.example.football.service.impl;

import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.example.football.util.FilePaths.TOWNS_IMPORT;
import static com.example.football.util.Messages.INVALID_FORMAT;
import static com.example.football.util.Messages.SUCCESSFULLY_IMPORTED_TOWN_TEAM;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper mapper) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_IMPORT));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        FileReader fileReader = new FileReader(TOWNS_IMPORT);

        TownImportDTO[] townDTOs = this.gson.fromJson(fileReader, TownImportDTO[].class);

        Arrays.stream(townDTOs)
                .forEach(town -> {

                    boolean isTownPresent = this.townRepository.findFirstByName(town.getName()).isPresent();

                    if (!town.validate() || isTownPresent) {
                        sb.append(String.format(INVALID_FORMAT, "Town"));
                        return;
                    }

                    Town toInsert = this.mapper.map(town, Town.class);

                    this.townRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_TOWN_TEAM, "Town", toInsert.getName(), toInsert.getPopulation()));
                });


        fileReader.close();

        return sb.toString();
    }
}
