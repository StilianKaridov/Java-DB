package ExamPreparation.CarService.src.main.java.softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartImportDTO;
import softuni.exam.models.entity.Part;
import ExamPreparation.CarService.src.main.java.softuni.exam.repository.PartRepository;
import ExamPreparation.CarService.src.main.java.softuni.exam.service.PartService;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static softuni.exam.util.FilePaths.PARTS_PATH;
import static softuni.exam.util.Messages.INVALID_IMPORT_MESSAGE;
import static softuni.exam.util.Messages.SUCCESSFULLY_IMPORTED_PART_MESSAGE;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, Gson gson, ModelMapper mapper) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(PARTS_PATH);
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();

        FileReader reader = new FileReader(PARTS_PATH.toFile());

        PartImportDTO[] partDTOs = this.gson.fromJson(reader, PartImportDTO[].class);

        Arrays.stream(partDTOs)
                .forEach(p -> {
                    boolean isPartPresent = this.partRepository.findFirstByPartName(p.getPartName()).isPresent();

                    if (isPartPresent || !p.validate()) {
                        sb.append(String.format(INVALID_IMPORT_MESSAGE, "part"));
                        return;
                    }

                    Part toInsert = this.mapper.map(p, Part.class);

                    this.partRepository.saveAndFlush(toInsert);

                    sb.append(String.format(SUCCESSFULLY_IMPORTED_PART_MESSAGE,
                            toInsert.getPartName(),
                            toInsert.getPrice().doubleValue()));
                });

        reader.close();

        return sb.toString();
    }
}
