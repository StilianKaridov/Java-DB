package ExamPreparation.CarService.src.main.java.softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicImportDTO;
import softuni.exam.models.entity.Mechanic;
import ExamPreparation.CarService.src.main.java.softuni.exam.repository.MechanicRepository;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.MechanicService;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static softuni.exam.util.FilePaths.MECHANICS_PATH;
import static softuni.exam.util.Messages.INVALID_IMPORT_MESSAGE;
import static softuni.exam.util.Messages.SUCCESSFULLY_IMPORTED_MECHANIC_MESSAGE;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final MechanicRepository mechanicRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public MechanicServiceImpl(MechanicRepository mechanicRepository, Gson gson, ModelMapper mapper) {
        this.mechanicRepository = mechanicRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(MECHANICS_PATH);
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();

        FileReader reader = new FileReader(MECHANICS_PATH.toFile());

        MechanicImportDTO[] mechanicDTOs = this.gson.fromJson(reader, MechanicImportDTO[].class);

        Arrays.stream(mechanicDTOs)
                .forEach(m -> {
                    boolean isMechanicPresent = this.mechanicRepository.findFirstByEmail(m.getEmail()).isPresent();

                    if (isMechanicPresent || !m.validate()) {
                        sb.append(String.format(INVALID_IMPORT_MESSAGE, "mechanic"));
                        return;
                    }

                    Mechanic toInsert = this.mapper.map(m, Mechanic.class);

                    this.mechanicRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_MECHANIC_MESSAGE,
                            toInsert.getFirstName(),
                            toInsert.getLastName()));
                });

        reader.close();

        return sb.toString();
    }
}
