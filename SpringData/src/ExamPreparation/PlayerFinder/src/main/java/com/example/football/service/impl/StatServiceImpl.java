package com.example.football.service.impl;

import com.example.football.models.dto.StatImportDTO;
import com.example.football.models.dto.StatImportWrapperDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.example.football.util.FilePaths.STATS_IMPORT;
import static com.example.football.util.Messages.INVALID_FORMAT;
import static com.example.football.util.Messages.SUCCESSFULLY_IMPORTED_STAT;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final ModelMapper mapper;

    public StatServiceImpl(StatRepository statRepository, ModelMapper mapper) {
        this.statRepository = statRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_IMPORT));
    }

    @Override
    public String importStats() throws JAXBException, IOException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(StatImportWrapperDTO.class);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        FileReader fileReader = new FileReader(STATS_IMPORT);

        StatImportWrapperDTO wrapperDTO = (StatImportWrapperDTO) unmarshaller.unmarshal(fileReader);

        for (StatImportDTO stat : wrapperDTO.getStats()) {
            boolean doesStatExists = this.statRepository
                    .findFirstByPassingAndShootingAndEndurance(stat.getPassing(), stat.getShooting(), stat.getEndurance())
                    .isPresent();

            if (!stat.validate() || doesStatExists) {
                sb.append(String.format(INVALID_FORMAT, "Stat"));
                continue;
            }

            Stat toInsert = this.mapper.map(stat, Stat.class);

            this.statRepository.saveAndFlush(toInsert);

            sb.append(String.format(SUCCESSFULLY_IMPORTED_STAT,
                    toInsert.getShooting(),
                    toInsert.getPassing(),
                    toInsert.getEndurance()));
        }

        fileReader.close();

        return sb.toString();
    }
}
