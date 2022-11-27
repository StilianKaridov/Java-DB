package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.constants.FilePaths.TOWNS_IMPORT;
import static softuni.exam.constants.PrintFormats.INVALID_FORMAT;
import static softuni.exam.constants.PrintFormats.SUCCESSFUL_TOWN_FORMAT;

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

        FileReader fileReader = new FileReader(Path.of(TOWNS_IMPORT).toFile());

        Arrays.stream(this.gson.fromJson(fileReader, TownImportDTO[].class))
                .forEach(town -> {
                    if (!town.validate()) {
                        sb.append(String.format(INVALID_FORMAT, "town"));
                        return;
                    }

                    Town toInsert = this.mapper.map(town, Town.class);

                    this.townRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFUL_TOWN_FORMAT, town.getTownName(), town.getPopulation()));
                });


        fileReader.close();
        return sb.toString();
    }
}
